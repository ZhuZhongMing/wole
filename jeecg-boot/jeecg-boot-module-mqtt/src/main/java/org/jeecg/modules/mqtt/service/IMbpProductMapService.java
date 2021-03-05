package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.MbpProductMap;

import java.util.List;
import java.util.Map;

/**
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date:   2020-08-18
 * @Version: V1.0
 */
public interface IMbpProductMapService extends IService<MbpProductMap> {

    /**
     * 根据 ids查询，查询产品所需的物料名称（多个物料名逗号隔开）
     * @param ids - 产品编号
     * @return
     */
    Map<String, String> getMaterialNamesByIds(List<String> ids);
}
