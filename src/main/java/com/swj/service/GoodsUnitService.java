package com.swj.service;

import com.swj.entity.TbGoodsUnit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface GoodsUnitService extends IService<TbGoodsUnit> {
    List<TbGoodsUnit> getGoodsUnitList();

    String getGoodsUnitById(Integer id);
}
