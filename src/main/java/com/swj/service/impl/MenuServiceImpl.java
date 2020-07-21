package com.swj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swj.entity.TbGoodsType;
import com.swj.entity.TbMenu;
import com.swj.mapper.TbgoodstypeMapper;
import com.swj.mapper.TbmenuMapper;
import com.swj.service.GoodsSpecimenService;
import com.swj.service.GoodsTypeService;
import com.swj.service.MenuService;
import com.swj.vo.tree.OneSubject;
import com.swj.vo.tree.TwoSubject;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MenuServiceImpl extends ServiceImpl<TbmenuMapper, TbMenu> implements MenuService {
   @Autowired
   private GoodsSpecimenService goodsSpecimenService;

    @Override
    public List<OneSubject> getMenuList() {
        QueryWrapper<TbMenu> queryWrapper = new QueryWrapper();
        queryWrapper.eq("p_id",0);
        //返回列表pid为0的一级分类
        List<TbMenu> list = baseMapper.selectList(queryWrapper);
        List<OneSubject> o_list = new ArrayList<>();
         for(TbMenu menu:list){
             OneSubject oneSubject = new OneSubject();
             oneSubject.setId(menu.getId().toString());
             oneSubject.setLabel(menu.getName());
             oneSubject.setValue(menu.getId().toString());
             oneSubject.setPId("0");
             QueryWrapper query = new QueryWrapper();
             query.eq("p_id", menu.getId());
             oneSubject.setPath(menu.getUrl());

              //返回列表的二级分类
             List<TbMenu> c_list = baseMapper.selectList(query);
             List<TwoSubject> t_list = new ArrayList<>();
             for(TbMenu c:c_list){
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(c.getId().toString());
                twoSubject.setLabel(c.getName());
                twoSubject.setPId(oneSubject.getId());
                twoSubject.setValue(c.getId().toString());
                twoSubject.setPath(c.getPath());
                twoSubject.setComponent(c.getUrl());
                 t_list.add(twoSubject);
             }
             oneSubject.setChildren(t_list);
             o_list.add(oneSubject);
         }
        return o_list;
    }


}
