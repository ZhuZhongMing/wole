package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.MbpMainplan;
import org.jeecg.modules.mqtt.mapper.MbpMainplanMapper;
import org.jeecg.modules.mqtt.service.IMbpMainplanService;
import org.jeecg.modules.mqtt.vo.PlanBOMVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 生产计划明细
 * @Author: jeecg-boot
 * @Date:   2020-08-25
 * @Version: V1.0
 */
@Service
public class MbpMainplanServiceImpl extends ServiceImpl<MbpMainplanMapper, MbpMainplan> implements IMbpMainplanService {
	
	@Resource
	private MbpMainplanMapper mbpMainplanMapper;
	
	@Override
	public List<MbpMainplan> selectByMainId(String mainId) {
		return mbpMainplanMapper.selectByMainId(mainId);
	}

	@Override
	public List<PlanBOMVO> queryListByMainId(MbpMainplan mbpMainplan) {
		return mbpMainplanMapper.queryListByMainId(mbpMainplan);
	}

	@Override
	public Map<String, String> getOrderTitleByIds(List<String> ids) {
		List<MbpMainplan> list = this.baseMapper.getOrderTitleByIds(ids);
		Map<String, String> res = new HashMap<String, String>();
		list.forEach(item -> {
					if (res.get(item.getId()) == null) {
						res.put(item.getId(), item.getOrderTitle());
					} else {
						res.put(item.getId(), res.get(item.getId()) + "," + item.getOrderTitle());
					}
				}
		);
		return res;
	}
}
