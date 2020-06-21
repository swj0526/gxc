package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.*;
import com.swj.mapper.TbselldetalisMapper;
import com.swj.service.SellDetalisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public  List<TbSellDetalis> getDetalisBySellId(Integer page, Integer limit,Integer sellId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("sell_id",sellId);
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
        Page<TbSellDetalis> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象

        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSellDetalis> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public void checkGoods(Integer page, Integer limit,Integer sellId, Map<String, Integer> map) {
        List<TbSellDetalis> list = getDetalisBySellId(page,limit,sellId);
        //遍历更改所有销售商品详情的出库状态,
        for (TbSellDetalis detalis : list) {
            Integer id = detalis.getId();
            detalis.setIsEnter(map.get(id));
        }
        sellService.updateSellByRead(sellId, TbPurchase.STATE_READ);
    }

    @Override
    public int addSellDetalis(Integer sellId, int num, TbGoods goods) {
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
}
