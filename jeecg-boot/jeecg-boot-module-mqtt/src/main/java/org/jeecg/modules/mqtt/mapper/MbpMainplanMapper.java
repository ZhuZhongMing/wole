package org.jeecg.modules.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mqtt.entity.MbpMainplan;
import org.jeecg.modules.mqtt.vo.PlanBOMVO;

import java.util.List;

/**
 * @Description: 生产计划明细
 * @Author: jeecg-boot
 * @Date:   2020-08-25
 * @Version: V1.0
 */
public interface MbpMainplanMapper extends BaseMapper<MbpMainplan> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MbpMainplan> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 根据主计划id查询明细详情
	 * @param mbpMainplan
	 * @return
	 */
	List<PlanBOMVO> queryListByMainId(MbpMainplan mbpMainplan);

	/**
	 * 查询计划明细中计划摘要中订单标题
	 * @param ids 计划明细ids
	 * @return
	 */
	List<MbpMainplan> getOrderTitleByIds(List<String> ids);
}
