package org.jeecg.modules.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2020-10-30
 * @Version: V1.0
 */
@Data
@TableName("mbp_equipment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mbp_equipment对象", description="设备表")
public class MbpEquipment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**设备编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "设备编号")
    private String id;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;
	/**网关编号*/
	@Excel(name = "网关编号", width = 15)
    @ApiModelProperty(value = "网关编号")
    private String gatewayId;
	/**设备类型*/
	@Excel(name = "设备类型", width = 15)
    @ApiModelProperty(value = "设备类型")
    private String equipmentType;
	/**一级地区*/
	@Excel(name = "一级地区", width = 15)
    @ApiModelProperty(value = "一级地区")
    private String level1Region;
	/**二级地区*/
	@Excel(name = "二级地区", width = 15)
    @ApiModelProperty(value = "二级地区")
    private String level2Region;
	/**三级地区*/
	@Excel(name = "三级地区", width = 15)
    @ApiModelProperty(value = "三级地区")
    private String level3Region;
	/**安装时间*/
	@Excel(name = "安装时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "安装时间")
    private Date deployTime;
	/**安装地址*/
	@Excel(name = "安装地址", width = 15)
    @ApiModelProperty(value = "安装地址")
    private String deployAddress;
	/**经度*/
	@Excel(name = "经度", width = 15)
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;
	/**纬度*/
	@Excel(name = "纬度", width = 15)
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
	/**通讯方式*/
	@Excel(name = "通讯方式", width = 15)
    @ApiModelProperty(value = "通讯方式")
    private String comType;
	/**产能系数*/
	@Excel(name = "产能系数", width = 15)
    @ApiModelProperty(value = "产能系数")
    private BigDecimal productCoefficient;
	/**设备状态0-离线,1-在线*/
	@Excel(name = "设备状态0-离线,1-在线", width = 15)
    @ApiModelProperty(value = "设备状态0-离线,1-在线")
    private Integer status;
	/**维护规则编号*/
	@Excel(name = "维护规则编号", width = 15)
    @ApiModelProperty(value = "维护规则编号")
    private String maintainRule;
	/**设备图片*/
	@Excel(name = "设备图片", width = 15)
    @ApiModelProperty(value = "设备图片")
    private String equipmentImage;
	/**设备评分*/
	@Excel(name = "设备评分", width = 15)
    @ApiModelProperty(value = "设备评分")
    private Integer equipmentScore;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remarks;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
    private Integer delFlag;
	/**所属工序*/
	@Excel(name = "所属工序", width = 15)
    @ApiModelProperty(value = "所属工序")
    private String processId;
}
