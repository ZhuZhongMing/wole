package org.jeecg.modules.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2020-08-19
 * @Version: V1.0
 */
@ApiModel(value="mbp_order对象", description="订单")
@Data
@TableName("mbp_orderlist")
public class MbpOrderlist implements Serializable {
    private static final long serialVersionUID = 1L;

	/**订单明细流水号*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "订单明细流水号")
	private String id;
	/**订单编号*/
	@ApiModelProperty(value = "订单编号")
	private String orderId;
	/**产品编号*/
	@Excel(name = "产品编号", width = 15, dictTable = "mbp_product_map", dicText = "product_name", dicCode = "id")
	@Dict(dictTable = "mbp_product_map", dicText = "product_name", dicCode = "id")
	@ApiModelProperty(value = "产品编号")
	private String materialId;
	/**数量*/
	@Excel(name = "数量", width = 15)
	@ApiModelProperty(value = "数量")
	private Long orderNum;
	/**模式*/
	@Excel(name = "模式", width = 15)
	@ApiModelProperty(value = "模式")
	private String model;
	/**交货日期*/
	@Excel(name = "交货日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "交货日期")
	private Date finishDate;
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
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
	@ApiModelProperty(value = "删除标识0-正常,1-已删除")
	private Integer delFlag;
	/**未分配数量*/
	@Excel(name = "未分配数量", width = 15)
	@ApiModelProperty(value = "未分配数量")
	private Integer allotNumber;
	/**已分配数量*/
	@Excel(name = "已分配数量", width = 15)
	@ApiModelProperty(value = "已分配数量")
	private Integer allotedNumber;
}
