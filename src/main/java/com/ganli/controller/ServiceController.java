package com.ganli.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ganli.common.entity.ResponseMessage;
import com.ganli.entity.Event;
import com.ganli.entity.FeedBack;
import com.ganli.entity.Merchant;
import com.ganli.entity.User;
import com.ganli.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by hao.cheng on 2016/3/11.
 * @desc 事件处理controller
 * @date 2016-3-11 09:57:29
 */
@RestController
@RequestMapping("service")
public class ServiceController extends BaseController{
    Logger log = LoggerFactory.getLogger(ServiceController.class);
    @Resource
    EventService eventService;

    /**
     * 同步上传数据
     * @param data
     * @param callback
     * @return
     */
    @RequestMapping("syncUpload")
    public Object syncUpload(String data,String callback){
        rm = new ResponseMessage();
        try{
            JSONObject obj = JSONObject.parseObject(data);
            String uid = obj.getString("uid");
            List events =  Arrays.asList(obj.getJSONArray("event").toArray());     //获取事件列表
            List gifts = Arrays.asList(obj.getJSONArray("gift").toArray());        //获取收礼单列表
            List repays = Arrays.asList(obj.getJSONArray("repay").toArray());      //获取还礼单列表
            eventService.saveData(events,gifts,repays,uid);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("系统异常");
            log.info(e.getMessage());
            return jsonpHandler(rm,callback);
        }

    }

    /**
     * 同步下载数据
     * @param data
     * @param callback
     * @return
     */
    @RequestMapping("syncDownload")
    public Object syncDownload(String data,String callback){
        rm = new ResponseMessage();
        User user= JSONObject.parseObject(data,User.class);
        rm.setEvent(eventService.findEvents(user.getUserUid()));
        rm.setGift(eventService.findGifts(user.getUserUid()));
        rm.setRepay(eventService.findRepays(user.getUserUid()));
        return jsonpHandler(rm,callback);
    }
    @RequestMapping("saveFeedBck")
    public Object saveFeedBck(String data,String callback){
        try{
            rm = new ResponseMessage();
            FeedBack feedBack = JSONObject.parseObject(data,FeedBack.class);
            feedBack.setFeedBckUid(UUID.randomUUID().toString());
            eventService.saveFeedBack(feedBack);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("操作异常");
            return jsonpHandler(rm,callback);
        }

    }
    @RequestMapping("saveMerchant")
    public Object saveMerchant(String data,String callback){
        try{
            rm = new ResponseMessage();
            Merchant merchant = JSONObject.parseObject(data,Merchant.class);
            if(merchant.getMerchantUid() == null){
                merchant.setMerchantUid(UUID.randomUUID().toString());
            }
            eventService.saveMerchant(merchant);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("操作异常");
            return jsonpHandler(rm,callback);
        }

    }

    /**
     * 查询商家列表
     * @param data
     * @param callback
     * @return
     */
    @RequestMapping("findMerchantList")
    public Object findMerchantList(String data,String callback){
        rm = new ResponseMessage();
        JSONObject obj = JSONObject.parseObject(data);
        int start = Integer.valueOf(obj.getString("start"));
        int limit = Integer.valueOf(obj.getString("limit"));
        Merchant merchant = JSONObject.parseObject(obj.getString("merchant"), Merchant.class);
        List merchants = eventService.findMerchantList(start, limit, merchant);
        rm.setDatas(merchants);
        return jsonpHandler(rm,callback);
    }

    /**
     * 记录操作次数
     * @param tp
     * @param type
     * @param phoneId
     * @param callback
     * @return
     */
    @RequestMapping("record/{tp}")
    public Object recordService(@PathVariable("tp") Integer tp,
                                @RequestParam(defaultValue = "", required = false) String type, String phoneId,String callback) {
        rm = new ResponseMessage();
        try {
            switch (tp) {
                case 1:
                    eventService.insertRecordInstall(phoneId);
                    break;
                case 2:
                    eventService.insertRecordEvent(phoneId, type);
                    break;
                case 3:
                    eventService.insertRecordMerchant(phoneId);
                    break;
            }
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("系统错误");
            log.info(e.getMessage());
            return jsonpHandler(rm,callback);
        }
    }

    /**
     * 查询操作记录
     * @param tp
     * @param startTime
     * @param endTime
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("count/{tp}")
    public Object countRecord(@PathVariable("tp")Integer tp,
                              @RequestParam(defaultValue = "",required = false)String startTime,
                              @RequestParam(defaultValue = "",required = false)String endTime,
                              @RequestParam(defaultValue = "",required = false)String type,
                              String callback){
        rm = new ResponseMessage();
        try{
            Integer count = null;
            switch (tp){
                case 1:count = eventService.countRecordInstall(startTime,endTime); break;
                case 2:count = eventService.countRecordEvent(startTime,endTime,type); break;
                case 3:count = eventService.countRecordMerchant(startTime,endTime); break;
            }
            rm.setData(count);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("系统异常");
            return jsonpHandler(rm,callback);
        }
    }

    public String toUppercaseFirst(String str){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < str.length(); i ++){
            if(i == 0){
                sb.append(String.valueOf(str.charAt(i)).toUpperCase());
            }else{
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
}
