package com.swj.service;

import com.swj.entity.TbGoods;
import com.swj.entity.TbPurchaseDetalis;
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
public interface PurchaseDetalisService extends IService<TbPurchaseDetalis> {

    void  addPurchaseDetalis(Integer purchaseId,Integer num, TbGoods goods);

    void checkGoods(Integer page, Integer limit,Integer purchaseId, Map<String,Integer> map);

    long getTotal();

    List<TbPurchaseDetalis> getDetalisByPurchaseId(Integer page,Integer limit,Integer purchaseId);
}
