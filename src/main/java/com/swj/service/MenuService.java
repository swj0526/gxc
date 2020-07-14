package com.swj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.entity.TbMenu;
import com.swj.vo.tree.OneSubject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface MenuService extends IService<TbMenu> {


    List<OneSubject> getMenuList();


}
