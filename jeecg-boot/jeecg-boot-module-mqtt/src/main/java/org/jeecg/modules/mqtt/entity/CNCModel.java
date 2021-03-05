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
 * 杭州三米明德科技有限公司
 *  cnc 数据模型
 * @author zzm
 * date 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CNC数据模型对象", description="CNC数据模型")
@TableName("cnc_model")
public class CNCModel implements Serializable {

    /**编号**/
    @ApiModelProperty(value = "编号")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**客户名**/
    @ApiModelProperty(value = "客户名")
    private String customername;
    /**客户编号**/
    @ApiModelProperty(value = "客户编号")
    private String customerid;
    /**机床名称**/
    @ApiModelProperty(value = "机床名称")
    private String cncname;
    /**机床编号**/
    @ApiModelProperty(value = "机床编号")
    private String cncsn;
    /**机床类型**/
    @ApiModelProperty(value = "机床类型")
    private String cnctype;
    /**机床ip**/
    @ApiModelProperty(value = "机床ip")
    private String cncip;
    /**机床模式**/
    @ApiModelProperty(value = "机床模式")
    private String cncmodel;
    /**机床状态**/
    @ApiModelProperty(value = "机床状态")
    private String cncstate;
    /**通电时间**/
    @ApiModelProperty(value = "通电时间")
    private String powerntime;
    /**运行时间**/
    @ApiModelProperty(value = "运行时间")
    private String runningtime;
    /**加工时间**/
    @ApiModelProperty(value = "加工时间")
    private String cuttingtime;
    /**当前加工零件名**/
    @ApiModelProperty(value = "当前加工零件名")
    private String partname;
    /**当前加工程序名**/
    @ApiModelProperty(value = "当前加工程序名")
    private String programname;
    /**已加工数量**/
    @ApiModelProperty(value = "已加工数量")
    private Long count;
    /**目标加工数量**/
    @ApiModelProperty(value = "目标加工数量")
    private Long targetcount;
    /**主轴负载**/
    @ApiModelProperty(value = "主轴负载")
    private Float spindleload;
    /**主轴转数**/
    @ApiModelProperty(value = "主轴转数")
    private Float spindlespeed;
    /**主轴倍率**/
    @ApiModelProperty(value = "主轴倍率")
    private Float spindlerate;
    /**主轴设定转速**/
    @ApiModelProperty(value = "主轴设定转速")
    private Float spindlespeedset;
    /**进给轴转速**/
    @ApiModelProperty(value = "进给轴转速")
    private Float feedspeed;
    /**进给轴倍率**/
    @ApiModelProperty(value = "进给轴倍率")
    private Float feedrate;
    /**进给轴设定转速**/
    @ApiModelProperty(value = "进给轴设定转速")
    private Float feedspeedset;
    /**报警类型**/
    @ApiModelProperty(value = "报警类型")
    private String alarmtype;
    /**报警号码**/
    @ApiModelProperty(value = "报警号码")
    private String alarmnum;
    /**报警描述**/
    @ApiModelProperty(value = "报警描述")
    private String alarminfo;
    /**刀具号**/
    @ApiModelProperty(value = "刀具号")
    private Long toolnum;
    /**X轴负载**/
    @ApiModelProperty(value = "X轴负载")
    private Float xload;
    /**Y轴负载**/
    @ApiModelProperty(value = "Y轴负载")
    private Float yload;
    /**Z轴负载**/
    @ApiModelProperty(value = "Z轴负载")
    private Float zload;
    /**A轴负载**/
    @ApiModelProperty(value = "A轴负载")
    private Float aload;
    /**B轴负载**/
    @ApiModelProperty(value = "B轴负载")
    private Float bload;
    /**C轴负载**/
    @ApiModelProperty(value = "C轴负载")
    private Float cload;
    /**IO采集功率**/
    @ApiModelProperty(value = "IO采集功率")
    private Float iopower;
    /**采集时间**/
    @ApiModelProperty(value = "采集时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datatime;
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
    /**是否删除*/
    @Excel(name = "是否删除", width = 15, dicCode = "del_flag")
    @Dict(dicCode = "del_flag")
    @ApiModelProperty(value = "是否删除")
    private Integer delFlag;

}
