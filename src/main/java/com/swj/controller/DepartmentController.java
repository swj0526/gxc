package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbDepartment;
import com.swj.service.DepartmentService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Api(description = "部门的controller")
@RestController
@RequestMapping("/department")
@CrossOrigin //跨域
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @ApiOperation("新增部门")
    @PostMapping("/addDepartment")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="新增部门")
    public Result addDepartment(@RequestBody TbDepartment department){
        int i= departmentService.addDepartment(department);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }
    @ApiOperation("修改部门")
    @PostMapping("/updateDepartment")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改部门")
    public Result updateDepartment(@RequestBody TbDepartment department){
        int i= departmentService.updateDepartment(department);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @ApiOperation("删除部门")
    @PostMapping("/deleteDepartment")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除部门")
    public Result deleteDepartment(Integer id){
        int i= departmentService.deleteDepartment(id);
        if (i == 1) {
            return Result.success();
        }else if (i == 2){
            return Result.error().message("该部门下面有员工,不可以删除!");
        }
        return Result.error().message("删除失败!");
    }
    @ApiOperation("根据id查找部门")
    @PostMapping("/getDepartmentById")
    public Result getDepartmentById(Integer id){

        TbDepartment department= departmentService.getDepartmentById(id);

        return Result.success().data(department);
    }

    @ApiOperation("返回部门列表,带分页")
    @PostMapping("/getDepartmentList")
    public Result getDepartmentList(Integer page,Integer limit,@RequestBody ConditionalVO vo){

        List<TbDepartment> list= departmentService.getDepartmentList(page,limit,vo);

        return Result.success().listForPage(list,departmentService.getTotal());
    }

    @ApiOperation("返回部门列表,共下拉框使用")
    @PostMapping("/getSelectList")
    public Result getSelectList(){
        List<TbDepartment> list= departmentService.getSelectList();
        return Result.success().data(list);
    }


}

