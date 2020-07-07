package com.swj.controller;


import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbClient;
import com.swj.service.ClientService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import com.swj.vo.GoodsVO;
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
@Api(description = "客户controller")
@RestController
@RequestMapping("/client")
@CrossOrigin //跨域
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/addClient")
    @ApiOperation("添加客户")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加客户")
    public Result addClient(@RequestBody TbClient client) {
        int i = clientService.addClient(client);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");
    }

    @PostMapping("/updateClient")
    @ApiOperation("修改客户")
    @LogAnnotation(operationType= LogOperateTypeEnum.UPDATE,operateContent="修改客户")
    public Result updateClient(@RequestBody TbClient client) {
        int i = clientService.updateClient(client);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("修改失败!");
    }

    @PostMapping("/deleteClient")
    @ApiOperation("删除客户")
    @LogAnnotation(operationType= LogOperateTypeEnum.DEL,operateContent="删除客户")
    public Result deleteClient(Integer id) {
        int i = clientService.deleteClient(id);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("删除失败!");
    }
    @ApiOperation("根据id查找客户")
    @PostMapping("/getClientById")
    public Result getClientById(Integer id) {
        TbClient client = clientService.getClientById(id);
        return Result.success().data(client);
    }

    @PostMapping("/getClientList")
    @ApiOperation("客户列表,带分页")
    public Result getClientList(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbClient> list = clientService.getClientList(page, limit, vo);
        return Result.success().listForPage(list, clientService.getTotal());
    }
}

