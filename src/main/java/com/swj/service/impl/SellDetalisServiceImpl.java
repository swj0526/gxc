package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.*;
import com.swj.mapper.TbselldetalisMapper;
import com.swj.service.GoodsInAndOutService;
import com.swj.service.GoodsService;
import com.swj.service.SellDetalisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Service
public class SellDetalisServiceImpl extends ServiceImpl<TbselldetalisMapper, TbSellDetalis> implements SellDetalisService {
 private long total;

 @Autowired
 private SellService sellService;

 @Autowired
 private GoodsService goodsService;

@Autowired
private GoodsInAndOutService goodsInAndOutService;

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public  List<TbSellDetalis> getDetailsBySellId(Integer page, Integer limit,Integer sellId) {
        QueryWrapper<TbSellDetalis> queryWrapper = new QueryWrapper();
        queryWrapper.eq("sell_id",sellId);
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        Page<TbSellDetalis> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSellDetalis> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public void checkGoods(Integer page, Integer limit,Integer sellId, Map<String, Integer> map) {
        List<TbSellDetalis> list = getDetailsBySellId(page,limit,sellId);
        //遍历更改所有销售商品详情的出库状态,
        for (TbSellDetalis detalis : list) {
            Integer id = detalis.getId();
            detalis.setIsEnter(map.get(id));
        }
        sellService.updateSellByRead(sellId, TbPurchase.STATE_READ);
    }

    @Override
    public int addSellDetails(Integer sellId, int num, TbGoods goods) {
        TbSellDetalis detalis = new TbSellDetalis();
        detalis.setSellId(sellId);
        detalis.setNum(num);
        detalis.setCode(goods.getCode());
        detalis.setName(goods.getName());
        detalis.setModel(goods.getModel());
        detalis.setPurchasingPrice(goods.getPurchasingPrice());
        detalis.setSellingPrice(goods.getSellingPrice());
        detalis.setIsEnter(0);
      return   baseMapper.insert(detalis);

    }

    @Override
    public int addDetails(String idList, String numList,Integer clientId) {
        //todo 判断客户是否存在

        //先拆分ID
        String[] ids = idList.split(",");
        String[] nums = numList.split(",");
        //生成采购单的一些基本信息
        TbSell sell = new TbSell();
        sell.setCode("XSD" + System.currentTimeMillis());
        sell.setIdList(idList);
        sell.setNumList(numList);
        sellService.addSell(sell);
        BigDecimal moeny = new BigDecimal(0);
        for (int j = 0; j < ids.length; j++) {
            TbGoods goods = goodsService.getGoodsById(Integer.parseInt(ids[j]));
            TbSellDetalis detalis = new TbSellDetalis();
            detalis.setCode(goods.getCode());
            detalis.setModel(goods.getModel());
            detalis.setNum(Integer.parseInt(nums[j]));
            detalis.setName(goods.getName());
            detalis.setPurchasingPrice(goods.getPurchasingPrice());
            detalis.setSellingPrice(goods.getSellingPrice());
            detalis.setSum(goods.getSellingPrice().multiply(new BigDecimal(detalis.getNum())));
            detalis.setIsEnter(TbPurchaseDetalis.ENTER_NO);
            detalis.setSellId(sell.getId());
            moeny = moeny.add(detalis.getSum());
            baseMapper.insert(detalis);
        }
        sell.setSum(moeny.setScale(2));
        sell.setClientId(clientId);
        sellService.updateSell(sell);
        return 1;
    }

    @Override
    public int updateDetails(String idList, String numList, Integer sellId) {
        //先拆分ID
        String[] ids = idList.split(",");
        String[] nums = numList.split(",");
        BigDecimal moeny = new BigDecimal(0);
        for (int j = 0; j < ids.length; j++) {
            TbSellDetalis detalis = baseMapper.selectById(ids[j]);
            if(Integer.parseInt(nums[j])>0){
                detalis.setNum(Integer.parseInt(nums[j]));//更改数量
                detalis.setSum(detalis.getSellingPrice().multiply(new BigDecimal(detalis.getNum())));//更改钱
                moeny = moeny.add(detalis.getSum());
                baseMapper.updateById(detalis);
            }else {
                baseMapper.deleteById(detalis);
            }
        }
        TbSell sell = sellService.getSellById(sellId);
        sell.setSum(moeny.setScale(2));
        sellService.updateSell(sell);
        return 1;
    }

    @Override
    public void determine(Integer sellId) {
      QueryWrapper<TbSellDetalis> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("sell_id", sellId);
        List<TbSellDetalis> list = baseMapper.selectList(queryWrapper);
        for(TbSellDetalis d:list){
            d.getCode();//商品编号
            d.getNum();//数量
            TbGoods goods = goodsService.getGoodsByCode(d.getCode());
            System.out.println("+++++++++++++"+goods);
            goods.setInventoryQuantity(goods.getInventoryQuantity()-d.getNum());
            //todo 判断一下商品库存是否为负数
            goodsService.updateGoods(goods);
            //出入库记录
            TbGoodsInAndOut goodsInAndOut = new TbGoodsInAndOut();
            goodsInAndOut.setState(TbGoodsInAndOut.STATE_OUT);
            goodsInAndOut.setNum(d.getNum());
            goodsInAndOut.setGoodsId(goods.getId());
            goodsInAndOutService.addGoodsInAndOut(goodsInAndOut);
        }
        TbSell sell = sellService.getSellById(sellId);
        sell.setIsRead(TbSell.STATE_SEND);
        sellService.updateSell(sell);
    }
}
