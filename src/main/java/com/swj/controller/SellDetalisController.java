package com.swj.controller;


import com.swj.entity.TbSellDetalis;
import com.swj.service.SellDetalisService;
import com.swj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
@Api("销售单的商品详情controller")
@RestController
@RequestMapping("/sellDetalis")
@CrossOrigin
public class SellDetalisController {

    @Autowired
    private SellDetalisService  detalisService;

    @ApiOperation("根据销售单的id返回销售的商品详情信息的列表")
    public Result getDetalisListBySellId(Integer page, Integer limit,Integer sellId){
        List<TbSellDetalis> list = detalisService.getDetalisBySellId(page,limit,sellId);
        return Result.success().listForPage(list,detalisService.getTotal());
    }
}

