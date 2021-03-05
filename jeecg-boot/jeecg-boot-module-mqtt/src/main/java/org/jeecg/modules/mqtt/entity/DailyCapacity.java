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

import java.util.Date;

/**
 * @Description: 每日产能
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
@Data
@TableName("daily_capacity")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="daily_capacity对象", description="每日产能")
public class DailyCapacity {
    
	/**id,自增主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id,自增主键")
	private Integer id;
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
    @ApiModelProperty(value = "设备编号")
	@Dict(dictTable = "mbp_equipment", dicText = "equipment_name", dicCode = "id")
	private String equipmentsn;
	/**产能数量-需同昨日设备上传实际数量做对比*/
	@Excel(name = "产能数量-需同昨日设备上传实际数量做对比", width = 15)
    @ApiModelProperty(value = "产能数量-需同昨日设备上传实际数量做对比")
	private Integer count;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**设备上传的实际数量*/
	@Excel(name = "设备上传的实际数量", width = 15)
    @ApiModelProperty(value = "设备上传的实际数量")
	private Integer equipmentcount;
	/**生产计划记录过的数量*/
	@Excel(name = "生产计划记录过的数量", width = 15)
    @ApiModelProperty(value = "生产计划记录过的数量")
	private Integer taskcount;
}
