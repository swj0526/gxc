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


    void checkGoods(Integer page, Integer limit,Integer purchaseId, Map<String,Integer> map);

    long getTotal();

    List<TbPurchaseDetalis> getDetailsByPurchaseId(Integer page, Integer limit, Integer purchaseId);

    void deleteDetailsListByPurchaseId( Integer purchaseId);

    int addDetails(String idList,String numList);

    int updateDetails(String idList, String numList,Integer purchaseId);
}
