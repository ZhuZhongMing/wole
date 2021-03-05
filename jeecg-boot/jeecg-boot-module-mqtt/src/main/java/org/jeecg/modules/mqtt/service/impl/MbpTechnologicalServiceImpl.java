package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.MbpTechnological;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;
import org.jeecg.modules.mqtt.mapper.MbpTechnologicalDetailsMapper;
import org.jeecg.modules.mqtt.mapper.MbpTechnologicalMapper;
import org.jeecg.modules.mqtt.service.IMbpTechnologicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 工艺信息
 * @Author: jeecg-boot
 * @Date:   2020-10-09
 * @Version: V1.0
 */
@Service
public class MbpTechnologicalServiceImpl extends ServiceImpl<MbpTechnologicalMapper, MbpTechnological> implements IMbpTechnologicalService {

	@Autowired
	private MbpTechnologicalMapper mbpTechnologicalMapper;
	@Autowired
	private MbpTechnologicalDetailsMapper mbpTechnologicalDetailsMapper;
	
	@Override
	@Transactional
	public void saveMain(MbpTechnological mbpTechnological, List<MbpTechnologicalDetails> mbpTechnologicalDetailsList) {
		mbpTechnologicalMapper.insert(mbpTechnological);
		if(mbpTechnologicalDetailsList!=null && mbpTechnologicalDetailsList.size()>0) {
			for(MbpTechnologicalDetails entity:mbpTechnologicalDetailsList) {
				//外键设置
				entity.setTechnologicalId(mbpTechnological.getId());
				mbpTechnologicalDetailsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(MbpTechnological mbpTechnological,List<MbpTechnologicalDetails> mbpTechnologicalDetailsList) {
		mbpTechnologicalMapper.updateById(mbpTechnological);
		
		//1.先删除子表数据
		mbpTechnologicalDetailsMapper.deleteByMainId(mbpTechnological.getId());
		
		//2.子表数据重新插入
		if(mbpTechnologicalDetailsList!=null && mbpTechnologicalDetailsList.size()>0) {
			for(MbpTechnologicalDetails entity:mbpTechnologicalDetailsList) {
				//外键设置
				entity.setTechnologicalId(mbpTechnological.getId());
				mbpTechnologicalDetailsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		mbpTechnologicalDetailsMapper.deleteByMainId(id);
		mbpTechnologicalMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			mbpTechnologicalDetailsMapper.deleteByMainId(id.toString());
			mbpTechnologicalMapper.deleteById(id);
		}
	}
	
}
