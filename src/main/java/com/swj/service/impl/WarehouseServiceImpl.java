package com.swj.service.impl;

import com.swj.entity.TbWarehouse;
import com.swj.mapper.TbwarehouseMapper;
import com.swj.service.GoodsService;
import com.swj.service.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<TbwarehouseMapper, TbWarehouse> implements WarehouseService {
   private long total;
   @Autowired
   private GoodsService goodsService;
    @Override
    public int addWarehouse(TbWarehouse warehouse) {
        //todo 判断仓库的负责人员是否是仓库部门的人员

        return  baseMapper.insert(warehouse);
    }

    @Override
    public int updateWarehouse(TbWarehouse warehouse) {
        //todo 判断修改的负责人员是否是仓库部门的人员

        return  baseMapper.deleteById(warehouse);
    }

    /**
     * 返回2,代表该仓库下面有商品
     * @param id
     * @return
     */
    @Override
    public int deleteWarehouse(Integer id) {
        //todo,删除这个仓库,要判断这个仓库下面是否存在商品
        boolean exist = goodsService.exist(id);
        if(exist){
         int i=   baseMapper.deleteById(id);
         return i;
        }
        return  2;
    }

    @Override
    public TbWarehouse getWarehouseById(Integer id) {
        return null;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public List<TbWarehouse> getWarehouseList(Integer page, Integer limit, TbWarehouse warehouse) {
        return null;
    }
}
