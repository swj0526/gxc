package com.swj.service;

import com.swj.entity.TbGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface GoodsService extends IService<TbGoods> {

    int addGoods(TbGoods goods);

    int updateGoods(TbGoods goods);

    TbGoods getGoodsById(Integer id);

    int deleteGoods(Integer id);

    List<TbGoods> getList(Integer page, Integer limit, GoodsVO goods);

    long getTotal();

    boolean exist(Integer warehouseId);
}
