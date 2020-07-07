package com.swj.annotation;

import com.swj.util.LogOperateTypeEnum;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    /**
     * 操作类型(enum):添加,删除,修改,登陆
     */
    LogOperateTypeEnum operationType() ;

    //操作内容(content)
    String operateContent();
}
