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
 * 采购单的详情信息表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Tbpurchasedetalis对象", description="")
@TableName("Tbpurchasedetalis")
public class TbPurchaseDetalis implements Serializable {

    private static final long serialVersionUID=1L;
    public static  final Integer ENTER_NO =0; //未入库
    public static  final Integer ENTER_YES =1; //已入库

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品编号")
    private String code;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品型号")
    private String model;

    @ApiModelProperty("采购数量")
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

    @ApiModelProperty("采购单id")
    @TableField(value = "purchase_id")
    private Integer purchaseId;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Timestamp updateTime;

    @TableLogic  //逻辑删除注解
    @ApiModelProperty("是否删除")
    private Integer del;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人id")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;

    @ApiModelProperty("是否入库,0未入库,1已入库")
    @TableField("is_enter")
    private Integer isEnter;


}
