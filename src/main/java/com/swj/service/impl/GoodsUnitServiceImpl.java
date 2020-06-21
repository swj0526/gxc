package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swj.entity.TbGoodsUnit;
import com.swj.mapper.TbgoodsunitMapper;
import com.swj.service.GoodsUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
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
public class GoodsUnitServiceImpl extends ServiceImpl<TbgoodsunitMapper, TbGoodsUnit> implements GoodsUnitService {

    /**
     * 返回所有的商品单位列表
     * @return
     */
    public List<TbGoodsUnit> getGoodsUnitList(){
        List<TbGoodsUnit> list = baseMapper.selectList(null);
        return list();
    }
}
