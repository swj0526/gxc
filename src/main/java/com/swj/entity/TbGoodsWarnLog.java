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
 * 库存异常表(库存下限报警,库存报溢,以及商品损坏的记录表)
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbgoodswarnlog对象", description = "")
@TableName("Tbgoodswarnlog")
public class TbGoodsWarnLog implements Serializable {

    private static final long serialVersionUID = 1L;
    public static  final Integer STATE_LOWER=1;//下限报警
    public static  final Integer STATE_UPPER=2;//上限报警
    public static  final Integer STATE_ERROR=3;//损坏

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品id")
    @TableField(value = "goods_id")
    private Integer goodsId;

    @TableField(exist = false) //商品编号
    private String code;

    @TableField(exist = false) //商品名称
    private String name;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("状态:库存下限报警1,库存报溢2,商品损坏3")
    private Integer state;

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


}
