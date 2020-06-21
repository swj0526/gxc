package com.swj.util;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 孙文举
 * @description
 * @create 2020-05-29 23:00
 */
@Data
public class Result {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private Result(){}

    public static Result success(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 不带分页
     * @param value
     * @return
     */
    public Result data( Object value){
        this.data.put("data", value);
        return this;
    }

    /**
     * 数据的追加
     * @param key
     * @param value
     * @return
     */
    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    /**
     * 带分页的返回
     * @param list
     * @param total
     * @return
     */
    public Result listForPage(Object list,long total){
        this.data.put("list", list);
        this.data.put("total", total);
        return this;
    }
    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}