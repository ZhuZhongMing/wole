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
import java.util.Date;

/**
 * @Description: 工序
 * @Author: jeecg-boot
 * @Date:   2020-09-11
 * @Version: V1.0
 */
@Data
@TableName("mbp_workprocess")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mbp_workprocess对象", description="工序")
public class MbpWorkprocess implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**编码*/
	@Excel(name = "编码", width = 15)
    @ApiModelProperty(value = "编码")
    private String workprocessEncode;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String workprocessName;
	/**性质*/
	@Excel(name = "性质", width = 15)
    @ApiModelProperty(value = "性质")
    private String workprocessProperty;
	/**自动派工0-手动,1-自动*/
	@Excel(name = "自动派工0-手动,1-自动", width = 15, dicCode = "is_auto_task_assign")
	@Dict(dicCode = "is_auto_task_assign")
    @ApiModelProperty(value = "自动派工0-手动,1-自动")
    private Integer isAutoTaskAssign;
	/**方向编号*/
	@Excel(name = "方向编号", width = 15)
    @ApiModelProperty(value = "方向编号")
    private String stepNumber;
	/**禁用进度0-禁用,1-启用*/
	@Excel(name = "禁用进度0-禁用,1-启用", width = 15, dicCode = "is_stop")
	@Dict(dicCode = "is_stop")
    @ApiModelProperty(value = "禁用进度0-禁用,1-启用")
    private Integer isStop;
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
