package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbPurchaseDetalis;
import com.swj.mapper.TbpurchasedetalisMapper;
import com.swj.service.PurchaseDetalisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.service.PurchaseService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public void addPurchaseDetalis(Integer purchaseId, Integer num, TbGoods goods) {
        TbPurchaseDetalis detalis = new TbPurchaseDetalis();
        detalis.setPurchaseId(purchaseId);
        detalis.setNum(num);
        detalis.setCode(goods.getCode());
        detalis.setName(goods.getName());
        detalis.setModel(goods.getModel());
        detalis.setPurchasingPrice(goods.getPurchasingPrice());
        detalis.setSellingPrice(goods.getSellingPrice());
        detalis.setIsEnter(0);
        baseMapper.insert(detalis);
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public List<TbPurchaseDetalis> getDetalisByPurchaseId(Integer page,Integer limit,Integer purchaseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
        Page pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        queryWrapper.eq("purchase_id", purchaseId);
        //找到当前采购单下采购的商品
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List list = pageParam.getRecords();//数据
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
        List<TbPurchaseDetalis> list = getDetalisByPurchaseId(page,limit,purchaseId);
        //遍历更改所有采购商品详情的入库状态,
        for (TbPurchaseDetalis detalis : list) {
            Integer id = detalis.getId();
            detalis.setIsEnter(map.get(id));
        }
        purchaseService.updatePurchaseByRead(purchaseId, TbPurchase.STATE_READ);


    }
}