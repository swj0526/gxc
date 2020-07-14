package com.swj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.entity.TbCustomer;
import com.swj.entity.TbGoods;
import com.swj.entity.TbGoodsInAndOut;
import com.swj.entity.TbGoodsWarnLog;
import com.swj.mapper.TbGoodsInAndOutMapper;

import com.swj.service.GoodsInAndOutService;
import com.swj.service.GoodsService;
import com.swj.vo.ConditionalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GoodsInAndOutServiceImpl extends ServiceImpl<TbGoodsInAndOutMapper, TbGoodsInAndOut> implements GoodsInAndOutService {
   private long total;
   @Autowired
   private GoodsService goodsService;

    @Override
    public int addGoodsInAndOut(TbGoodsInAndOut goodsInAndOut) {

        return baseMapper.insert(goodsInAndOut);
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public List<TbGoodsInAndOut> getGoodsInAndOutList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbGoodsInAndOut> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序排序
        Page<TbGoodsInAndOut> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        baseMapper.selectPage(pageParam,queryWrapper);
        List<TbGoodsInAndOut> list = pageParam.getRecords();//数据
        for(TbGoodsInAndOut log:list){
            Integer goodsId = log.getGoodsId();
            TbGoods goods = goodsService.getGoodsById(goodsId);
            log.setCode(goods.getCode());
            log.setName(goods.getName());
        }
        total= pageParam.getTotal();
        return list;
    }
}
