package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;
import org.jeecg.modules.mqtt.mapper.MbpTechnologicalDetailsMapper;
import org.jeecg.modules.mqtt.service.IMbpTechnologicalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 工艺详情
 * @Author: jeecg-boot
 * @Date:   2020-10-09
 * @Version: V1.0
 */
@Service
public class MbpTechnologicalDetailsServiceImpl extends ServiceImpl<MbpTechnologicalDetailsMapper, MbpTechnologicalDetails> implements IMbpTechnologicalDetailsService {
	
	@Autowired
	private MbpTechnologicalDetailsMapper mbpTechnologicalDetailsMapper;
	
	@Override
	public List<MbpTechnologicalDetails> selectByMainId(String mainId) {
		return mbpTechnologicalDetailsMapper.selectByMainId(mainId);
	}

	@Override
	public List<MbpTechnologicalDetails> queryByEquipment(String technologicalId) {
		return mbpTechnologicalDetailsMapper.queryByEquipment(technologicalId);
	}
}
