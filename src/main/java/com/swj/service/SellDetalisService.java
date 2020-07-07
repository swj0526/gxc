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

    int addSellDetails(Integer sellId, int num, TbGoods goods);

    void checkGoods(Integer page, Integer limit,Integer sellId, Map<String, Integer> map);

    List<TbSellDetalis> getDetailsBySellId(Integer page, Integer limit, Integer sellId);

    long getTotal();

    int addDetails(String idList, String numList);

    int updateDetails(String idList, String numList, Integer purchaseId);

}
