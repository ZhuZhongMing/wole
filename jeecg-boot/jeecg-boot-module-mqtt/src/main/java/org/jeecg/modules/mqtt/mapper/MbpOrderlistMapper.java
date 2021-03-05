package org.jeecg.modules.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mqtt.entity.MbpOrderlist;
import org.jeecg.modules.mqtt.vo.PlanVO;

import java.util.List;

/**
 * @Description: 订单明细
 * @Author: jeecg-boot
 * @Date:   2020-08-19
 * @Version: V1.0
 */
public interface MbpOrderlistMapper extends BaseMapper<MbpOrderlist> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MbpOrderlist> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 订单明细-通过主表ID查询，生成对应的生产计划明细
	 * @param id 订单编号
	 * @return PlanVO 订单明细对应的计划
	 */
	List<PlanVO> queryListToPlan(String id);
}
