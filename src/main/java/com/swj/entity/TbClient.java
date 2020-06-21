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
 * 客户基本信息表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbclient对象", description = "")
@TableName("Tbclient")
public class TbClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户名称")
    private String name;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "客户的地址")
    private String address;


    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Timestamp updateTime;

    @TableLogic  //逻辑删除注解
    @ApiModelProperty(value = "是否删除")
    private Integer del;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;


}
