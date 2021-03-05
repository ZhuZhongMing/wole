package org.jeecg.modules.mqtt.vo;

import lombok.Data;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;

import java.util.List;

/**
 * 杭州三米明德科技有限公司
 *
 * @author zzm
 * date 2020-10-30
 */
@Data
public class ScheduleVO {

    /**设备所属工序**/
    private String workprocessId;

    /**工艺详情**/
    private List<MbpTechnologicalDetails> list;

}
