package org.jeecg.modules.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 物料表
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
@Data
@TableName("mbp_material")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mbp_material对象", description="物料表")
public class MbpMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

	/**编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;
	/**物料编码*/
	@Excel(name = "物料编码", width = 15)
    @ApiModelProperty(value = "物料编码")
    private String materialId;
	/**物料名称*/
	@Excel(name = "物料名称", width = 15)
    @ApiModelProperty(value = "物料名称")
    private String materialName;
	/**别名*/
	@Excel(name = "别名", width = 15)
    @ApiModelProperty(value = "别名")
    private String alias;
	/**英文名*/
	@Excel(name = "英文名", width = 15)
    @ApiModelProperty(value = "英文名")
    private String englishName;
	/**检索码*/
	@Excel(name = "检索码", width = 15)
    @ApiModelProperty(value = "检索码")
    private String indexCode;
	/**等级*/
	@Excel(name = "等级", width = 15)
    @ApiModelProperty(value = "等级")
    private String level;
	/**型号*/
	@Excel(name = "型号", width = 15)
    @ApiModelProperty(value = "型号")
    private String model;
	/**规格*/
	@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private String special;
	/**通用规格*/
	@Excel(name = "通用规格", width = 15)
    @ApiModelProperty(value = "通用规格")
    private String generalSpecial;
	/**商标*/
	@Excel(name = "商标", width = 15)
    @ApiModelProperty(value = "商标")
    private String brand;
	/**ABC类*/
	@Excel(name = "ABC类", width = 15)
    @ApiModelProperty(value = "ABC类")
    private String abcCategory;
	/**颜色*/
	@Excel(name = "颜色", width = 15)
    @ApiModelProperty(value = "颜色")
    private String color;
	/**计算特性1*/
	@Excel(name = "计算特性1", width = 15)
    @ApiModelProperty(value = "计算特性1")
    private BigDecimal spe1;
	/**计算特性2*/
	@Excel(name = "计算特性2", width = 15)
    @ApiModelProperty(value = "计算特性2")
    private BigDecimal spe2;
	/**条形码*/
	@Excel(name = "条形码", width = 15)
    @ApiModelProperty(value = "条形码")
    private String barcode;
	/**物料分类编码*/
	@Excel(name = "物料分类编码", width = 15)
    @ApiModelProperty(value = "物料分类编码")
    private String materialTypeEncode;
	/**单位Id*/
	@Excel(name = "单位Id", width = 15)
    @ApiModelProperty(value = "单位Id")
    private String unitid;
	/**是否虚拟件0-否,1-是*/
	@Excel(name = "是否虚拟件0-否,1-是", width = 15)
    @ApiModelProperty(value = "是否虚拟件0-否,1-是")
    private Integer isVirtalPart;
	/**是否外购件0-否,1-是*/
	@Excel(name = "是否外购件0-否,1-是", width = 15)
    @ApiModelProperty(value = "是否外购件0-否,1-是")
    private Integer isPurchasePart;
	/**是否通用件0-否,1-是*/
	@Excel(name = "是否通用件0-否,1-是", width = 15)
    @ApiModelProperty(value = "是否通用件0-否,1-是")
    private Integer isGeneralPart;
	/**安全库存*/
	@Excel(name = "安全库存", width = 15)
    @ApiModelProperty(value = "安全库存")
    private BigDecimal minWhStorge;
	/**最高库存*/
	@Excel(name = "最高库存", width = 15)
    @ApiModelProperty(value = "最高库存")
    private BigDecimal maxWhStorge;
	/**当前库存*/
	@Excel(name = "当前库存", width = 15)
    @ApiModelProperty(value = "当前库存")
    private BigDecimal currentWhStogre;
	/**允许销售0-禁止,1-允许*/
	@Excel(name = "允许销售0-禁止,1-允许", width = 15)
    @ApiModelProperty(value = "允许销售0-禁止,1-允许")
    private Integer isSell;
	/**序列号*/
	@Excel(name = "序列号", width = 15)
    @ApiModelProperty(value = "序列号")
    private String serialNumber;
	/**采购单位*/
	@Excel(name = "采购单位", width = 15)
    @ApiModelProperty(value = "采购单位")
    private String purchaseOrganize;
	/**产地*/
	@Excel(name = "产地", width = 15)
    @ApiModelProperty(value = "产地")
    private String productAddress;
	/**是否入库检验*/
	@Excel(name = "是否入库检验", width = 15)
    @ApiModelProperty(value = "是否入库检验")
    private Integer isWhInSpect;
	/**成本日期*/
	@Excel(name = "成本日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "成本日期")
    private Date costDate;
	/**标准成本价*/
	@Excel(name = "标准成本价", width = 15)
    @ApiModelProperty(value = "标准成本价")
    private BigDecimal standardCost;
	/**销售价*/
	@Excel(name = "销售价", width = 15)
    @ApiModelProperty(value = "销售价")
    private BigDecimal salesCost;
	/**采购价*/
	@Excel(name = "采购价", width = 15)
    @ApiModelProperty(value = "采购价")
    private BigDecimal purchaseCost;
	/**采购提前天数*/
	@Excel(name = "采购提前天数", width = 15)
    @ApiModelProperty(value = "采购提前天数")
    private Integer prepareDays;
	/**工序编号*/
	@Excel(name = "工序编号", width = 15)
    @ApiModelProperty(value = "工序编号")
    private String processId;
	/**是否停用0-否,1-是*/
	@Excel(name = "是否停用0-否,1-是", width = 15)
    @ApiModelProperty(value = "是否停用0-否,1-是")
    private Integer isStop;
	/**起初数值*/
	@Excel(name = "起初数值", width = 15)
    @ApiModelProperty(value = "起初数值")
    private BigDecimal startNumber;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private String figurePath;
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

	/**数量**/
	@TableField(exist = false)
    private BigDecimal number;
}
