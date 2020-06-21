package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbGoods;
import com.swj.mapper.TbgoodsMapper;
import com.swj.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.GoodsVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
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
public class GoodsServiceImpl extends ServiceImpl<TbgoodsMapper, TbGoods> implements GoodsService {
    private long total;//条件查询的分页总条数
    @Override
    public TbGoods getGoodsById(Integer id) {
        TbGoods goods = baseMapper.selectById(id);
        return goods;
    }

    @Override
    public List<TbGoods> getList(Integer page, Integer limit, GoodsVO goods) {
        QueryWrapper<TbGoods> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByAsc("create_time");//条件按照升序排序
        Page pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String name = goods.getName();
        String code = goods.getCode();
        String begin = goods.getBegin();
        String end = goods.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(code)) {
            queryWrapper.like("code", code);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        //降序排列,为了让新增的显示在前面方便查看
        queryWrapper.orderByDesc("create_time");
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;

    }

    @Override
    public boolean exist(Integer warehouseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("warehouse_id",warehouseId);
        List<TbGoods> list = baseMapper.selectList(queryWrapper);
        if(list.size()==0){
            return true;
        }
        return false;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public int deleteGoods(Integer id) {
        TbGoods goodsDB = getGoodsById(id);
        if(goodsDB==null){
            return 0;
        }
        return  baseMapper.deleteById(id);

    }

    @Override
    public int updateGoods(TbGoods goods) {
        TbGoods goodsDB = getGoodsById(goods.getId());
        if(goodsDB==null){
            return 0;
        }
        return  baseMapper.updateById(goods);
    }

    @Override
    public int addGoods(TbGoods goods) {
        String code ="SP"+System.currentTimeMillis();
        goods.setCode(code);
        return baseMapper.insert(goods);
    }
}
