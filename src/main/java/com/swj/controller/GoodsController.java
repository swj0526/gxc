package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbGoods;
import com.swj.service.GoodsService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import com.swj.vo.GoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api(description = "商品controller")
@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/addGoods")
    @ApiOperation("添加商品")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加商品")
    public Result addGoods(@RequestBody TbGoods goods){
        int i = goodsService.addGoods(goods);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }
    @PostMapping("/updateGoods")
    @ApiOperation("修改商品")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改商品")
    public Result updateGoods(@RequestBody TbGoods goods){
        int i =  goodsService.updateGoods(goods);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @PostMapping("/getGoodsById")
    @ApiOperation("根据id查询商品")
    public Result  getGoodsById(Integer id){
        TbGoods goods = goodsService.getGoodsById(id);
        return Result.success().data(goods);
    }
    @PostMapping("/deleteGoods")
    @ApiOperation("删除商品")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="删除商品")
    public Result deleteGoods(Integer id){
      int i =goodsService.deleteGoods(id);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }
    @PostMapping("/getList" )
    @ApiOperation("商品列表,分页")
    public Result getList(Integer page, Integer limit, @RequestBody ConditionalVO vo){
        List<TbGoods> list = goodsService.getList(page, limit, vo);
        return Result.success().listForPage(list, goodsService.getTotal());
    }
}

