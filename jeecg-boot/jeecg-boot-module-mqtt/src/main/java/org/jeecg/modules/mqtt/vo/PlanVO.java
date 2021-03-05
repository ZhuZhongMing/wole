package org.jeecg.modules.mqtt.vo;

import lombok.Data;

/**
 * 杭州三米明德科技有限公司
 *  生产计划
 * @author zzm
 * date 2020-08-26
 */
@Data
public class PlanVO {
    /*产品编号*/
    private String materialId;
    /*数量*/
    private Long number;

}
