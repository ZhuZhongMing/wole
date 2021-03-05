package org.jeecg.modules.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date:   2020-08-18
 * @Version: V1.0
 */
@Data
@TableName("mbp_product_map")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mbp_product_map对象", description="产品表")
public class MbpProductMap implements Serializable {
    private static final long serialVersionUID = 1L;

	/**产品id*/
	//@TableId(type = IdType.ASSIGN_ID)
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "产品id")
    private String id;
	/**产品名称*/
	@Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
    private String productName;

	/**物料编号*/
	@Excel(name = "物料编号", width = 15)
    @ApiModelProperty(value = "物料编号")
    private String materialId;
    /**物料名称*/
    @ApiModelProperty(value = "物料名称")
    @TableField(exist = false)
    private String materialName;

	/**产品型号*/
	@Excel(name = "产品型号", width = 15)
    @ApiModelProperty(value = "产品型号")
    private String productType;
	/**产品规格*/
	@Excel(name = "产品规格", width = 15)
    @ApiModelProperty(value = "产品规格")
    private String productSize;
	/**产品颜色*/
	@Excel(name = "产品颜色", width = 15)
    @ApiModelProperty(value = "产品颜色")
    private String productColor;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String disp;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
    private String updataBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updataTime;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15, dicCode = "del_flag")
	@Dict(dicCode = "del_flag")
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
    private Integer delFlag;

    /**产品图片*/
    @Excel(name = "产品图片", width = 15)
    @ApiModelProperty(value = "产品图片")
    private String productImage;

    @Excel(name = "工艺编号")
    @ApiModelProperty(value = "工艺编号")
    @Dict(dicCode = "id", dictTable = "mbp_technological", dicText = "technological_name")
    private String technologicalId;
}
