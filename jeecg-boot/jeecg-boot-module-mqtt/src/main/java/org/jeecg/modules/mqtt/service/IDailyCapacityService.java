package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.DailyCapacity;
import org.jeecg.modules.mqtt.vo.DailyCapacityVO;

import java.util.Date;
import java.util.List;

/**
 * @Description: 每日产能
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
public interface IDailyCapacityService extends IService<DailyCapacity> {

    /**
     * 按月统计参量
     * @param time
     * @return
     */
    Integer sumMonthCapacity(Date time);

    /**
     * 按天统计参量
     * @param time
     * @return
     */
    Integer sumDayCapacity(Date time);

    /**
     * 按天统计设备在线状况
     * @param time
     * @return
     */
    Integer countDayEquipmentOnline(Date time);

    /**
     * 产量对比 --根据设备，条件查询
     * @param dailyCapacityVO
     * @return
     */
    List<DailyCapacityVO> groupByEquipment(DailyCapacityVO dailyCapacityVO);

}
