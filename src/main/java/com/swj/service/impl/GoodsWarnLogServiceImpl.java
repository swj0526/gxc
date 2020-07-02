package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbCustomer;
import com.swj.entity.TbGoods;
import com.swj.entity.TbGoodsWarnLog;
import com.swj.mapper.TbgoodswarnlogMapper;
import com.swj.service.GoodsService;
import com.swj.service.GoodsWarnLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Service
public class GoodsWarnLogServiceImpl extends ServiceImpl<TbgoodswarnlogMapper, TbGoodsWarnLog> implements GoodsWarnLogService {
    private long total;
    @Autowired
    private GoodsService goodsService;
    @Override
    public int addGoodsWarnLog(TbGoodsWarnLog goodsWarnLog) {
        if(goodsWarnLog.getState()==TbGoodsWarnLog.STATE_ERROR){
            TbGoods goods = goodsService.getGoodsById(goodsWarnLog.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity()-goodsWarnLog.getNum());
            goodsService.updateGoodsByWarn(goods);
        }
        return baseMapper.insert(goodsWarnLog);
    }

    @Override
    public List<TbGoodsWarnLog> getGoodsWarnLogList(Integer page, Integer limit, ConditionalVO vo) {

        QueryWrapper<TbGoodsWarnLog> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序排序
        Page<TbGoodsWarnLog> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象

        String end = vo.getEnd();
        String begin = vo.getBegin();

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam,queryWrapper);
        List<TbGoodsWarnLog> list = pageParam.getRecords();//数据
        for(TbGoodsWarnLog log:list){
            Integer goodsId = log.getGoodsId();
            TbGoods goods = goodsService.getGoodsById(goodsId);
            log.setCode(goods.getCode());
            log.setName(goods.getName());
        }
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public long getTotal() {
        return total;
    }
}
