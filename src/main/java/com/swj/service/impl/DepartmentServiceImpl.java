package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbDepartment;
import com.swj.entity.TbEmployee;
import com.swj.mapper.TbdepartmentMapper;
import com.swj.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.service.EmployeeService;
import com.swj.vo.ConditionalVO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DepartmentServiceImpl extends ServiceImpl<TbdepartmentMapper, TbDepartment> implements DepartmentService {
  private long total;

   @Autowired
   private EmployeeService employeeService;

    @Override
    public int addDepartment(TbDepartment department) {
        return baseMapper.insert(department);
    }

    @Override
    public int updateDepartment(TbDepartment department) {
        return baseMapper.updateById(department);
    }

    /**
     * 返回2,该部门下面存在员工,不可以删除
     * @param id
     * @return
     */
    @Override
    public int deleteDepartment(Integer id) {
        //判断该部门下面是否存在员工
        List<TbEmployee> employeeListByDepId = employeeService.getEmployeeListByDepId(id);
        if (employeeListByDepId.size()!=0){
            return 2;
        }
        return baseMapper.deleteById(id);
    }

    @Override
    public TbDepartment getDepartmentById(Integer id) {
        return  baseMapper.selectById(id);
    }

    @Override
    public List<TbDepartment> getSelectList() {
        List<TbDepartment> list = baseMapper.selectList(null);
        return list;
    }

    @Override
    public List<TbDepartment> getDepartmentList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbDepartment> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序排序
        Page<TbDepartment> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(keywords)) {
            queryWrapper.like("name", keywords).or().like("remark",keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<TbDepartment> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    @Override
    public long getTotal() {
        return total;
    }
}
