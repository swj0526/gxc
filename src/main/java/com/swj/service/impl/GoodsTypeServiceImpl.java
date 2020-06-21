package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swj.entity.TbGoodsType;
import com.swj.mapper.TbgoodstypeMapper;
import com.swj.service.GoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.vo.tree.OneSubject;
import com.swj.vo.tree.TwoSubject;
import org.springframework.stereotype.Service;

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
public class GoodsTypeServiceImpl extends ServiceImpl<TbgoodstypeMapper, TbGoodsType> implements GoodsTypeService {

    @Override
    public int addGoodsType(TbGoodsType goodsType) {
        return baseMapper.insert(goodsType);
    }

    @Override
    public int updateGoodsType(TbGoodsType goodsType) {
        return baseMapper.updateById(goodsType);
    }

    /**
     * 返回值为2,该一级分类下面存在二级分类
     * @param id
     * @return
     */
    @Override
    public int deleteGoodsType(Integer id) {
        //判断删除的下面是否有二级分类
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",id);
        List<TbGoodsType> list = baseMapper.selectList(queryWrapper);
        if(list.size()!=0){
            return 2;
        }
        return  baseMapper.deleteById(id);
    }

    @Override
    public List<OneSubject> getGoodsTypeList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pId",0);
        //返回列表pid为0的一级分类
        List<TbGoodsType> list = baseMapper.selectList(queryWrapper);
        List<OneSubject> o_list = new ArrayList<>();
         for(TbGoodsType type:list){
             OneSubject oneSubject = new OneSubject();
             oneSubject.setId(type.getId().toString());
             oneSubject.setTitle(type.getName());
             QueryWrapper query = new QueryWrapper();
              queryWrapper.eq("pId", type.getId());
              //返回列表的二级分类
             List<TbGoodsType> c_list = baseMapper.selectList(query);
             List<TwoSubject> t_list = new ArrayList<>();
             for(TbGoodsType c:c_list){
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(c.getId().toString());
                twoSubject.setTitle(c.getName());
                 t_list.add(twoSubject);
             }
             oneSubject.setChildren(t_list);
             o_list.add(oneSubject);
         }
        return o_list;
    }

    @Override
    public TbGoodsType getGoodsTypeById(Integer id) {
        return baseMapper.selectById(id);
    }
}
