package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbRole;
import com.swj.mapper.TbroleMapper;
import com.swj.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
public class RoleServiceImpl extends ServiceImpl<TbroleMapper, TbRole> implements RoleService {
  private Long total;
    @Override
    public int addRole(TbRole role) {
        return baseMapper.insert(role);
    }

    @Override
    public int updateRole(TbRole role) {
        return baseMapper.updateById(role);
    }

    @Override
    public int deleteRole(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<TbRole> getRoleList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbRole> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<TbRole> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String begin = vo.getBegin();
        String end = vo.getEnd();
        String keywords = vo.getKeywords();
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("remark", keywords);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbRole> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public TbRole getRoleById(int id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void setPerms(int id, String perms) {
        TbRole role = baseMapper.selectById(id);
        role.setPerms(perms);
        baseMapper.updateById(role);
    }

    @Override
    public List<TbRole> getSelectList() {
        return baseMapper.selectList(null);
    }
}
