package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbClient;
import com.swj.entity.TbCustomer;
import com.swj.mapper.TbcustomerMapper;
import com.swj.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class CustomerServiceImpl extends ServiceImpl<TbcustomerMapper, TbCustomer> implements CustomerService {
private long total;
    @Override
    public int addCustomer(TbCustomer customer) {
        return baseMapper.insert(customer);
    }

    @Override
    public int updateCustomer(TbCustomer customer) {
        TbCustomer customerDB = getCustomerById(customer.getId());
        if(customerDB==null){
            return 0;
        }
      return   baseMapper.updateById(customer);
    }

    @Override
    public int deleteCustomer(Integer id) {
        TbCustomer customerDB = getCustomerById(id);
        if(customerDB==null){
            return 0;
        }
        return baseMapper.deleteById(id);
    }

    @Override
    public List<TbCustomer> getCustomerList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbCustomer> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序排序
        Page<TbCustomer> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(keywords)) {
            queryWrapper.like("name", keywords).or().like("phone", keywords).
                    or().like("address", keywords).or().like("remark", keywords).
                    or().like("contact",keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam,queryWrapper);
        List<TbCustomer> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public TbCustomer getCustomerById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<TbCustomer> getSelectList() {
        return baseMapper.selectList(null);
    }
}
