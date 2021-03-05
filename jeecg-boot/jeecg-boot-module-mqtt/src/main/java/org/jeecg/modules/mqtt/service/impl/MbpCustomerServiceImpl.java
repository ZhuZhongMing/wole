package org.jeecg.modules.mqtt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mqtt.entity.MbpCustomer;
import org.jeecg.modules.mqtt.mapper.MbpCustomerMapper;
import org.jeecg.modules.mqtt.service.IMbpCustomerService;
import org.springframework.stereotype.Service;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
@Service
public class MbpCustomerServiceImpl extends ServiceImpl<MbpCustomerMapper, MbpCustomer> implements IMbpCustomerService {

}
