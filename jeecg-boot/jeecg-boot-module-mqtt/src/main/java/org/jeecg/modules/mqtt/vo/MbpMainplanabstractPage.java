package org.jeecg.modules.mqtt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.mqtt.entity.MbpMainplan;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 生产计划
 * @Author: jeecg-boot
 * @Date:   2020-08-25
 * @Version: V1.0
 */
@Data
@ApiModel(value="mbp_mainplanabstractPage对象", description="生产计划")
public class MbpMainplanabstractPage {

	/**主计划摘要编号*/
	@ApiModelProperty(value = "主计划摘要编号")
	private String id;
	/**部门编号*/
	@Excel(name = "部门编号", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
	@ApiModelProperty(value = "部门编号")
	private String deptId;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15, dictTable = "mbp_order", dicText = "order_title", dicCode = "id")
    @Dict(dictTable = "mbp_order", dicText = "order_title", dicCode = "id")
	@ApiModelProperty(value = "订单编号")
	private String orderId;
	/**业务员编号*/
	@Excel(name = "业务员编号", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@ApiModelProperty(value = "业务员编号")
	private String salesmanId;
	/**计划种类编号*/
	@Excel(name = "计划种类编号", width = 15)
	@ApiModelProperty(value = "计划种类编号")
	private String plantypeId;
	/**业务日期*/
	@Excel(name = "业务日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "业务日期")
	private Date saleDate;
	/**审核人编号*/
	@Excel(name = "审核人编号", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@ApiModelProperty(value = "审核人编号")
	private String auditorId;
	/**审核日期*/
	@Excel(name = "审核日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "审核日期")
	private Date auditDate;
	/**审核状态0-待审,1-打回,2-通过*/
	@Excel(name = "审核状态0-待审,1-打回,2-通过", width = 15, dicCode = "audit_status")
    @Dict(dicCode = "audit_status")
	@ApiModelProperty(value = "审核状态0-待审,1-打回,2-通过")
	private Integer auditStatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private String disp;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**制单日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "制单日期")
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
	
	@ExcelCollection(name="生产计划明细")
	@ApiModelProperty(value = "生产计划明细")
	private List<MbpMainplan> mbpMainplanList;
	
}
