package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.CNCModel;
import org.jeecg.modules.mqtt.mapper.CNCModelMapper;
import org.jeecg.modules.mqtt.service.ICNCModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 杭州三米明德科技有限公司
 * CNC数据模型
 * @author zzm
 * date 2020-08-06
 */
@Service
public class CNCModelServiceImpl extends ServiceImpl<CNCModelMapper, CNCModel> implements ICNCModelService {

    @Resource
    private CNCModelMapper cncModelMapper;

    @Override
    public List<CNCModel> queryAlarmByEId(CNCModel cncModel) {
        return cncModelMapper.queryAlarmByEId(cncModel);
    }

    @Override
    public List<CNCModel> countCncModel(CNCModel cncModel) {
        return cncModelMapper.countCncModel(cncModel);
    }

    @Override
    public List<CNCModel> countCncModelAll() {
        return cncModelMapper.countCncModelAll();
    }

    @Override
    public Integer countCncToday() {
        return cncModelMapper.countCncToday();
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
    @Override
    //@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Transactional( propagation = Propagation.REQUIRES_NEW)
    public boolean save(CNCModel entity) {
        return retBool(baseMapper.insert(entity));
    }*/

}
