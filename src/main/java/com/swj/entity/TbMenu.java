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
 *  菜单表
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TbMenu对象", description="")
@TableName("menu")
public class TbMenu implements Serializable {



    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "p_id")
    private Integer pId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("地址")
    private String url;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("权限标示")
    private String label;







}

