package org.jeecg.modules.mqtt.vo;
import lombok.Data;
import org.jeecg.modules.mqtt.entity.MbpMaterial;

import java.util.Date;
import java.util.HashSet;


/**
 * 杭州三米明德科技有限公司
 *  生产计划
 * @author zzm
 * date 2020-08-26
 */
@Data
public class PlanBOMVO {
    /**主计划编号*/
    private String mainplanid;
    /**主计划摘要编号*/
    private String mainplanAbstractId;
    /**数量*/
    private Long number;

    /**产品id*/
    private String id;
    /**产品名称*/
    private String productName;

    /**物料编号*/
    private String materialId;
    /**物料名称*/
    private String materialName;

    /**产品型号*/
    private String productType;
    /**产品规格*/
    private String productSize;
    /**产品颜色*/
    private String productColor;
    /**备注*/
    private String disp;
    /**创建人*/
    private String createBy;
    /**创建时间*/
    private Date createTime;
    /**修改人*/
    private String updataBy;
    /**修改时间*/
    private Date updataTime;
    /**删除标识0-正常,1-已删除*/
    private Integer delFlag;
    /**产品图片*/
    private String productImage;

    /**配件列表*/
    HashSet<MbpMaterial> materialList;

}
