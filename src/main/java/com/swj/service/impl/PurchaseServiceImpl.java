package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swj.entity.TbPurchase;
import com.swj.mapper.TbpurchaseMapper;
import com.swj.service.GoodsService;
import com.swj.service.PurchaseDetalisService;
import com.swj.service.PurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.ConditionalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
public class PurchaseServiceImpl extends ServiceImpl<TbpurchaseMapper, TbPurchase> implements PurchaseService {
    private long total;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PurchaseDetalisService purchaseDetalisService;

    @Override
    public int addPurchase(TbPurchase purchase) {
        purchase.setIsRead(TbPurchase.STATE_DEFAULT); //添加之后未审核状态为0
        int i = baseMapper.insert(purchase);
        return i;
    }

    @Override
    public int updatePurchaseByRead(Integer id, Integer state) {
        TbPurchase purchase = baseMapper.selectById(id);
        purchase.setIsRead(state);
        return baseMapper.updateById(purchase);
    }

    @Override
    public int updatePurchase(TbPurchase purchase) {
      return   baseMapper.updateById(purchase);
    }

    /**
     * 更改采购单的阅读状态,当采购的商品入库,则更改阅读状态
     *
     * @param id
     * @return
     */
    public int updatePurchaseByRead(Integer id) {
        TbPurchase purchase = baseMapper.selectById(id);
        //TODO
        purchase.setIsRead(TbPurchase.STATE_READ);
        return baseMapper.updateById(purchase);
    }

    @Override
    public int deletePurchase(Integer id) {
        purchaseDetalisService.deleteDetailsListByPurchaseId(id);
        return baseMapper.deleteById(id);
    }

    @Override
    public TbPurchase getPurchaseById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<TbPurchase> getPurchaseList(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbPurchase> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        Page<TbPurchase> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String begin = vo.getBegin();
        String end = vo.getEnd();
        String keywords = vo.getKeywords();
        List<Integer> rlist = new ArrayList<>();
        rlist.add(TbPurchase.STATE_DEFAULT);
        rlist.add(TbPurchase.STATE_READ);
        rlist.add(TbPurchase.STATE_INTO);

        queryWrapper.in("is_read", rlist);
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("code", keywords)
                    .or().like("model", keywords);
        }

         if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        //降序排列,为了让新增的显示在前面方便查看
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchase> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public List<TbPurchase> getPurchaseListEnd(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbPurchase> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<TbPurchase> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        String begin = vo.getBegin();
        String end = vo.getEnd();
        String keywords = vo.getKeywords();
        List<Integer> rlist = new ArrayList<>();
        rlist.add(TbPurchase.STATE_END);
        rlist.add(TbPurchase.STATE_NO);
        queryWrapper.in("is_read", rlist);
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).
                    or().like("code", keywords)
                    .or().like("model", keywords);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchase> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public int endPurchaseById(Integer purchaseId, int stateEnd) {
        TbPurchase purchase = getPurchaseById(purchaseId);
        purchase.setState(stateEnd);
        return  baseMapper.updateById(purchase);
    }

    @Override
    public List<TbPurchase> getPurchaseListDefault(Integer page, Integer limit, ConditionalVO vo) {
        QueryWrapper<TbPurchase> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        queryWrapper.eq("is_read",TbPurchase.STATE_DEFAULT);
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).or().like("code", keywords).or().like("model", keywords);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        Page<TbPurchase> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchase> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public List<TbPurchase> getPurchaseListError(Integer page, Integer limit,ConditionalVO vo) {
        QueryWrapper<TbPurchase> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");//条件按照升序排序
        queryWrapper.eq("is_read",TbPurchase.STATE_NO);
        String keywords = vo.getKeywords();
        String end = vo.getEnd();
        String begin = vo.getBegin();
        if(!StringUtils.isEmpty(keywords)){
            queryWrapper.like("name", keywords).or().like("code", keywords).or().like("model", keywords);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge(" create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }
        Page<TbPurchase> pageParam = new Page<>(page, limit);//把分页的条件封装成一个对象
        baseMapper.selectPage(pageParam, queryWrapper); //按照分页跟条件去查找数据
        List<TbPurchase> list = pageParam.getRecords();//数据
        total= pageParam.getTotal();
        return list;
    }

    @Override
    public void setEmp(Integer empId, Integer purchaseId) {
        TbPurchase purchase = baseMapper.selectById(purchaseId);
        purchase.setEmpId(empId);
        purchase.setIsRead(TbPurchase.STATE_READ);
        baseMapper.updateById(purchase);
    }

    @Override
    public long getTotal() {
        return total;
    }
}
