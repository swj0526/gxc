package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbClient;
import com.swj.service.ClientService;
import com.swj.service.MenuService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import com.swj.vo.GoodsVO;
import com.swj.vo.tree.OneSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api(description = "菜单controller")
@RestController
@RequestMapping("/menu")
@CrossOrigin //跨域
public class MenuController {
    @Autowired
    private MenuService menuService;


    @ApiOperation("返回菜单列表,tree")
    @PostMapping("/getMenuList")
    public Result getMenuList() {
        List<OneSubject> list = menuService.getMenuList();
        return Result.success().data(list);
    }
}

