package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbSell;
import com.swj.mapper.TbpurchaseMapper;
import com.swj.service.GoodsService;
import com.swj.service.PurchaseDetalisService;
import com.swj.service.PurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<TbpurchaseMapper, TbPurchase> implements PurchaseService {
    private long total;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PurchaseDetalisService purchaseDetalisService;

    @Override
    public int addPurchase(TbPurchase purchase) {
        purchase.setCode("CGD" + System.currentTimeMillis());
        purchase.setIsRead(0); //添加之后未审核状态为0
        int i = baseMapper.insert(purchase);
        //拆分购物单的id列表
        String idList = purchase.getIdList();
        String numList = purchase.getNumList();
        String[] splitId = idList.split(",");
        String[] splitNum = numList.split(",");
        for (int j = 0; j < splitId.length; j++) {
            TbGoods goods = goodsService.getGoodsById(Integer.parseInt(splitId[j]));
            purchaseDetalisService.addPurchaseDetalis(purchase.getId(), Integer.parseInt(splitNum[j]), goods);
        }
        return i;
    }

    @Override
    public int updatePurchaseByRead(Integer id, Integer state) {
        TbPurchase purchase = baseMapper.selectById(id);
        purchase.setIsRead(state);
        return baseMapper.updateById(purchase);
    }

    @Override
    public int updatePurchase(TbPurchase purchase) {

        return 0;
    }

    /**
     * 更改采购单的阅读状态,当采购的商品入库,则更改阅读状态
     *
     * @param id
     * @return
     */
    public int updatePurchaseByRead(Integer id) {
        TbPurchase purchase = baseMapper.selectById(id);
        purchase.setIsRead(1);
        return baseMapper.updateById(purchase);
    }

    @Override
    public int deletePurchase(Integer id) {
        return 0;
    }

    @Override
    public TbPurchase getPurchaseById(Integer id) {
        return null;
    }

    @Override
    public List<TbPurchase> getPurchaseList(Integer page, Integer limit, TbPurchase purchase) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
        Page<TbPurchase> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        //todo 具体的查询条件在定
       /* if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }*/
        //降序排列,为了让新增的显示在前面方便查看
        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchase> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public int endPurchaseById(Integer purchaseId, int stateEnd) {
        TbPurchase purchase = getPurchaseById(purchaseId);
        purchase.setState(stateEnd);
        return  baseMapper.updateById(purchase);
    }

    @Override
    public List<TbPurchase> getPurchaseListByState(Integer page, Integer limit, Integer state) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
        Page<TbPurchase> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchase> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public long getTotal() {
        return total;
    }
}
