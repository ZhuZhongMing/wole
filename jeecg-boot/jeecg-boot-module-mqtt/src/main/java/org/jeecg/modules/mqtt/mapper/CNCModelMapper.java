package org.jeecg.modules.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.mqtt.entity.CNCModel;

import java.util.List;

/**
 * 杭州三米明德科技有限公司
 *   CNC数据模型
 * @author zzm
 * date 2020-08-06
 */
public interface CNCModelMapper extends BaseMapper<CNCModel> {

    /**
     * 查询指定设备报警数据
     * @param cncModel
     */
    List<CNCModel> queryAlarmByEId(CNCModel cncModel);

    /**
     * 采集频率 -- 根据设备查询
     * @param cncModel
     * @return
     */
    List<CNCModel> countCncModel(CNCModel cncModel);

}
