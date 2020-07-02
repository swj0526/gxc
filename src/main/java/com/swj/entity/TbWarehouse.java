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
 * 仓库表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbwarehouse对象", description = "")
@TableName("Tbwarehouse")
public class TbWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("仓库名称")
    private String name;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("仓库负责人")
    @TableField(value = "emp_id")
    private Integer empId;

    @TableField(exist = false) //非数据数据库字段
    private String phone;//负责人联系方式，只供给前台使用

    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private Timestamp updateTime;

    @TableLogic  //逻辑删除注解
    @ApiModelProperty("是否删除")
    private Integer del;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人id")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;


}
