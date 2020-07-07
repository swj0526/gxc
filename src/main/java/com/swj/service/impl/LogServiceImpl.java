package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbClient;
import com.swj.entity.TbEmployee;
import com.swj.entity.TbLog;
import com.swj.mapper.TblogMapper;
import com.swj.service.EmployeeService;
import com.swj.service.GoodsService;
import com.swj.service.GoodsWarnLogService;
import com.swj.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class LogServiceImpl extends ServiceImpl<TblogMapper, TbLog> implements LogService {
   private long total;

    @Autowired
    private EmployeeService employeeService;
    @Override
    public int addLog(TbLog log) {
       baseMapper.insert(log);
        TbEmployee employee = employeeService.getEmployeeById(log.getOperatorId());
        System.out.println(employee.getName());
        log.setName(employee.getName());
        return baseMapper.updateById(log);
    }

    @Override
    public List<TbLog> getLogList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbLog> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序
        Page<TbLog> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(keywords)) {
            queryWrapper.like("name", keywords).or().like("state", keywords).
                    or().like("remark", keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<TbLog> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    @Override
    public long getTotal() {
        return total;
    }
}
