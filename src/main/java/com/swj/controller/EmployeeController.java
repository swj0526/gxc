package com.swj.controller;


import com.swj.entity.TbEmployee;
import com.swj.service.EmployeeService;
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
    public Result addEmployee(@RequestBody TbEmployee employee) {
        int i = employeeService.addEmployee(employee);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @ApiOperation("修改员工")
    @PostMapping("/updateEmployee")
    public Result updateEmployee(@RequestBody TbEmployee employee) {
        int i = employeeService.updateEmployee(employee);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @ApiOperation("删除员工")
    @PostMapping("/deleteEmployee")
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

    @ApiOperation("更改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(String password) {
        int i = employeeService.updatePassword(password);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改密码失败!");
    }

    @ApiOperation("员工账号状态更改,开启之后,可以用手机号跟默认的密码(手机号)登录,关闭之后,账号不可用")
    @PostMapping("/changeState")
    public Result changeState(Integer state, Integer employeeId) {
         employeeService.changeState(state, employeeId);
        return Result.success();
    }
}
