package com.swj.controller;


import com.swj.entity.TbGoodsUnit;
import com.swj.service.GoodsUnitService;
import com.swj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api(description = "商品单位controller")
@RestController
@RequestMapping("/goodsUnit")
@CrossOrigin //跨域
public class GoodsUnitController {

   @Autowired
   private GoodsUnitService goodsUnitService;

    @ApiOperation("商品单位列表")
    @PostMapping("/getList")
    public Result getList(){
        List<TbGoodsUnit> list = goodsUnitService.list();
        return Result.success().data(list);
    }
    @ApiOperation("根据id返回单位")
    @PostMapping("/getGoodsUnitById")
    public Result getGoodsUnitById(Integer id){
        return Result.success().data(goodsUnitService.getGoodsUnitById(id));
    }

}

