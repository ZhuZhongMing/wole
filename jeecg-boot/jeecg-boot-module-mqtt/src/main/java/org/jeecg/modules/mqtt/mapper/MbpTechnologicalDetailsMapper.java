package org.jeecg.modules.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;

import java.util.List;

/**
 * @Description: 工艺详情
 * @Author: jeecg-boot
 * @Date:   2020-10-09
 * @Version: V1.0
 */
public interface MbpTechnologicalDetailsMapper extends BaseMapper<MbpTechnologicalDetails> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MbpTechnologicalDetails> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 查询产品工艺详情
	 * @param technologicalId
	 * @return
	 */
	List<MbpTechnologicalDetails> queryByEquipment(String technologicalId);
}
