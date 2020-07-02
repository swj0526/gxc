package com.swj.service;

import com.swj.entity.TbGoodsSpecimen;
import com.swj.entity.TbGoodsType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.tree.OneSubject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface GoodsTypeService extends IService<TbGoodsType> {

    int addGoodsType(TbGoodsType goodsType);

    int updateGoodsType(TbGoodsType goodsType);

    int deleteGoodsType(Integer id);

    List<OneSubject> getGoodsTypeList();

    TbGoodsType getGoodsTypeById(Integer id);

    List<TbGoodsType> getList();

    String getParent(Integer id);

    int getPId(Integer id);
}
