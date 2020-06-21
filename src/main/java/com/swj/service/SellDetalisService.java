package com.swj.service;

import com.swj.entity.TbGoods;
import com.swj.entity.TbSellDetalis;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface SellDetalisService extends IService<TbSellDetalis> {

    int addSellDetalis(Integer sellId, int num, TbGoods goods);

    void checkGoods(Integer page, Integer limit,Integer sellId, Map<String, Integer> map);

    List<TbSellDetalis> getDetalisBySellId(Integer page, Integer limit,Integer sellId);

    long getTotal();


}
