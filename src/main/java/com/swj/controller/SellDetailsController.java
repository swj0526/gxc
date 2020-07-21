package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbSellDetalis;
import com.swj.service.SellDetalisService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import io.swagger.annotations.Api;
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
@Api("销售单的商品详情controller")
@RestController
@RequestMapping("/sellDetails")
@CrossOrigin
public class SellDetailsController {

    @Autowired
    private SellDetalisService  detailsService;

    @ApiOperation("根据销售单的id返回销售的商品详情信息的列表")
    @PostMapping("/getDetailsListBySellId")
    public Result getDetailsListBySellId(Integer page, Integer limit,Integer sellId){
        List<TbSellDetalis> list = detailsService.getDetailsBySellId(page,limit,sellId);
        return Result.success().listForPage(list,detailsService.getTotal());
    }

    @ApiOperation("添加销售单的详情信息")
    @PostMapping("/addDetails")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加销售单的详情")
    public Result addDetails(String idList,String numList,Integer clientId){
        int i= detailsService.addDetails(idList,numList,clientId);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }
    @ApiOperation("修改销售单的详情信息")
    @PostMapping("/updateDetails")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改销售单的详情")
    public Result updateDetails(String idList,String numList,Integer sellId){
        int i= detailsService.updateDetails(idList,numList,sellId);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }


}

