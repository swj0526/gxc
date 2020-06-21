package com.swj.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 孙文举
 * @description
 * @create 2020-06-11 14:08
 */
@Data
@Component
public class GoodsVO {
    private String name;
    private String code;
    private String begin;
    private String end;
}