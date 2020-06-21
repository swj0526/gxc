package com.swj.service;

import com.swj.entity.TbCustomer;
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
public interface CustomerService extends IService<TbCustomer> {

    int addCustomer(TbCustomer customer);

    int updateCustomer(TbCustomer customer);

    int deleteCustomer(Integer id);

    List<TbCustomer> getCustomerList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();

    TbCustomer getCustomerById(Integer id);
}
