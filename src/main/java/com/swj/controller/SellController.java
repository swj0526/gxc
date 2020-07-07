package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbSell;
import com.swj.entity.TbSellDetalis;
import com.swj.service.SellDetalisService;
import com.swj.service.SellService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api("销售单controller")
@RestController
@RequestMapping("/sell")
@CrossOrigin
public class SellController {

    @Autowired
    private SellService sellService;
    @Autowired
    private SellDetalisService detalisService;

    @PostMapping("/addSell")
    @ApiOperation("添加销售单")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加销售单")
    public Result addSell(@RequestBody TbSell sell) {
        int i = sellService.addSell(sell);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @PostMapping("/updateSell")
    @ApiOperation("修改销售单")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改销售单")
    public Result updateSell(@RequestBody TbSell sell) {
        int i = sellService.updateSell(sell);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @PostMapping("/deleteSell")
    @ApiOperation("删除销售单")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除销售单")
    public Result deleteSell(Integer id) {
        int i = sellService.deleteSell(id);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @PostMapping("/getSellById")
    @ApiOperation("根据id返回销售单")
    public Result getSellById(Integer id) {
        TbSell sell = sellService.getSellById(id);
        return Result.success().data(sell);
    }

    @PostMapping("/getSellList")
    @ApiOperation("销售单列表,分页")
    public Result getSellList(Integer page, Integer limit,@RequestBody ConditionalVO vo) {
        List<TbSell> list = sellService.getSellList(page, limit, vo);
        return Result.success().listForPage(list, sellService.getTotal());
    }



    @ApiOperation("已完成销售单,分页")
    @PostMapping("/getSellListEnd")
    public Result getSellListEnd(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbSell> sellList = sellService.getSellListEnd(page, limit, vo);
        return Result.success().listForPage(sellList, sellService.getTotal());
    }



    @ApiOperation("/仓库人员查看销售单,并阅读销售单,将销售商品出库")
    @PostMapping("/checkGoods")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="商品出库")
    public Result checkGoods(Integer page, Integer limit, Integer sellId, Map<String, Integer> map) {
        detalisService.checkGoods(page, limit, sellId, map);
        return Result.success();
    }

    @ApiOperation("/返回各种状态的销售订单列表")
    @PostMapping("/getSellListByState")
    public Result getSellListByState(Integer page, Integer limit, Integer state) {
        List<TbSell> list = sellService.getSellListByState(page, limit, state);
        return Result.success().listForPage(list, sellService.getTotal());
    }

    @ApiOperation("/销售人员最后确认订单,整个订单完成")
    @PostMapping("/endSellById")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="销售单完成")
    public Result endSellById(Integer sellId) {
        int i = sellService.endSellById(sellId, TbSell.STATE_END);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }
}

