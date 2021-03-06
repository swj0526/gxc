package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbWarehouse;
import com.swj.service.WarehouseService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api(description = "仓库controller")
@RestController
@RequestMapping("/warehouse")
@CrossOrigin
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @ApiOperation("新增仓库")
    @PostMapping("/addWarehouse")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="新增仓库")
    public Result addWarehouse(@RequestBody TbWarehouse warehouse) {
        int i = warehouseService.addWarehouse(warehouse);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @ApiOperation("修改仓库")
    @PostMapping("/updateWarehouse")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改仓库")
    public Result updateWarehouse(@RequestBody TbWarehouse warehouse) {
        int i = warehouseService.updateWarehouse(warehouse);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @ApiOperation("删除仓库")
    @PostMapping("/deleteWarehouse")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除仓库")
    public Result deleteWarehouse(Integer id) {
        int i = warehouseService.deleteWarehouse(id);
        if (i == 1) {
            return Result.success();
        }else if(i==2){
            return Result.error().message("仓库不可删除,下面有商品!");
        }

        return Result.error().message("删除失败!");
    }

    @ApiOperation("根据id选择仓库")
    @PostMapping("/getWarehouseById")
    public Result getWarehouseById(Integer id) {
        TbWarehouse warehouse = warehouseService.getWarehouseById(id);
        return  Result.success().data(warehouse);
    }

    @ApiOperation("返回仓库列表,带分页")
    @PostMapping("/getWarehouseList")
    public Result getWarehouseList(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
      List<TbWarehouse> list= warehouseService.getWarehouseList(page,limit,vo);
      return Result.success().listForPage(list,warehouseService.getTotal());
    }

    @ApiOperation("返回仓库列表，供select使用")
    @PostMapping("/getSelectList")
    public Result getSelectList() {
        List<TbWarehouse> list= warehouseService.getSelectList();
        return Result.success().data(list);
    }
}

