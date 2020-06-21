package com.swj.autohandler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 孙文举
 * @description
 * @create 2020-06-11 12:18
 */
@Component //交给spring管理
public class MyMetaObjectHandler implements MetaObjectHandler {

    //新增的自动填充方法
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Timestamp(System.currentTimeMillis()), metaObject);
        this.setFieldValByName("updateTime",  new Timestamp(System.currentTimeMillis()), metaObject);
        //todo 暂且定位1 ,需要重新获取更改
        this.setFieldValByName("operatorId",1,metaObject);
    }
  //修改的自动填充方法
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Timestamp(System.currentTimeMillis()), metaObject);
        //todo 暂且定位1 ,需要重新获取更改
        this.setFieldValByName("operatorId",1,metaObject);
    }
}