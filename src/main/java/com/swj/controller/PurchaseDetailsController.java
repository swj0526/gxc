package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbPurchaseDetalis;
import com.swj.service.PurchaseDetalisService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("采购单的商品详情信息controller")
@RestController
@RequestMapping("/purchaseDetails")
@CrossOrigin
public class PurchaseDetailsController {
    @Autowired
    private PurchaseDetalisService detailsService;

    @ApiOperation("根据采购单id,查询采购单商品详情信息")
    @PostMapping("/getDetailsByPurchaseId")
    public Result getDetailsByPurchaseId(Integer page,Integer limit,Integer purchaseId){
       List<TbPurchaseDetalis> list =  detailsService.getDetailsByPurchaseId(page,limit,purchaseId);
       return Result.success().listForPage(list,detailsService.getTotal());
    }

    @ApiOperation("添加采购单的详情信息")
    @PostMapping("/addDetails")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加采购单的详情信息")
    public Result addDetails(String idList,String numList){
      int i= detailsService.addDetails(idList,numList);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }
    @ApiOperation("修改采购单的详情信息")
    @PostMapping("/updateDetails")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改采购单的详情信息")
    public Result updateDetails(String idList,String numList,Integer purchaseId){
        int i= detailsService.updateDetails(idList,numList,purchaseId);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }
    @ApiOperation("签收采购单")
    @PostMapping("/determineDetails")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="仓库人员签收采购单")
    public Result determineDetails(String idList,String numList,String remarkList,Integer purchaseId){
        int i= detailsService.determineDetails(idList,numList,remarkList,purchaseId);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("签收失败!");
    }
}

