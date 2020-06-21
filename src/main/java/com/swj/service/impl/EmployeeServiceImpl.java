package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.base.SC;
import com.swj.entity.TbClient;
import com.swj.entity.TbEmployee;
import com.swj.mapper.TbemployeeMapper;
import com.swj.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.EmployeeVO;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
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
public class EmployeeServiceImpl extends ServiceImpl<TbemployeeMapper, TbEmployee> implements EmployeeService {
    private long total;

    /**
     * 添加员工,添加的时候,密码为默认123456,
     *
     * @param employee
     * @return
     */
    @Override
    public int addEmployee(TbEmployee employee) {
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        //todo 账号的状态暂且未设置
        employee.setPassword(password);//初始化密码为123456
        return baseMapper.insert(employee);
    }

    @Override
    public int updateEmployee(TbEmployee employee) {
        return baseMapper.updateById(employee);
    }

    @Override
    public int deleteEmployee(Integer id) {
        //todo 删除员工之前,要判断该员工是否负责其他任务,比如,这个员工负责的订单未完成,不可删除
        return baseMapper.deleteById(id);
    }

    @Override
    public TbEmployee getEmployeeById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<TbEmployee> getEmployeeList(Integer page, Integer limit, EmployeeVO vo) {
        QueryWrapper<TbEmployee> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        Page<TbEmployee> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        int departmentId = vo.getDepartmentId();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(keywords)) {
            queryWrapper.like("name", keywords).or().like("phone", keywords)
                    .or().like("remark", keywords);
        }
        if (departmentId != 0) {
            queryWrapper.eq("department_id", departmentId);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<TbEmployee> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;

    }

    @Override
    public void changeState(Integer state, Integer employeeId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", employeeId);
        TbEmployee employee = baseMapper.selectOne(queryWrapper);
        employee.setState(TbEmployee.STATE_OPEN);//状态更改
        String password = DigestUtils.md5DigestAsHex((employee.getPhone() + "123456").getBytes());
        employee.setPassword(password);
        baseMapper.updateById(employee);

    }

    @Override
    public int updatePassword(String password) {
        String newPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        //获取当前是谁登录的
        String key = SC.SESSION_KEY;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", key);
        TbEmployee employee = baseMapper.selectOne(queryWrapper);
        employee.setPassword(newPassword);
        return baseMapper.updateById(employee);
    }

    @Override
    public List<TbEmployee> getEmployeeListByDepId(Integer departmentId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("department_id", departmentId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public long getTotal() {
        return total;
    }
}
