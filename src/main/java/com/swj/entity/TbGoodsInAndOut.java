package com.swj.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品进出库记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "TbGoodsInAndOut对象", description = "")
@TableName("tbgoodsinandout")
public class TbGoodsInAndOut implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final  Integer STATE_INTO=1; //进库
    public static final  Integer STATE_OUT=2; //出库

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(exist = false)
    private String code;//非数据库字段，商品编号

    @TableField(exist = false)
    private String name; //非数据库字段，商品名称

    @TableField(value = "goods_id")
    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("状态")
    private Integer state;

    @ApiModelProperty("数量")
    private Integer num;

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
