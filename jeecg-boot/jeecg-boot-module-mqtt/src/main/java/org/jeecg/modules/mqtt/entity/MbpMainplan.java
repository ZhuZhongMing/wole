package org.jeecg.modules.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 生产计划明细
 * @Author: jeecg-boot
 * @Date:   2020-08-25
 * @Version: V1.0
 */
@ApiModel(value="mbp_mainplanabstract对象", description="生产计划")
@Data
@TableName("mbp_mainplan")
public class MbpMainplan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主计划编号*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主计划编号")
	private String id;

	/**主计划摘要编号*/
	@ApiModelProperty(value = "主计划摘要编号")
	private String mainplanAbstractId;
	/**订单标题*/
	@TableField(exist = false)
	@ApiModelProperty(value = "订单标题")
	private String orderTitle;

	/**物料编号*/
	@Excel(name = "物料编号", width = 15, dictTable = "mbp_product_map", dicText = "product_name", dicCode = "id")
	@Dict(dictTable = "mbp_product_map", dicText = "product_name", dicCode = "id")
	@ApiModelProperty(value = "物料编号")
	private String materialId;
	/**物料名称*/
	@TableField(exist = false)
	private String materialName;


	/**数量*/
	@Excel(name = "数量", width = 15)
	@ApiModelProperty(value = "数量")
	private Long number;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	/**完成时间*/
	@Excel(name = "完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "完成时间")
	private Date finishTime;
	/**计划类型编号*/
	@Excel(name = "计划类型编号", width = 15)
	@ApiModelProperty(value = "计划类型编号")
	private Integer planTypeId;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
	@ApiModelProperty(value = "客户名称")
	private String customenrName;
	/**加工顺序*/
	@Excel(name = "加工顺序", width = 15)
	@ApiModelProperty(value = "加工顺序")
	private Integer processOrder;
	/**版本号*/
	@Excel(name = "版本号", width = 15)
	@ApiModelProperty(value = "版本号")
	private String versionNo;
	/**图号*/
	@Excel(name = "图号", width = 15)
	@ApiModelProperty(value = "图号")
	private String figureNo;
	/**优先级编号*/
	@Excel(name = "优先级编号", width = 15)
	@ApiModelProperty(value = "优先级编号")
	private Integer priorityId;
	/**计划状态编号0-等待确认,1-正在进行,2-计划完成*/
	@Excel(name = "计划状态编号0-等待确认,1-正在进行,2-计划完成", width = 15, dicCode = "planstatus_id")
	@Dict(dicCode = "planstatus_id")
	@ApiModelProperty(value = "计划状态编号0-等待确认,1-正在进行,2-计划完成")
	private Integer planstatusId;
	/**是否关闭*/
	@Excel(name = "是否关闭", width = 15)
	@ApiModelProperty(value = "是否关闭")
	private Integer isClosed;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private String disp;
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
	@Excel(name = "删除标识0-正常,1-已删除", width = 15, dicCode = "del_flag")
	@Dict(dicCode = "del_flag")
	@ApiModelProperty(value = "删除标识0-正常,1-已删除")
	private Integer delFlag;
	/**设备编号*/
	@Excel(name = "设备编号", width = 15, dictTable = "mbp_equipment", dicText = "equipment_name", dicCode = "id")
	@Dict(dicCode = "id", dictTable = "mbp_equipment", dicText = "equipment_name")
	@ApiModelProperty(value = "设备编号")
	private String equipmentId;


	/**已完成数量*/
	@Excel(name = "已完成数量", width = 15)
	@ApiModelProperty(value = "已完成数量")
	private Long finishNumber;


}
