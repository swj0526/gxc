package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.entity.TbGoodsInAndOut;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbPurchaseDetalis;
import com.swj.mapper.TbpurchasedetalisMapper;
import com.swj.service.GoodsService;
import com.swj.service.PurchaseDetalisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Service
public class PurchaseDetalisServiceImpl extends ServiceImpl<TbpurchasedetalisMapper, TbPurchaseDetalis> implements PurchaseDetalisService {
    private long total;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsInAndOutServiceImpl goodsInAndOutService;
    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public int determineDetails(String idList, String numList, String remarkList, Integer purchaseId) {
        //先拆分ID
        String[] ids = idList.split(",");
        String[] nums = numList.split(",");
        String[] remarks = remarkList.split("@");
        Boolean state =false;
        for (int j = 0; j < ids.length; j++) {
            TbPurchaseDetalis detalis = baseMapper.selectById(ids[j]);
            if(detalis.getNum()!=Integer.parseInt(nums[j])){ //判断传递的商品那个是受损的
                detalis.setNumInstock(Integer.parseInt(nums[j]));//更改数量
                detalis.setNumError(detalis.getNum()-detalis.getNumInstock());
                detalis.setRemark(remarks[j]);
                state=true;
            }
            detalis.setIsEnter(TbPurchaseDetalis.ENTER_YES);
            baseMapper.updateById(detalis);
            //商品id
            TbGoods goods = goodsService.getGoodsByCode(detalis.getCode());
            //商品进库记录
            TbGoodsInAndOut goodsInAndOut = new TbGoodsInAndOut();
            goodsInAndOut.setGoodsId(goods.getId());
            goodsInAndOut.setNum(Integer.parseInt(nums[j]));
            goodsInAndOut.setState(TbGoodsInAndOut.STATE_INTO);//进库
            goodsInAndOutService.addGoodsInAndOut(goodsInAndOut);
            //更改商品的库存数量
            goods.setInventoryQuantity(goods.getInventoryQuantity()+Integer.parseInt(nums[j]));
            goodsService.updateGoods(goods);
        }
        TbPurchase purchase = purchaseService.getPurchaseById(purchaseId);
        if(state==true){

            purchase.setIsRead(TbPurchase.STATE_NO);
        }else{
            purchase.setIsRead(TbPurchase.STATE_END);
        }
        purchaseService.updatePurchase(purchase);
        return 1;
    }

    @Override
    public int updateDetails(String idList, String numList,Integer purchaseId) {
        //先拆分ID
        String[] ids = idList.split(",");
        String[] nums = numList.split(",");
        BigDecimal moeny = new BigDecimal(0);
        for (int j = 0; j < ids.length; j++) {
            TbPurchaseDetalis detalis = baseMapper.selectById(ids[j]);
            if(Integer.parseInt(nums[j])>0){
                detalis.setNum(Integer.parseInt(nums[j]));//更改数量
                detalis.setSum(detalis.getPurchasingPrice().multiply(new BigDecimal(detalis.getNum())));//更改钱
                moeny = moeny.add(detalis.getSum());
                baseMapper.updateById(detalis);
            }else {
                baseMapper.deleteById(detalis);
            }
        }
        TbPurchase purchase = purchaseService.getPurchaseById(purchaseId);
        purchase.setSum(moeny.setScale(2));
        purchaseService.updatePurchase(purchase);
        return 1;
    }

    @Override
    public int addDetails(String idList, String numList) {
        //先拆分ID
        String[] ids = idList.split(",");
        String[] nums = numList.split(",");
        //生成采购单的一些基本信息
        TbPurchase purchase = new TbPurchase();
        purchase.setCode("CGD" + System.currentTimeMillis());
        purchase.setIdList(idList);
        purchase.setNumList(numList);
        purchaseService.addPurchase(purchase);
       BigDecimal moeny = new BigDecimal(0);
        for (int j = 0; j < ids.length; j++) {
            TbGoods goods = goodsService.getGoodsById(Integer.parseInt(ids[j]));
            TbPurchaseDetalis detalis = new TbPurchaseDetalis();
            detalis.setCode(goods.getCode());
            detalis.setModel(goods.getModel());
            detalis.setNum(Integer.parseInt(nums[j]));
            detalis.setName(goods.getName());
            detalis.setPurchasingPrice(goods.getPurchasingPrice());
            detalis.setSellingPrice(goods.getSellingPrice());
            detalis.setSum(goods.getPurchasingPrice().multiply(new BigDecimal(detalis.getNum())));
            detalis.setNumInstock(detalis.getNum());
            detalis.setIsEnter(TbPurchaseDetalis.ENTER_NO);
            detalis.setPurchaseId(purchase.getId());
            moeny = moeny.add(detalis.getSum());
            baseMapper.insert(detalis);
        }
         purchase.setSum(moeny.setScale(2));
        purchaseService.updatePurchase(purchase);
        return 1;
    }

    @Override
    public void deleteDetailsListByPurchaseId(Integer purchaseId) {
        QueryWrapper<TbPurchaseDetalis> queryWrapper = new QueryWrapper();
        queryWrapper.eq("purchase_id",purchaseId);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<TbPurchaseDetalis> getDetailsByPurchaseId(Integer page, Integer limit, Integer purchaseId) {
        QueryWrapper<TbPurchaseDetalis> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        Page<TbPurchaseDetalis> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        queryWrapper.eq("purchase_id", purchaseId);
        //找到当前采购单下采购的商品
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchaseDetalis> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    /**
     * 检查商品,合格的入库,不合格拒收入库,并更改采购单的阅读状态
     *
     * @param purchaseId
     */
    @Override
    public void checkGoods(Integer page, Integer limit,Integer purchaseId, Map<String, Integer> map) {
        List<TbPurchaseDetalis> list = getDetailsByPurchaseId(page,limit,purchaseId);
        //遍历更改所有采购商品详情的入库状态,
        for (TbPurchaseDetalis detalis : list) {
            Integer id = detalis.getId();
            detalis.setIsEnter(map.get(id));
        }
        purchaseService.updatePurchaseByRead(purchaseId, TbPurchase.STATE_READ);


    }
}