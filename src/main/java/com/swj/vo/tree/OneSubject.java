package com.swj.vo.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙文举
 * @description
 * @create 2020-06-04 9:42
 */
//封装tree的一级返回格式
    @Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}