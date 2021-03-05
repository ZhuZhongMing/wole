package org.jeecg.modules.mqtt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mqtt.entity.MbpMainplan;
import org.jeecg.modules.mqtt.vo.PlanBOMVO;

import java.util.List;
import java.util.Map;

/**
 * @Description: 生产计划明细
 * @Author: jeecg-boot
 * @Date:   2020-08-25
 * @Version: V1.0
 */
public interface IMbpMainplanService extends IService<MbpMainplan> {

	public List<MbpMainplan> selectByMainId(String mainId);

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
    Map<String, String> getOrderTitleByIds(List<String> ids);
}
