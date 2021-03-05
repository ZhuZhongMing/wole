package org.jeecg.modules.mqtt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 每日产能
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
@Data
public class DailyCapacityVO {

	/**
	 * id,自增主键
	 */
	private Integer id;
	/**
	 * 设备编号
	 */
	private String equipmentsn;
	/**
	 * 产能数量-需同昨日设备上传实际数量做对比
	 */
	private Integer count;

	/**
	 * 产能数量-需同昨日设备上传实际数量做对比,保存当天产量
	 */
	private Integer nowCount;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 设备上传的实际数量
	 */
	private Integer equipmentcount;
	/**
	 * 生产计划记录过的数量
	 */
	private Integer taskcount;

	/*时间查询*/
	private String time;

	/**
	 * 设备名称
	 */
	private String equipmentName;
	/**
	 * 开始时间
	 */
	private String createTime_begin;
	/**
	 * 结束
	 */
	private String createTime_end;

	/**
	 * 今日数量
	 */
	private Integer dSum;
	/**
	 * 本月数量
	 */
	private Integer mSum;


}