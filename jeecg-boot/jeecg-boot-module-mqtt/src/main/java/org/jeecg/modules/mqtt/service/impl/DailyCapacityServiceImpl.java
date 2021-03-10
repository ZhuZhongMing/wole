package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.DailyCapacity;
import org.jeecg.modules.mqtt.mapper.DailyCapacityMapper;
import org.jeecg.modules.mqtt.service.IDailyCapacityService;
import org.jeecg.modules.mqtt.vo.DailyCapacityVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description: 每日产能
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
@Service
public class DailyCapacityServiceImpl extends ServiceImpl<DailyCapacityMapper, DailyCapacity> implements IDailyCapacityService {

    @Resource
    private DailyCapacityMapper dailyCapacityMapper;

    @Override
    public Integer sumMonthCapacity(Date time) {
        return dailyCapacityMapper.sumMonthCapacity(time);
    }

    @Override
    public Integer sumDayCapacity(Date time) {
        return dailyCapacityMapper.sumDayCapacity(time);
    }

    @Override
    public Integer countDayEquipmentOnline(Date time) {
        return dailyCapacityMapper.countDayEquipmentOnline(time);
    }

    @Override
    public List<DailyCapacityVO> groupByEquipment(DailyCapacityVO dailyCapacityVO) {
        return dailyCapacityMapper.groupByEquipment(dailyCapacityVO);
    }

    @Override
    public List<DailyCapacity> sumDaily(DailyCapacity dailyCapacity) {
        return dailyCapacityMapper.sumDaily(dailyCapacity);
    }

    @Override
    public Integer sumDailyAll() {
        return dailyCapacityMapper.sumDailyAll();
    }

    @Override
    public List<DailyCapacity> sumDailyDay() {
        return dailyCapacityMapper.sumDailyDay();
    }

    @Override
    public Integer sumDailyToday() {
        return dailyCapacityMapper.sumDailyToday();
    }

}
