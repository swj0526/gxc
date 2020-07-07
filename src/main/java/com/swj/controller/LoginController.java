package com.swj.controller;

import com.swj.annotation.LogAnnotation;
import com.swj.base.SC;
import com.swj.util.LogOperateTypeEnum;
import com.swj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 孙文举
 * @description
 * @create 2020-06-10 15:34
 */
@Api(description = "登录Controller") //在swagger上显示中文提示
@RestController
@RequestMapping("/login")
@CrossOrigin //解决跨域
public class LoginController {
    @Autowired
    private HttpSession session;
    @ApiOperation("登录")
    @PostMapping("/checkLogin")
    @LogAnnotation(operationType= LogOperateTypeEnum.LOGIN,operateContent="登陆")
    public Result checkLogin(String username, String password) {
        String phone ="18653525596";
        session.setAttribute("phone",phone);
        SC.setSession(phone);
        return Result.success().data("token","admin");
    }
    @ApiOperation("登录之后获取用户信息的方法")
    @PostMapping("/info")
    public Result info() {
        return Result.success().data("name","swj").data("roles","[管理员]")
                .data("avatar","http://www.adwzw.com/uploadfile/2018/0806/20180806095331358.jpg");
    }
}