package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.MbpMainplan;
import org.jeecg.modules.mqtt.entity.MbpMainplanabstract;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 生产计划
 * @Author: jeecg-boot
 * @Date:   2020-08-25
 * @Version: V1.0
 */
public interface IMbpMainplanabstractService extends IService<MbpMainplanabstract> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MbpMainplanabstract mbpMainplanabstract, List<MbpMainplan> mbpMainplanList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MbpMainplanabstract mbpMainplanabstract, List<MbpMainplan> mbpMainplanList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
