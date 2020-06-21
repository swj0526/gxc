package com.swj.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 孙文举
 * @description 条件查询的共用vo(供应商,客户)都使用的这个vo
 * @create 2020-06-17 10:03
 */
@Data
@Component
public class ConditionalVO {
    private String keywords;
    private String begin;
    private String end;
}