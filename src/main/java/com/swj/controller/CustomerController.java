package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbClient;
import com.swj.entity.TbCustomer;
import com.swj.service.CustomerService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "供应商controller")
@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @ApiOperation("添加供应商")
    @PostMapping("/addCustomer")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加供应商")
    public Result addCustomer(@RequestBody TbCustomer customer){
     int i= customerService.addCustomer(customer);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @ApiOperation("修改供应商")
    @PostMapping("/updateCustomer")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改供应商")
    public Result updateCustomer(@RequestBody TbCustomer customer){
      int i=  customerService.updateCustomer(customer);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @ApiOperation("删除供应商")
    @PostMapping("/deleteCustomer")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除供应商")
    public Result deleteCustomer(Integer id){
        int i=   customerService.deleteCustomer(id);
        if(i==1){
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }

    @ApiOperation("供应商列表,分页")
    @PostMapping("/getCustomerList")
    public Result getCustomerList(Integer page,Integer limit,@RequestBody ConditionalVO vo ){
      List<TbCustomer> list= customerService.getCustomerList(page,limit,vo);
        return Result.success().listForPage(list,customerService.getTotal());
    }
    @ApiOperation("根据id返回供应商")
    @PostMapping("/getCustomerById")
    public Result getCustomerById(Integer id){
        return Result.success().data(customerService.getCustomerById(id));
    }
    @ApiOperation("返回所有的供应商，供给下拉列表使用")
    @PostMapping("/getSelectList")
    public Result getSelectList(){

        return Result.success().data(customerService.getSelectList());
    }

}

