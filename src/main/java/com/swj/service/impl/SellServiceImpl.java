package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbSell;
import com.swj.mapper.TbsellMapper;
import com.swj.service.GoodsService;
import com.swj.service.SellDetalisService;
import com.swj.service.SellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
public class SellServiceImpl extends ServiceImpl<TbsellMapper, TbSell> implements SellService {
    private long total;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellDetalisService detalisService;


    @Override
    public int addSell(TbSell sell) {
       sell.setIsRead(TbSell.STATE_DEFAULT);
        return  baseMapper.insert(sell);

    }

    @Override
    public int updateSell(TbSell sell) {
        return baseMapper.updateById(sell);
    }

    @Override
    public int deleteSell(Integer id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public TbSell getSellById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<TbSell> getSellList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbSell> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<TbSell> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String begin = vo.getBegin();
        String  end =   vo.getEnd();
        List<Integer> rlist = new ArrayList<>();
        rlist.add(TbSell.STATE_DEFAULT);
        rlist.add(TbSell.STATE_OUT);
        rlist.add(TbSell.STATE_SEND);
        queryWrapper.in("is_read", rlist);
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("code", keywords)
                    .or().like("model", keywords);
        }
       if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSell> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    /**
     * 历史销售单的展示
     * @param page
     * @param limit
     * @param vo
     * @return
     */
    @Override
    public List<TbSell> getSellListEnd(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbSell> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<TbSell> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String begin = vo.getBegin();
        String  end =   vo.getEnd();
        List<Integer> rlist = new ArrayList<>();
        rlist.add(TbSell.STATE_END);
        rlist.add(TbSell.STATE_ERROR);
        queryWrapper.in("is_read", rlist);
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("code", keywords)
                    .or().like("model", keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSell> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    @Override
    public List<TbSell> getSellListError(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbSell> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<TbSell> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String begin = vo.getBegin();
        String  end =   vo.getEnd();
        queryWrapper.in("is_read", TbSell.STATE_ERROR);
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("code", keywords)
                    .or().like("model", keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSell> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;

    }

    @Override
    public List<TbSell> getSellListDefault(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbSell> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<TbSell> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String begin = vo.getBegin();
        String  end =   vo.getEnd();
        queryWrapper.in("is_read", TbSell.STATE_DEFAULT);
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("code", keywords)
                    .or().like("model", keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSell> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    @Override
    public int endSellById(Integer sellId, int stateEnd) {
        TbSell sell = getSellById(sellId);
        sell.setState(stateEnd);
        return baseMapper.updateById(sell);
    }

    @Override
    public List<TbSell> getSellListByState(Integer page, Integer limit, Integer state) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("state", state);
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        Page<TbSell> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象

        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbSell> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    //更改销售单的商品的阅读状态
    @Override
    public int updateSellByRead(Integer sellId, Integer state) {
        TbSell sell = baseMapper.selectById(sellId);
        sell.setIsRead(state);
        return baseMapper.updateById(sell);
    }

    @Override
    public void setEmp(Integer empId, Integer sellId) {
        TbSell sell = baseMapper.selectById(sellId);
        sell.setEmpId(empId);
        sell.setIsRead(TbPurchase.STATE_READ);
        baseMapper.updateById(sell);
    }

    @Override
    public long getTotal() {
        return total;
    }
}
