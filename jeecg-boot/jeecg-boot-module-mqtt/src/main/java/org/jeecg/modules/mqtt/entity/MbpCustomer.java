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
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
@Data
@TableName("mbp_customer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mbp_customer对象", description="客户表")
public class MbpCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

	/**客户编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "客户编号")
    private String id;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private String customerName;
	/**简称*/
	@Excel(name = "简称", width = 15)
    @ApiModelProperty(value = "简称")
    private String abcName;
	/**检索码*/
	@Excel(name = "检索码", width = 15)
    @ApiModelProperty(value = "检索码")
    private String indexCode;
	/**是否供应商0-否,1-是*/
	@Excel(name = "是否供应商0-否,1-是", width = 15, dicCode = "is_supplier")
	@Dict(dicCode = "is_supplier")
    @ApiModelProperty(value = "是否供应商0-否,1-是")
    private Integer isSupplier;
	/**是否客户0-否,1-是*/
	@Excel(name = "是否客户0-否,1-是", width = 15, dicCode = "is_client")
	@Dict(dicCode = "is_client")
    @ApiModelProperty(value = "是否客户0-否,1-是")
    private Integer isClient;
	/**客户类型*/
	@Excel(name = "客户类型", width = 15)
    @ApiModelProperty(value = "客户类型")
    private String customerType;
	/**客户分类1*/
	@Excel(name = "客户分类1", width = 15)
    @ApiModelProperty(value = "客户分类1")
    private String categoryType;
	/**所属区域*/
	@Excel(name = "所属区域", width = 15)
    @ApiModelProperty(value = "所属区域")
    private String customerArea;
	/**首次合作时间*/
	@Excel(name = "首次合作时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "首次合作时间")
    private Date firstCoDate;
	/**客户分类2*/
	@Excel(name = "客户分类2", width = 15)
    @ApiModelProperty(value = "客户分类2")
    private String categoryType2;
	/**是否外协0-否,1-是*/
	@Excel(name = "是否外协0-否,1-是", width = 15, dicCode = "is_assist")
	@Dict(dicCode = "is_assist")
    @ApiModelProperty(value = "是否外协0-否,1-是")
    private Integer isAssist;
	/**客户地址*/
	@Excel(name = "客户地址", width = 15)
    @ApiModelProperty(value = "客户地址")
    private String customerAddress;
	/**邮政编码*/
	@Excel(name = "邮政编码", width = 15)
    @ApiModelProperty(value = "邮政编码")
    private String postCode;
	/**法人*/
	@Excel(name = "法人", width = 15)
    @ApiModelProperty(value = "法人")
    private String customerManager;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String contactPerson;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private String telephone;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String email;
	/**传真*/
	@Excel(name = "传真", width = 15)
    @ApiModelProperty(value = "传真")
    private String fax;
	/**开户银行*/
	@Excel(name = "开户银行", width = 15)
    @ApiModelProperty(value = "开户银行")
    private String bank;
	/**开户账号*/
	@Excel(name = "开户账号", width = 15)
    @ApiModelProperty(value = "开户账号")
    private String account;
	/**付款方式*/
	@Excel(name = "付款方式", width = 15)
    @ApiModelProperty(value = "付款方式")
    private String paymentKind;
	/**付款周期*/
	@Excel(name = "付款周期", width = 15)
    @ApiModelProperty(value = "付款周期")
    private Integer paymentCircle;
	/**结算货币*/
	@Excel(name = "结算货币", width = 15)
    @ApiModelProperty(value = "结算货币")
    private String currency;
	/**税号*/
	@Excel(name = "税号", width = 15)
    @ApiModelProperty(value = "税号")
    private String taxNo;
	/**营业执照*/
	@Excel(name = "营业执照", width = 15)
    @ApiModelProperty(value = "营业执照")
    private String license;
	/**信用等级*/
	@Excel(name = "信用等级", width = 15)
    @ApiModelProperty(value = "信用等级")
    private String creditGrade;
	/**信用额度*/
	@Excel(name = "信用额度", width = 15)
    @ApiModelProperty(value = "信用额度")
    private BigDecimal creditLimit;
	/**业务员*/
	@Excel(name = "业务员", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @ApiModelProperty(value = "业务员")
    private String salesman;
	/**客户启用状态*/
	@Excel(name = "客户启用状态", width = 15, dicCode = "customer_status")
	@Dict(dicCode = "customer_status")
    @ApiModelProperty(value = "客户启用状态")
    private Integer customerStatus;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private String description;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String disp;
	/**企业网址*/
	@Excel(name = "企业网址", width = 15)
    @ApiModelProperty(value = "企业网址")
    private String webSite;
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
