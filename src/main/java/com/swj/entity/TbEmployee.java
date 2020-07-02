package com.swj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工,用户共用一张表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbemployee对象", description = "")
@TableName("Tbemployee")
public class TbEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Boolean STATE_CLOSE=false; //账号未开启
    public static final Boolean STATE_OPEN=true; //账号已开启

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value="密码")
    private String password;

    @ApiModelProperty(value="部门id")
    @TableField("department_id")
    private Integer departmentId;

    @ApiModelProperty(value="账号状态:0未开启,1开启")
    private Boolean state;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Timestamp updateTime;

    @TableLogic  //逻辑删除注解
    @ApiModelProperty(value="是否删除")
    private Integer del;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="操作人id")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;


}
