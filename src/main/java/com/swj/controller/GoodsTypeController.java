package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbGoodsSpecimen;
import com.swj.entity.TbGoodsType;
import com.swj.service.GoodsService;
import com.swj.service.GoodsTypeService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.tree.OneSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api(description = "商品类别的controller")
@RestController
@RequestMapping("/goodsType")
@CrossOrigin
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    @ApiOperation("新增商品类别")
    @PostMapping("/addGoodsType")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="新增商品类别")
    public Result addGoodsType(@RequestBody TbGoodsType goodsType) {
        int i = goodsTypeService.addGoodsType(goodsType);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @ApiOperation("修改商品类别")
    @PostMapping("/updateGoodsType")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改商品类别")
    public Result updateGoodsType(@RequestBody TbGoodsType goodsType) {
        int i = goodsTypeService.updateGoodsType(goodsType);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @ApiOperation("删除商品类别")
    @PostMapping("/deleteGoodsType")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除商品类别")
    public Result deleteGoodsType(Integer id) {
        int i = goodsTypeService.deleteGoodsType(id);
        if (i == 1) {
            return Result.success();
        }else  if(i==2){
            return Result.error().message("该分类下面有二级分类,不可删除!");
        }else  if(i==3){
            return Result.error().message("该分类下面有商品,不可删除!");
        }
        return Result.error().message("删除失败!");
    }
    @ApiOperation("根据id查找商品类别")
    @PostMapping("/getGoodsTypeById")
    public Result getGoodsTypeById(Integer id) {
        TbGoodsType goodsType = goodsTypeService.getGoodsTypeById(id);
        return Result.success().data(goodsType);
    }

    @ApiOperation("返回商品类别列表,tree")
    @PostMapping("/getGoodsTypeList")
    public Result getGoodsTypeList() {
        List<OneSubject> list = goodsTypeService.getGoodsTypeList();
        return Result.success().data(list);
    }
    @ApiOperation("返回列表")
    @PostMapping("/getList")
    public Result getList() {
        List<TbGoodsType> list = goodsTypeService.getList();
        return Result.success().data(list);
    }

    @ApiOperation("根据子id，返回父名称")
    @PostMapping("/getParent")
    public Result getParent(Integer id) {
        return Result.success().data(goodsTypeService.getParent(id));
    }
    @ApiOperation("根据子id，返回父id")
    @PostMapping("/getPId")
    public Result getPId(Integer id) {
        return Result.success().data(goodsTypeService.getPId(id));
    }
    @ApiOperation("根据id查找商品类别,返回name值")
    @PostMapping("/getNameById")
    public Result getNameById(Integer id) {
        TbGoodsType goodsType = goodsTypeService.getGoodsTypeById(id);
        return Result.success().data(goodsType.getName());
    }
}

