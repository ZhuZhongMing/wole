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
 * @Description: 订单
 * @Author: jeecg-boot
 * @Date:   2020-08-19
 * @Version: V1.0
 */
@ApiModel(value="mbp_order对象", description="订单")
@Data
@TableName("mbp_order")
public class MbpOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**订单编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "订单编号")
    private String id;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private String orderId;
	/**订单标题*/
	@Excel(name = "订单标题", width = 15)
    @ApiModelProperty(value = "订单标题")
    private String orderTitle;
	/**客户编号*/
	@Excel(name = "客户编号", width = 15, dictTable = "mbp_customer", dicText = "customer_name", dicCode = "id")
    @Dict(dictTable = "mbp_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "客户编号")
    private String customerId;
	/**客户名称**/
	@TableField(exist = false)
	private String customerName;


	/**业务员*/
	@Excel(name = "业务员", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @ApiModelProperty(value = "业务员")
    private String salesman;
	/**部门编号*/
	@Excel(name = "部门编号", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @ApiModelProperty(value = "部门编号")
    private String deptId;
	/**支付方式*/
	@Excel(name = "支付方式", width = 15)
    @ApiModelProperty(value = "支付方式")
    private String paymentId;
	/**支付时间*/
	@Excel(name = "支付时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "支付时间")
    private Date paymentDate;
	/**递送方式*/
	@Excel(name = "递送方式", width = 15)
    @ApiModelProperty(value = "递送方式")
    private String deliveryMethod;
	/**递送费用*/
	@Excel(name = "递送费用", width = 15)
    @ApiModelProperty(value = "递送费用")
    private java.math.BigDecimal deliveryFee;
	/**审核人*/
	@Excel(name = "审核人", width = 15)
    @ApiModelProperty(value = "审核人")
    private String auditPerson;
	/**审核时间*/
	@Excel(name = "审核时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date auditTime;
	/**结算货币*/
	@Excel(name = "结算货币", width = 15)
    @ApiModelProperty(value = "结算货币")
    private String currency;
	/**订单类别*/
	@Excel(name = "订单类别", width = 15)
    @ApiModelProperty(value = "订单类别")
    private String orderType;
	/**订单分配状态0-未全部分配,1-已全部分配*/
	@Excel(name = "订单分配状态0-未全部分配,1-已全部分配", width = 15, dicCode = "order_state")
    @Dict(dicCode = "order_state")
    @ApiModelProperty(value = "订单分配状态0-未全部分配,1-已全部分配")
    private Integer orderState;
	/**合同附件*/
	@Excel(name = "合同附件", width = 15)
    @ApiModelProperty(value = "合同附件")
    private String accessory;
	/**合同编号*/
	@Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private String contract;
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
    @ApiModelProperty(value = "修改人")
    private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15, dicCode = "del_flag")
    @Dict(dicCode = "del_flag")
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
    private Integer delFlag;
}
