package com.swj.controller;


import com.swj.entity.TbGoodsWarnLog;
import com.swj.entity.TbLog;
import com.swj.service.LogService;
import com.swj.util.Result;
import com.swj.vo.ConditionalVO;
import io.swagger.annotations.Api;
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
@RequestMapping("/log")
@CrossOrigin
@Api(description = "日志controller")
public class LogController {
    @Autowired
    LogService logService;


    @PostMapping("/getLogList")
    @ApiOperation("返回日志记录，带分页")
    public Result getLogList(Integer page, Integer limit, @RequestBody ConditionalVO vo) {
        List<TbLog> list = logService.getLogList(page, limit, vo);
        return Result.success().listForPage(list,logService.getTotal());
    }
}

