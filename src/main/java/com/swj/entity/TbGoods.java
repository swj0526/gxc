package com.swj.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品列表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbgoods对象", description = "")
@TableName("Tbgoods")
public class TbGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品编码")
    private String code;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品型号")
    private String model;

    @ApiModelProperty("商品类别")
    @TableField(value = "goods_type_id")
    private Integer goodsTypeId;

    @ApiModelProperty("商品的单位id")
    @TableField(value = "goods_unit_id")
    private Integer goodsUnitId;

    @ApiModelProperty("采购价格")
    @TableField(value = "purchasing_price")
    private BigDecimal purchasingPrice;

    @ApiModelProperty("出售价格")
    @TableField(value = "selling_price")
    private BigDecimal sellingPrice;

    @ApiModelProperty("库存数量")
    @TableField(value = "inventory_quantity")
    private Integer inventoryQuantity;


    @TableField(exist = false) //入库数量，非数据库字段
    private Integer num;


    @ApiModelProperty("销售总数")
    @TableField(value = "sell_total")
    private Integer sellTotal;

    @ApiModelProperty("商品上限数量")
    @TableField(value = "upper")
    private Integer upper;

    @ApiModelProperty("商品下限数量")
    @TableField(value = "lower")
    private Integer lower;

    @ApiModelProperty("生产产商id")
    @TableField(value = "customer_id")
    private Integer customerId;

    @ApiModelProperty("所属仓库id")
    @TableField("warehouse_id")
    private Integer warehouseId;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Timestamp updateTime;
    @TableLogic  //逻辑删除注解
    private Integer del;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人id")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;


}
