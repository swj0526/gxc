package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbRole;
import com.swj.service.RoleService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@RestController
@RequestMapping("/role")
@CrossOrigin
@Api(description = "角色Controller")
public class RoleController {
  @Autowired
    private RoleService roleService;

    @PostMapping("/addRole")
    @ApiOperation("添加角色")
    @LogAnnotation(operationType = LogOperateTypeEnum.ADD,operateContent = "添加角色")
    public Result  addUser(@RequestBody TbRole role){
      int i=  roleService.addRole(role);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
  }
    @PostMapping("/updateRole")
    @ApiOperation("修改角色")
    @LogAnnotation(operationType = LogOperateTypeEnum.UPDATE,operateContent = "修改角色")
    public Result  updateRole(@RequestBody TbRole role){
       int i= roleService.updateRole(role);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }
    @PostMapping("/deleteRole")
    @ApiOperation("删除角色")
    @LogAnnotation(operationType = LogOperateTypeEnum.DEL,operateContent = "删除角色")
    public Result  deleteRole(int id){
      int i=  roleService.deleteRole(id);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }
    @PostMapping("/getRoleList")
    @ApiOperation("返回角色列表带分页")
    public Result  getRoleList(Integer page, Integer limit, @RequestBody ConditionalVO vo){
        List<TbRole> list = roleService.getRoleList(page,limit,vo);
        return Result.success().listForPage(list,roleService.getTotal());
    }

    @PostMapping("/getRoleById")
    @ApiOperation("根据id返回role")
    public Result  getRoleById(int id){
      TbRole role= roleService.getRoleById(id);
      return Result.success().data(role);
    }

    @PostMapping("/setPerms")
    @ApiOperation("设置角色的访问权限")
    @LogAnnotation(operationType = LogOperateTypeEnum.UPDATE,operateContent = "设置角色的访问权限")
    public Result  setPerms(int id,String perms){
         roleService.setPerms(id,perms);
        return Result.success();
    }
}

