package com.swj.entity;

import java.math.BigDecimal;

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
 * 销售单的商品列表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbselldetalis对象", description = "")
@TableName("Tbselldetalis")
public class TbSellDetalis implements Serializable {

    private static final long serialVersionUID = 1L;
    public static  final Integer ENTER_NO =0; //未入库
    public static  final Integer ENTER_YES =1; //已入库

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品编号")
    private String code;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类型")
    private String model;

    @ApiModelProperty("商品数量")
    private int num;

    @ApiModelProperty("采购价格")
    @TableField(value = "purchasing_price")
    private BigDecimal purchasingPrice;

    @ApiModelProperty("出售价格")
    @TableField(value = "selling_price")
    private BigDecimal sellingPrice;

    @ApiModelProperty("单项总价")
    @TableField(value = "sum")
    private BigDecimal sum;

    @ApiModelProperty("销售单id")
    @TableField(value = "sell_id")
    private Integer sellId;

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
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "operator_id")
    private Integer operatorId;

    @ApiModelProperty("是否签收.0.未出库,1已出库")
    @TableField(value = "is_enter")
    private Integer isEnter;

    @ApiModelProperty("出库数量")
    @TableField("num_out")
    private Integer numOut;


    @ApiModelProperty("受损数量")
    @TableField("num_error")
    private Integer numError;

}
