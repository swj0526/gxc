package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbClient;
import com.swj.entity.TbEmployee;
import com.swj.entity.TbWarehouse;
import com.swj.mapper.TbwarehouseMapper;
import com.swj.service.EmployeeService;
import com.swj.service.GoodsService;
import com.swj.service.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
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
    @Autowired
    private EmployeeService employeeService;

    @Override
    public int addWarehouse(TbWarehouse warehouse) {
        return baseMapper.insert(warehouse);
    }

    @Override
    public int updateWarehouse(TbWarehouse warehouse) {
        return baseMapper.updateById(warehouse);
    }

    /**
     * 返回2,代表该仓库下面有商品
     *
     * @param id
     * @return
     */
    @Override
    public int deleteWarehouse(Integer id) {
        //todo,删除这个仓库,要判断这个仓库下面是否存在商品
        boolean exist = goodsService.exist(id);
        if (exist) {
            int i = baseMapper.deleteById(id);
            return i;
        }
        return 2;
    }

    @Override
    public TbWarehouse getWarehouseById(Integer id) {
        return  baseMapper.selectById(id);
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public List<TbWarehouse> getSelectList() {
       return baseMapper.selectList(null);
    }

    @Override
    public List<TbWarehouse> getWarehouseList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbWarehouse> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序
        Page<TbWarehouse> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(keywords)) {
            queryWrapper.like("name", keywords).
                    or().like("address", keywords).or().like("remark", keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<TbWarehouse> list = pageParam.getRecords();//数据
        for(TbWarehouse warehouse:list){
            Integer empId = warehouse.getEmpId();
            TbEmployee employee = employeeService.getEmployeeById(empId);
            String phone = employee.getPhone();
            warehouse.setPhone(phone);
        }
        total = pageParam.getTotal();
        return list;
    }
}
