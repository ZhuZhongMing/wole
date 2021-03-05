package org.jeecg.modules.mqtt.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jeecg.modules.mqtt.entity.CNCModel;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 杭州三米明德科技有限公司
 *  LiDengCNC --- mqtt消息处理工具类
 * @author zzm
 * date 2020-08-24
 */
@Slf4j
public class CNCMessageDispose {

    public static CNCModel mqttMessageDispose(MqttMessage message){
        String theMsg = MessageFormat.format("{0}", new String(message.getPayload()));
        Map<String, Object> parse = (Map<String, Object>) JSONObject.parse(theMsg);
        List<Map<String, String>> data = (List<Map<String, String>>) parse.get("data");

        CNCModel cncModel = new CNCModel();
        cncModel.setCustomername(data.get(0).get("v"));
        cncModel.setCustomerid(data.get(1).get("v"));
        cncModel.setCncname(data.get(2).get("v"));
        cncModel.setCncsn(data.get(3).get("v"));
        cncModel.setCnctype(data.get(4).get("v"));
        cncModel.setCncip(data.get(5).get("v"));
        cncModel.setCncmodel(data.get(6).get("v"));
        cncModel.setCncstate(data.get(7).get("v"));
        cncModel.setPowerntime(data.get(8).get("v"));
        cncModel.setRunningtime(data.get(9).get("v"));
        cncModel.setCuttingtime(data.get(10).get("v"));
        cncModel.setPartname(data.get(11).get("v"));
        cncModel.setProgramname(data.get(12).get("v"));

        // log.info("isString : " + (data.get(29).get("v") instanceof String));
        if ("".equals(data.get(13).get("v")) && data.get(13).get("v").length() == 0) {
            cncModel.setCount(0L);
        }else {
            cncModel.setCount(Long.parseLong(data.get(13).get("v")));
        }

        if ("".equals(data.get(14).get("v"))) {
            cncModel.setTargetcount(0L);
        }else {
            cncModel.setTargetcount(Long.parseLong(data.get(14).get("v")));
        }

        if ("".equals(data.get(15).get("v"))) {
            cncModel.setSpindleload(0F);
        }else {
            cncModel.setSpindleload(Float.parseFloat(data.get(15).get("v")));
        }

        if ("".equals(data.get(16).get("v"))) {
            cncModel.setSpindlespeed(0F);
        }else {
            cncModel.setSpindlespeed(Float.parseFloat(data.get(16).get("v")));
        }

        if ("".equals(data.get(17).get("v"))) {
            cncModel.setSpindlerate(0F);
        }else {
            cncModel.setSpindlerate(Float.parseFloat(data.get(17).get("v")));
        }

        if ("".equals(data.get(18).get("v"))) {
            cncModel.setSpindlespeedset(0F);
        }else {
            cncModel.setSpindlespeedset(Float.parseFloat(data.get(18).get("v")));
        }

        if ("".equals(data.get(19).get("v"))) {
            cncModel.setFeedspeed(0F);
        }else {
            cncModel.setFeedspeed(Float.parseFloat(data.get(19).get("v")));
        }

        if ("".equals(data.get(20).get("v"))) {
            cncModel.setFeedrate(0F);
        }else {
            cncModel.setFeedrate(Float.parseFloat(data.get(20).get("v")));
        }

        if ("".equals(data.get(21).get("v"))) {
            cncModel.setFeedspeedset(0F);
        }else {
            cncModel.setFeedspeedset(Float.parseFloat(data.get(21).get("v")));
        }

        cncModel.setAlarmtype(data.get(22).get("v"));
        cncModel.setAlarmnum(data.get(23).get("v"));
        cncModel.setAlarminfo(data.get(24).get("v"));

        if ("".equals(data.get(25).get("v"))) {
            cncModel.setToolnum(0L);
        }else {
            cncModel.setToolnum(Long.parseLong(data.get(25).get("v")));
        }

        if ("".equals(data.get(26).get("v"))) {
            cncModel.setXload(0F);
        }else {
            cncModel.setXload(Float.parseFloat(data.get(26).get("v")));
        }

        if ("".equals(data.get(27).get("v"))) {
            cncModel.setYload(0F);
        }else {
            cncModel.setYload(Float.parseFloat(data.get(27).get("v")));
        }

        if ("".equals(data.get(28).get("v"))) {
            cncModel.setZload(0F);
        }else {
            cncModel.setZload(Float.parseFloat(data.get(28).get("v")));
        }

        if ("".equals(data.get(29).get("v"))) {
            cncModel.setAload(0F);
        }else {
            cncModel.setAload(Float.parseFloat(data.get(29).get("v")));
        }

        if ("".equals(data.get(30).get("v"))) {
            cncModel.setBload(0F);
        }else {
            cncModel.setBload(Float.parseFloat(data.get(30).get("v")));
        }

        if ("".equals(data.get(31).get("v"))) {
            cncModel.setCload(0F);
        }else {
            cncModel.setCload(Float.parseFloat(data.get(31).get("v")));
        }

        if ("".equals(data.get(32).get("v"))) {
            cncModel.setIopower(0F);
        }else {
            cncModel.setIopower(Float.parseFloat(data.get(32).get("v")));
        }

        try {
            cncModel.setDatatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get(33).get("v")));
        } catch (ParseException e) {
            cncModel.setDatatime(new Date());
            log.error("消息中采集时间异常！已取用系统当前时间，异常信息:" + e);
            e.printStackTrace();
        }

        return  cncModel;
    }

}
