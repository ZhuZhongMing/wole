package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.CNCModel;

import java.util.List;

/**
 * 杭州三米明德科技有限公司
 * cnc 数据模型
 * @author zzm
 * date 2020-07-24
 */
public interface ICNCModelService extends IService<CNCModel> {

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
