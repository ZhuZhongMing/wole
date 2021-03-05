package org.jeecg.modules.mqtt.util.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.mqtt.entity.CNCModel;
import org.jeecg.modules.mqtt.entity.MbpMqttData;
import org.jeecg.modules.mqtt.entity.MbpMqttGatewayConfig;
import org.jeecg.modules.mqtt.service.ICNCModelService;
import org.jeecg.modules.mqtt.service.IMbpMqttDataService;
import org.jeecg.modules.mqtt.service.IMbpMqttGatewayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.List;

import static org.jeecg.modules.mqtt.util.CNCMessageDispose.mqttMessageDispose;

/**
 * 杭州三米明德科技有限公司
 * MQTT客户端回调函数
 * @author zzm
 * date 2020-12-01
 */
@Slf4j
@Component
public class MqttCallBackUtil implements MqttCallback {

    @Autowired
    private IMbpMqttGatewayConfigService mbpMqttConfigService;
    @Autowired
    private IMbpMqttDataService mbpMqttDataService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysBaseAPI iSysBaseAPI;
    @Autowired
    private ICNCModelService icncModelService;

    private MqttClient mqttConnect = null;
    private MbpMqttGatewayConfig mqttConfig = null;

    public static MqttCallBackUtil util;
    @PostConstruct
    public void init() {
        util = this;
        util.mbpMqttConfigService = this.mbpMqttConfigService;
        util.mbpMqttDataService = this.mbpMqttDataService;
        util.redisUtil = this.redisUtil;
        util.iSysBaseAPI = this.iSysBaseAPI;
        util.icncModelService = this.icncModelService;
    }

    /**
     * 连接丢失
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        /*连接断开时清除缓存*/
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.del(mqttConfig.getMqttClientId());
        MQTTConnentionUtil.reconnectionMQTT(mqttConnect, mqttConfig, this, mbpMqttConfigService);
    }

    /**
     *
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        try {
            log.info("=========================================================》" + mqttConfig.getMqttClientId() +"接收消息成功！当前消息主题 : " + topic);
            log.info("===========》"+ mqttConfig.getMqttClientId() +"接收消息成功！当前消息原始状态 : " + JSONObject.toJSONString(mqttMessage));

            String dto = mqttMessage.toString();
            String oldStr = "杭州钱富万向节有限公司";
            String newStr = "杭州沃勒精密机械有限公司";
            if (dto.contains(oldStr)) {
               dto = dto.replace(oldStr, newStr);
            }

            CNCModel cncModel = mqttMessageDispose(mqttMessage);
            cncModel.setCustomername("杭州沃勒精密机械有限公司");
            util.icncModelService.save(cncModel);
            MessageDTO messageDTO = new MessageDTO(Thread.currentThread().getName(),"admin_mqtt",topic, dto);
            // 普通消息
            messageDTO.setCategory(CommonConstant.MSG_CATEGORY_1);
            // 推送普通消息
            util.iSysBaseAPI.sendSysAnnouncement(messageDTO,"admin_mqtt");
            log.info("cnc : " + cncModel);

            /*//mqttConnect.disconnect();
            log.info("=========================================================》" + mqttConfig.getMqttClientId() +"接收消息成功！当前消息主题 : " + topic);

            String theMsg = MessageFormat.format("{0}", new String(mqttMessage.getPayload()));
            JSONObject jsonObject = JSONObject.parseObject(theMsg);
            System.out.println("===========》" + mqttConfig.getMqttClientId() + "接收消息成功！ 数据格式: " + jsonObject);
            String devList = jsonObject.getString("devList");
            String cellLocation = jsonObject.getString("cell_location");
            if (StringUtils.isEmpty(cellLocation) == false) {
                System.out.println("信号质量");
                return;
            }
            if (StringUtils.isEmpty(devList)) {
                System.out.println("网关可能掉线");
                return;
            }
            JSONArray devListArray= JSONArray.parseArray(devList);
            JSONArray varList = devListArray.getJSONObject(0).getJSONArray("varList");
            if (StringUtils.isEmpty(varList)) {
                System.out.println("设备检测否在线");
                return;
            }
            System.out.println("===== : " + JSON.toJSONString(varList));
            // 获取设备ID
            String devNo = devListArray.getJSONObject(0).getString("devNo");
            log.info("===========》" + mqttConfig.getMqttClientId() + "接收消息成功！ 原始格式，devNo: " + devNo);
            List<MbpMqttData> mbpMqttDataList = JSONArray.parseArray(varList.toJSONString(), MbpMqttData.class);
            mbpMqttDataList.forEach(item -> {
                //System.out.println("var Name：" + item.getVarName());
                // 客户端ID
                item.setConfigId(mqttConfig.getId());
                // 设备ID
                item.setEquipmentId(devNo);
                util.mbpMqttDataService.save(item);
            });
            MessageDTO messageDTO = new MessageDTO(Thread.currentThread().getName(),"admin_mqtt",topic, mqttMessage.toString());
            // 普通消息
            messageDTO.setCategory(CommonConstant.MSG_CATEGORY_1);
            // 推送普通消息
            util.iSysBaseAPI.sendSysAnnouncement(messageDTO,"admin_mqtt");*/
        } catch (JSONException e) {
            log.info(mqttConfig.getMqttClientId() + "取消订阅");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  发布消息
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    /**
     * 开启连接
     */
    //@PostConstruct
    public void  run(MbpMqttGatewayConfig mqtt) throws MqttException {
        mqttConfig = mqtt;
        mqttConnect = MQTTConnentionUtil.getMQTTConnect(mqtt);
        mqttConnect.setCallback(this);
        // 连接成功， 手动设置连接状态，delFlag
        MbpMqttGatewayConfig newMqtt = new MbpMqttGatewayConfig();
        newMqtt.setId(mqtt.getId());
        newMqtt.setDelFlag(1);
        util.mbpMqttConfigService.updateById(newMqtt);
    }
}
