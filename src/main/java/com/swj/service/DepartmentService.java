package com.swj.service;

import com.swj.entity.TbDepartment;
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
public interface DepartmentService extends IService<TbDepartment> {

    int addDepartment(TbDepartment department);

    int updateDepartment(TbDepartment department);

    int deleteDepartment(Integer id);

    TbDepartment getDepartmentById(Integer id);

    List<TbDepartment> getDepartmentList(Integer page, Integer limit, ConditionalVO vo);

    List<TbDepartment> getSelectList();

    long getTotal();
}
