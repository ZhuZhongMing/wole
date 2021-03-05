package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.MbpTechnological;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 工艺信息
 * @Author: jeecg-boot
 * @Date:   2020-10-09
 * @Version: V1.0
 */
public interface IMbpTechnologicalService extends IService<MbpTechnological> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MbpTechnological mbpTechnological, List<MbpTechnologicalDetails> mbpTechnologicalDetailsList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MbpTechnological mbpTechnological, List<MbpTechnologicalDetails> mbpTechnologicalDetailsList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
