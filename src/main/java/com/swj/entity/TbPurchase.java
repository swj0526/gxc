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
 * 进货单表,采购表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Tbpurchase对象", description = "")
@TableName("Tbpurchase")
public class TbPurchase implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int STATE_DEFAULT=1;//仓库申请采购，生成了采购单，采购员未阅读
    public static final int STATE_READ=2;//采购员已阅读，（采购中）并按照采购单进行采购
    public static final int STATE_INTO=3;//采购员采购完成，仓库人员正在盘点入库（入库中）
    public static final int STATE_END=4;//仓库人员完成入库，如有不合格的将会反馈
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("进货单的编号")
    private String code;

    @ApiModelProperty("进货商品的id列表,例如1,2,3,")
    @TableField(value = "id_list")
    private String idList;

    @ApiModelProperty("进货商品的数量列表")
    @TableField(value = "num_list")
    private String numList;

    @ApiModelProperty("总金额")
    private BigDecimal sum;

    @ApiModelProperty("实付金额")
    private BigDecimal pay;

    @ApiModelProperty("付款状态 0:未付款 1:付部分款 2:已付清")
    private Integer state;

    @ApiModelProperty("是否阅读")
    @TableField(value = "is_read")
    private Integer isRead;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    private Timestamp updateTime;

    @TableLogic  //逻辑删除注解
    @ApiModelProperty("是否删除")
    private Integer del;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "operator_id")
    private Integer operatorId;


}
