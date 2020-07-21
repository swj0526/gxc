package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbPurchase;
import com.swj.entity.TbSell;
import com.swj.service.PurchaseDetalisService;
import com.swj.service.PurchaseService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
@Api(description = "采购单controller")
@RestController
@RequestMapping("/purchase")
@CrossOrigin
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDetalisService detalisService;

    @ApiOperation("新增采购单")
    @PostMapping("/addPurchase")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="新增采购单")
    public Result addPurchase(@RequestBody TbPurchase purchase) {
        int i = purchaseService.addPurchase(purchase);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    /**
     * 采购单只有在仓库人员为入库之前才可以修改,入库之后不可修改
     *
     * @param purchase
     * @return
     */
    @ApiOperation("修改采购单")
    @PostMapping("/updatePurchase")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改采购单")
    public Result updatePurchase(@RequestBody TbPurchase purchase) {
        int i = purchaseService.updatePurchase(purchase);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    /**
     * 采购单只有在仓库人员为入库之前才可以删除,入库之后不可删除
     *
     * @return
     */
    @ApiOperation("删除采购单")
    @PostMapping("/deletePurchase")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除采购单")
    public Result deletePurchase(Integer id) {
        int i = purchaseService.deletePurchase(id);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }

    @ApiOperation("根据id获取采购单")
    @PostMapping("/getPurchaseById")
    public Result getPurchaseById(Integer id) {
        return Result.success().data(purchaseService.getPurchaseById(id));

    }

    @ApiOperation("待审核采购单列表,分页")
    @PostMapping("/getPurchaseList")
    public Result getPurchaseList(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbPurchase> purchaseList = purchaseService.getPurchaseList(page, limit, vo);
        return Result.success().listForPage(purchaseList, purchaseService.getTotal());
    }
    @ApiOperation("已完成采购单（无异常）,分页")
    @PostMapping("/getPurchaseListEnd")
    public Result getPurchaseListEnd(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbPurchase> purchaseList = purchaseService.getPurchaseListEnd(page, limit, vo);
        return Result.success().listForPage(purchaseList, purchaseService.getTotal());
    }

    @ApiOperation("仓库人员提交采购单的列表，分页")
    @PostMapping("/getPurchaseListDefault")
    public Result getPurchaseListDefault(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbPurchase> purchaseList = purchaseService.getPurchaseListDefault(page, limit, vo);
        return Result.success().listForPage(purchaseList, purchaseService.getTotal());
    }

    @ApiOperation("分单，给采购部门员工分单")
    @PostMapping("/setEmp")
    public Result setEmp(Integer empId,Integer purchaseId) {
        purchaseService.setEmp(empId,purchaseId);
        return Result.success();
    }

    @ApiOperation("/仓库人员检查采购商品并入库")
    @PostMapping("/checkGoods")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="仓库人员检查采购商品入库")
    public Result checkGoods(Integer page, Integer limit, Integer purchaseId, Map<String, Integer> map) {
        detalisService.checkGoods(page, limit, purchaseId, map);
        return Result.success();
    }

    @ApiOperation("/返回有异常的完成的采购单，分页")
    @PostMapping("/getPurchaseListError")
    public Result getPurchaseListError(Integer page, Integer limit,@RequestBody ConditionalVO vo) {
        List<TbPurchase> list = purchaseService.getPurchaseListError(page, limit,vo);
        return Result.success().listForPage(list, purchaseService.getTotal());
    }

    @ApiOperation("/采购人员最后确认订单,整个订单完成")
    @PostMapping("/endPurchaseById")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="采购订单完成")
    public Result endPurchaseById(Integer purchaseId) {
        int i = purchaseService.endPurchaseById(purchaseId, TbPurchase.STATE_END);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }
}

