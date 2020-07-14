package com.swj.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孙文举
 * @description 商品样品表
 * @create 2020-06-15 17:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "TbGoodsSpecimen对象", description = "")
@TableName("Tbgoodsspecimen")
public class TbGoodsSpecimen implements Serializable {

    private static final long serialVersionUID = 1L;
    public  static  final  Integer ENTER_NO =0; //商品未入库的状态
    public  static  final  Integer ENTER_YES =1; //商品已入库的状态
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
    private BigDecimal purchasingPrice ;

    public void setPurchasingPrice(BigDecimal purchasingPrice) {
        this.purchasingPrice = purchasingPrice.setScale(2);
    }

    @ApiModelProperty("生产产商id")
    @TableField(value = "customer_id")
    private Integer customerId;

    @ApiModelProperty("是否入库,0:未入库,还是样品状态,1:入库,就不是样品状态了")
    @TableField("is_enter")
    private Integer isEnter;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Timestamp updateTime;

    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Integer del;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人id")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;


}