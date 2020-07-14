package com.swj.service;

import com.swj.entity.TbGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.ConditionalVO;

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

    int updateGoodsByWarn(TbGoods goods);

    TbGoods getGoodsById(Integer id);
    TbGoods getGoodsByCode(String code);

    int deleteGoods(Integer id);

    List<TbGoods> getList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();

    boolean exist(Integer warehouseId);

    void checkInventory();
}
