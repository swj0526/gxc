package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbEmployee;
import com.swj.service.EmployeeService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.EmployeeVO;
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
@Api(description = "员工controller")
@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("新增员工")
    @PostMapping("/addEmployee")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="新增员工")
    public Result addEmployee(@RequestBody TbEmployee employee) {
        int i = employeeService.addEmployee(employee);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @ApiOperation("修改员工")
    @PostMapping("/updateEmployee")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改员工")
    public Result updateEmployee(@RequestBody TbEmployee employee) {
        int i = employeeService.updateEmployee(employee);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @ApiOperation("删除员工")
    @PostMapping("/deleteEmployee")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除员工")
    public Result deleteEmployee(Integer id) {
        int i = employeeService.deleteEmployee(id);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }

    @ApiOperation("根据id查询员工")
    @PostMapping("/getEmployeeById")
    public Result getEmployeeById(Integer id) {
        TbEmployee employee = employeeService.getEmployeeById(id);
        return Result.success().data(employee);
    }

    @ApiOperation("查询员工列表,分页")
    @PostMapping("/getEmployeeList")
    public Result getEmployeeList(Integer page, Integer limit, @RequestBody EmployeeVO vo) {
        List<TbEmployee> list = employeeService.getEmployeeList(page, limit, vo);
        return Result.success().listForPage(list, employeeService.getTotal());
    }
    @ApiOperation("查询员工列表,select")
    @PostMapping("/getSelectList")
    public Result getSelectList() {
        List<TbEmployee> list = employeeService.getSelectList();
        return Result.success().data(list);
    }
    @ApiOperation("更改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(String password) {
        int i = employeeService.updatePassword(password);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改密码失败!");
    }

    @ApiOperation("员工账号状态更改,开启之后,可以用手机号跟默认的密码(手机号)登录,关闭之后,账号不可登陆")
    @PostMapping("/changeState")
    public Result changeState(Boolean state, Integer employeeId) {
        Integer changeState = employeeService.changeState(state, employeeId);
        return Result.success().data(changeState);
    }

    @ApiOperation("查询仓库的所有员工，供下拉列表用")
    @PostMapping("/getWarehouseList")
    public Result getWarehouseList() {
        List<TbEmployee> list = employeeService.getWarehouseList();
        return Result.success().data(list);
    }
    @ApiOperation("查询采购部的所有员工，供下拉列表用")
    @PostMapping("/getSelectListByPurchase")
    public Result getSelectListByPurchase() {
        List<TbEmployee> list = employeeService.getSelectListByPurchase();
        return Result.success().data(list);
    }
}

