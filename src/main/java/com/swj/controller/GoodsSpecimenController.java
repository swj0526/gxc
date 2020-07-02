package com.swj.controller;


import com.swj.entity.TbGoods;
import com.swj.entity.TbGoodsSpecimen;
import com.swj.service.GoodsSpecimenService;
import com.swj.util.Result;
import com.swj.vo.GoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-15
 */
@Api(description = "商品样品的controller")
@RestController
@RequestMapping("/goodsSpecimen")
@CrossOrigin //跨域
public class GoodsSpecimenController {
    @Autowired
    private GoodsSpecimenService goodsSpecimenService;

    @PostMapping("/addGoodsSpecimen")
    @ApiOperation("添加商品样品")
    public Result addGoodsSpecimen(@RequestBody TbGoodsSpecimen goodsSpecimen){
        int i = goodsSpecimenService.addGoodsSpecimen(goodsSpecimen);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }
    @PostMapping("/updateGoodsSpecimen")
    @ApiOperation("修改商品样品")
    public Result updateGoodsSpecimen(@RequestBody TbGoodsSpecimen goodsSpecimen){
        int i =  goodsSpecimenService.updateGoodsSpecimen(goodsSpecimen);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @PostMapping("/getGoodsSpecimenById")
    @ApiOperation("根据id查询商品样品")
    public Result  getGoodsSpecimenById(Integer id){
        TbGoodsSpecimen goodsSpecimen = goodsSpecimenService.getGoodsSpecimenById(id);
        return Result.success().data(goodsSpecimen);
    }
    @PostMapping("/deleteGoodsSpecimen")
    @ApiOperation("删除商品样品")
    public Result deleteGoodsSpecimen(Integer id){
        int i =goodsSpecimenService.deleteGoodsSpecimen(id);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }
    @PostMapping("/getList" )
    @ApiOperation("商品样品列表,分页")
    public Result getList(Integer page, Integer limit, @RequestBody TbGoodsSpecimen goodsSpecimen){
        List<TbGoodsSpecimen> list = goodsSpecimenService.getList(page, limit, goodsSpecimen);
        return Result.success().listForPage(list, goodsSpecimenService.getTotal());
    }

    @PostMapping("/instock")
    @ApiOperation("商品样品入库")
    public Result instock(@RequestBody TbGoods goods){
        goodsSpecimenService.instock(goods);
        return Result.success();
    }
}

