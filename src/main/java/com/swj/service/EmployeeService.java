package com.swj.service;

import com.swj.entity.TbEmployee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.EmployeeVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface EmployeeService extends IService<TbEmployee> {

    int addEmployee(TbEmployee employee);

    int updateEmployee(TbEmployee employee);

    int deleteEmployee(Integer id);

    TbEmployee getEmployeeById(Integer id);

    List<TbEmployee> getEmployeeList(Integer page, Integer limit, EmployeeVO vo);

    long getTotal();

    List<TbEmployee> getEmployeeListByDepId(Integer departmentId);

    int updatePassword(String password);

    Integer changeState(Boolean state, Integer employeeId);

    List<TbEmployee> getWarehouseList();

    List<TbEmployee> getSelectList();
}
