package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.MbpOrderlist;
import org.jeecg.modules.mqtt.mapper.MbpOrderlistMapper;
import org.jeecg.modules.mqtt.service.IMbpOrderlistService;
import org.jeecg.modules.mqtt.vo.PlanVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2020-08-19
 * @Version: V1.0
 */
@Service
public class MbpOrderlistServiceImpl extends ServiceImpl<MbpOrderlistMapper, MbpOrderlist> implements IMbpOrderlistService {
	
	@Resource
	private MbpOrderlistMapper mbpOrderlistMapper;
	
	@Override
	public List<MbpOrderlist> selectByMainId(String mainId) {
		return mbpOrderlistMapper.selectByMainId(mainId);
	}

	@Override
	public List<PlanVO> queryListToPlan(String id) {
		return mbpOrderlistMapper.queryListToPlan(id);
	}
}
