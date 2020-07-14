package com.swj.service;

import com.swj.entity.TbPurchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.ConditionalVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface PurchaseService extends IService<TbPurchase> {

    int addPurchase(TbPurchase purchase);

    int updatePurchase(TbPurchase purchase);

    int updatePurchaseByRead(Integer id,Integer state);

    int deletePurchase(Integer id);

    TbPurchase getPurchaseById(Integer id);

    List<TbPurchase> getPurchaseList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();

    int endPurchaseById(Integer purchaseId, int stateEnd);

    List<TbPurchase> getPurchaseListEnd(Integer page, Integer limit, ConditionalVO vo);

    List<TbPurchase> getPurchaseListError(Integer page, Integer limit, ConditionalVO vo);

    List<TbPurchase> getPurchaseListDefault(Integer page, Integer limit, ConditionalVO vo);

    void setEmp(Integer empId, Integer purchaseId);
}
