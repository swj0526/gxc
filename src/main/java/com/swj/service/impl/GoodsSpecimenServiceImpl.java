package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.entity.TbGoodsSpecimen;
import com.swj.mapper.TbgoodsspecimenMapper;
import com.swj.service.GoodsService;
import com.swj.service.GoodsSpecimenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2020-06-15
 */
@Service
public class GoodsSpecimenServiceImpl extends ServiceImpl<TbgoodsspecimenMapper, TbGoodsSpecimen> implements GoodsSpecimenService {
   private long total;
   @Autowired
   private GoodsService goodsService;

    @Override
    public int addGoodsSpecimen(TbGoodsSpecimen goodsSpecimen) {
        String code ="SP"+System.currentTimeMillis();
        goodsSpecimen.setCode(code);
        goodsSpecimen.setIsEnter(TbGoodsSpecimen.ENTER_NO); //设置状态为未入库
        return baseMapper.insert(goodsSpecimen);
    }

    @Override
    public int updateGoodsSpecimen(TbGoodsSpecimen goodsSpecimen) {
        TbGoodsSpecimen goodsSpecimenDB = getGoodsSpecimenById(goodsSpecimen.getId());
        if(goodsSpecimen==null){
            return 0;
        }
        return  baseMapper.updateById(goodsSpecimen);
    }

    @Override
    public TbGoodsSpecimen getGoodsSpecimenById(Integer id) {
        return  baseMapper.selectById(id);
    }

    @Override
    public int deleteGoodsSpecimen(Integer id) {
        TbGoodsSpecimen goodsSpecimenDB = getGoodsSpecimenById(id);
        if(goodsSpecimenDB==null){
            return 0;
        }
        return  baseMapper.deleteById(id);
    }

    @Override
    public List<TbGoodsSpecimen> getList(Integer page, Integer limit, TbGoodsSpecimen goodsSpecimen) {
        QueryWrapper<TbGoodsSpecimen> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.eq("is_enter",TbGoodsSpecimen.ENTER_NO);
        Page pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String name = goodsSpecimen.getName();
        String code = goodsSpecimen.getCode();
       /* String begin = goods.getBegin();
        String end = goods.getEnd();*/
           //todo 条件带完善
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(code)) {
            queryWrapper.like("code", code);
        }
/*
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }*/
        //降序排列,为了让新增的显示在前面方便查看
        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    /**
     * 商品入库
     * @param goods
     */
    @Override
    public void instock(TbGoods goods) {
        String code =goods.getCode();
        QueryWrapper<TbGoodsSpecimen> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.eq("code",code);
        TbGoodsSpecimen goodsSpecimen = baseMapper.selectOne(queryWrapper);
        goodsSpecimen.setIsEnter(TbGoodsSpecimen.ENTER_YES);//商品样品表更改为已入库
        baseMapper.updateById(goodsSpecimen); //更改商品样品表
        Integer num = goods.getNum();
        goods.setInventoryQuantity(num);
        goodsService.addGoods(goods);//商品已入库
    }

    @Override
    public long getTotal() {
        return total;
    }
}
