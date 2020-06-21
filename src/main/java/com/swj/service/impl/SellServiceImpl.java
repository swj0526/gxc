package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbSell;
import com.swj.entity.TbSellDetalis;
import com.swj.mapper.TbsellMapper;
import com.swj.service.GoodsService;
import com.swj.service.SellDetalisService;
import com.swj.service.SellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        sell.setCode("XSD" + System.currentTimeMillis());
        sell.setIsRead(0); //销售单生成之后未审核状态为0
        int i = baseMapper.insert(sell);
        //拆分销售单的id列表
        String idList = sell.getIdList();
        String numList = sell.getNumList();
        String[] splitId = idList.split(",");
        String[] splitNum = numList.split(",");
        for (int j = 0; j < splitId.length; j++) {
            TbGoods goods = goodsService.getGoodsById(Integer.parseInt(splitId[j]));
            detalisService.addSellDetalis(sell.getId(), Integer.parseInt(splitNum[j]), goods);
        }
        return i;
    }

    @Override
    public int updateSell(TbSell sell) {
        return 0;
    }

    @Override
    public int deleteSell(Integer id) {
        return 0;
    }

    @Override
    public TbSell getSellById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<TbSell> getSellList(Integer page, Integer limit, TbSell sell) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
        Page<TbSell> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
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
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
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
    public long getTotal() {
        return total;
    }
}
