package com.swj.controller;


import com.swj.entity.TbPurchaseDetalis;
import com.swj.service.PurchaseDetalisService;
import com.swj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/purchaseDetalis")
@CrossOrigin
public class PurchaseDetalisController {
    @Autowired
    private PurchaseDetalisService detalisService;

    @ApiOperation("根据采购单id,查询采购单商品详情信息")
    public Result getDetalisByPurchaseId(Integer page,Integer limit,Integer purchaseId){
       List<TbPurchaseDetalis> list =  detalisService.getDetalisByPurchaseId(page,limit,purchaseId);
       return Result.success().listForPage(list,detalisService.getTotal());
    }

}

