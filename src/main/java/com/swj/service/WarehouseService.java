package com.swj.service;

import com.swj.entity.TbWarehouse;
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
public interface WarehouseService extends IService<TbWarehouse> {

    int addWarehouse(TbWarehouse warehouse);

    int updateWarehouse(TbWarehouse warehouse);

    int deleteWarehouse(Integer id);

    TbWarehouse getWarehouseById(Integer id);

    long getTotal();

    List<TbWarehouse> getWarehouseList(Integer page, Integer limit, TbWarehouse warehouse);

}
