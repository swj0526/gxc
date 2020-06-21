package com.swj.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 孙文举
 * @description sessionController 里面放一些项目共用数据
 * @create 2020-06-15 16:10
 */
public class SC {

    public static String SESSION_KEY;

    //设置session中的电话号码
    public static void setSession(String key) {
        SESSION_KEY = key;
    }
}