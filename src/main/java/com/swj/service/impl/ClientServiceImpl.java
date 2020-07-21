package com.swj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbClient;
import com.swj.entity.TbGoods;
import com.swj.mapper.TbclientMapper;
import com.swj.service.ClientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
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
public class ClientServiceImpl extends ServiceImpl<TbclientMapper, TbClient> implements ClientService {
    private long total;

    @Override
    public int deleteClient(Integer id) {
        TbClient clientDB = getClientById(id);
        if (clientDB == null) {
            return 0;
        }
        return baseMapper.deleteById(id);
    }

    @Override
    public TbClient getClientById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public List<TbClient> getClientList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbClient> queryWrapper = new QueryWrapper<>();//封装一个条件对象
        queryWrapper.orderByDesc("create_time");//条件按照降序
        Page<TbClient> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if (!StringUtils.isEmpty(keywords)) {
            queryWrapper.like("name", keywords).or().like("phone", keywords).
                    or().like("address", keywords).or().like("remark", keywords);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<TbClient> list = pageParam.getRecords();//数据
        total = pageParam.getTotal();
        return list;
    }

    @Override
    public int updateClient(TbClient client) {
        TbClient clientDB = getClientById(client.getId());
        if (clientDB == null) {
            return 0;
        }
        return baseMapper.updateById(client);
    }

    @Override
    public int addClient(TbClient client) {
        //todo 添加时候后台做的校验
        return baseMapper.insert(client);
    }

    @Override
    public List<TbClient> selectList() {
        return baseMapper.selectList(null);
    }
}
