package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;

import java.util.List;

/**
 * @Description: 工艺详情
 * @Author: jeecg-boot
 * @Date:   2020-10-09
 * @Version: V1.0
 */
public interface IMbpTechnologicalDetailsService extends IService<MbpTechnologicalDetails> {

	public List<MbpTechnologicalDetails> selectByMainId(String mainId);

    /**
     * 查询产品工艺详情
     * @param technologicalId
     * @return
     */
    List<MbpTechnologicalDetails> queryByEquipment(String technologicalId);
}
