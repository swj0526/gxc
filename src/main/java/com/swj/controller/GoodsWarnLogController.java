package com.swj.controller;


import com.swj.entity.TbGoodsWarnLog;
import com.swj.service.GoodsService;
import com.swj.service.GoodsWarnLogService;
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
@RestController
@RequestMapping("/goodsWarnLog")
@Api(description = "商品异常controller")
@CrossOrigin
public class GoodsWarnLogController {
    @Autowired
    private GoodsWarnLogService goodsWarnLogService;

    @PostMapping("/addGoodsWarnLog")
    @ApiOperation("新增商品的异常记录")
    public Result addGoodsWarnLog(@RequestBody TbGoodsWarnLog goodsWarnLog) {
      int i=  goodsWarnLogService.addGoodsWarnLog(goodsWarnLog);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");

    }

    @PostMapping("/getGoodsWarnLogList")
    @ApiOperation("返回商品的异常记录")
    public Result getGoodsWarnLogList(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbGoodsWarnLog> list = goodsWarnLogService.getGoodsWarnLogList(page, limit, vo);
        return Result.success().listForPage(list,goodsWarnLogService.getTotal());
    }

}

