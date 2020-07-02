package com.swj.vo.tree;

import lombok.Data;

/**
 * @author 孙文举
 * @description
 * @create 2020-06-04 9:45
 */
//封装二级分类的返回的tree
    @Data
public class TwoSubject {
    private String id;
    private String pId;
    private String value;
    private String label;
}