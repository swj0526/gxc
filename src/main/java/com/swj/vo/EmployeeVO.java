package com.swj.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 孙文举
 * @description 员工,用户的vo
 * @create 2020-06-17 10:03
 */
@Data
@Component
public class EmployeeVO {
    private String keywords;
    private int  departmentId;
    private String begin;
    private String end;
}