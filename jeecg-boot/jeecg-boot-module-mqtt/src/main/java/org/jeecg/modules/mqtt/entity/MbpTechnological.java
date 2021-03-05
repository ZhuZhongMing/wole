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
 * @Description: 工艺信息
 * @Author: jeecg-boot
 * @Date:   2020-10-09
 * @Version: V1.0
 */
@ApiModel(value="mbp_technological对象", description="工艺信息")
@Data
@TableName("mbp_technological")
public class MbpTechnological implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**工艺名称*/
	@Excel(name = "工艺名称", width = 15)
    @ApiModelProperty(value = "工艺名称")
    private String technologicalName;
	/**工艺说明*/
	@Excel(name = "工艺说明", width = 15)
    @ApiModelProperty(value = "工艺说明")
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
	/**删除标识*/
	@Excel(name = "删除标识", width = 15, dicCode = "del_flag")
    @Dict(dicCode = "del_flag")
    @ApiModelProperty(value = "删除标识")
    private Integer delFlag;
}
