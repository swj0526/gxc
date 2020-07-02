package com.swj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.entity.TbGoods;
import com.swj.entity.TbGoodsSpecimen;
import com.swj.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-15
 */
public interface GoodsSpecimenService extends IService<TbGoodsSpecimen> {
    int addGoodsSpecimen(TbGoodsSpecimen goodsSpecimen);

    int updateGoodsSpecimen(TbGoodsSpecimen goodsSpecimen);

    TbGoodsSpecimen getGoodsSpecimenById(Integer id);

    int deleteGoodsSpecimen(Integer id);

    List<TbGoodsSpecimen> getList(Integer page, Integer limit, TbGoodsSpecimen goodsSpecimen);

    long getTotal();

    void instock(TbGoods goods);
}
