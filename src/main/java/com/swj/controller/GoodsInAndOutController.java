package com.swj.controller;

import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbGoodsInAndOut;
import com.swj.service.GoodsInAndOutService;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("商品进出库记录controller")
@RestController
@RequestMapping("/goodsInAndOut")
@CrossOrigin
public class GoodsInAndOutController {
    @Autowired
    private GoodsInAndOutService goodsInAndOutService;

    @PostMapping("/addGoodsInAndOut")
    @ApiOperation("添加商品进出库的记录")
    @LogAnnotation(operationType= LogOperateTypeEnum.ADD,operateContent="添加商品进出库记录")
    public Result addGoodsInAndOut(TbGoodsInAndOut goodsInAndOut) {
        int i = goodsInAndOutService.addGoodsInAndOut(goodsInAndOut);
        if (i == 1) {
            return Result.success();
        }
        return Result.error().message("添加失败!");

    }
    @PostMapping("/getGoodsInAndOutList")
    @ApiOperation("添加商品进出库的记录")
    public Result getGoodsInAndOutList(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
       List<TbGoodsInAndOut> list =  goodsInAndOutService.getGoodsInAndOutList(page,limit,vo);
         return  Result.success().listForPage(list,goodsInAndOutService.getTotal());

    }

}
