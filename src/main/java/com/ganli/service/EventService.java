package com.ganli.service;

import com.alibaba.fastjson.JSONObject;
import com.ganli.dao.EventDao;
import com.ganli.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hao.cheng on 2016/3/15.
 */
@Service
public class EventService {
    Logger log = LoggerFactory.getLogger(EventService.class);
    @Resource
    EventDao eventDao;

    @Transactional
    public void saveData(List<Event> events,List<GiftList> gifts,List<RepayList> repayLists,String uid){
        try{
            if(events.size() > 0){
                eventDao.deleteEvent(uid);    //删除原事件
                eventDao.saveEvent(events);       //批量插入新事件
            }
            if(gifts.size() > 0){
                eventDao.deleteGift(uid);
                eventDao.saveGift(gifts);
            }
            if (repayLists.size() > 0){
                eventDao.deleteRepay(uid);
                eventDao.saveRepay(repayLists);
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
    @Transactional
    public void saveDataOnly(List<Event> events,List<GiftList> gifts,List<RepayList> repayLists){
        try{
            if(events.size() > 0){
                eventDao.saveEvent(events);       //批量插入新事件
            }
            if(gifts.size() > 0){
                eventDao.saveGift(gifts);
            }
            if (repayLists.size() > 0){
                eventDao.saveRepay(repayLists);
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
    @Transactional
    public void updateDataOnly(List<Event> events,List<GiftList> gifts,List<RepayList> repayLists){
        try{
            if(events.size() > 0){
                eventDao.batchUpdateEvent(events);       //批量插入新事件
            }
            if(gifts.size() > 0){
                eventDao.batchUpdateGift(gifts);
            }
            if (repayLists.size() > 0){
                eventDao.batchUpdateRepay(repayLists);
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
    @Transactional
    public void deleteDataOnly(List<Event> events,List<GiftList> gifts,List<RepayList> repayLists){
        try{
            if(events.size() > 0){
                eventDao.batchDeleteEvent(events);       //批量插入新事件
            }
            if(gifts.size() > 0){
                eventDao.batchDeleteGift(gifts);
            }
            if (repayLists.size() > 0){
                eventDao.batchDeleteRepay(repayLists);
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
    public List<Event> findEvents(String uid){
        return eventDao.findEventList(uid);
    }
    public List<GiftList> findGifts(String uid){
        return eventDao.findGiftList(uid, null, null);
    }
    public List<RepayList> findRepays(String uid){
        return eventDao.findRepayList(uid);
    }
    @Transactional
    public Integer saveFeedBack(FeedBack feedBack){ //保存反馈信息
        return eventDao.saveFeedBack(feedBack);
    }
    @Transactional
    public Integer saveMerchant(Merchant merchant,List<Map<String,Object>> list){
        eventDao.saveMerchant(merchant);
        if(list.size() > 0){
            eventDao.deleteImgs(merchant.getMerchantUid());
            eventDao.insertImgs(list);
        }
        return null;
    }
    public List<Merchant> findMerchantList(int start,int limit,Merchant merchant){
        return eventDao.findMerchantList(start, limit, merchant);
    }
    @Transactional
    public Integer insertRecordInstall(String phoneId,String location, String phone){
        if(eventDao.countRecordInstall(getToday(),getToday(),phoneId) != 0){
            return null;
        }
        return eventDao.insertRecordInstall(phoneId,location,phone);
    }
    @Transactional
    public Integer insertRecordEvent(String phoneId,String type,String location,String phone){
        return eventDao.insertRecordEvent(phoneId, type, location, phone);
    }
    @Transactional
    public Integer insertRecordMerchant(String phoneId,String phone,String location){
        if(eventDao.countRecordMerchant(getToday(),getToday(),phoneId) != 0){
            return null;
        }
        return eventDao.insertRecordMerchant(phoneId, location, phone);
    }
    public Integer  countRecordInstall(String startTime,String endTime){
        return eventDao.countRecordInstall(startTime, endTime, null);
    }
    public Integer countRecordEvent(String startTime,String endTime,String type){
        return eventDao.countRecordEvent(startTime, endTime, type);
    }
    public Integer countRecordMerchant(String startTime,String endTime){
        return eventDao.countRecordMerchant(startTime, endTime, null);
    }
    public Integer countMerchant(Merchant merchant){
        return eventDao.countMerchant(merchant);
    }
    /**
     * 获取今天的日期
     * @return
     */
    private String getToday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        return today;
    }
    @Transactional
    public Integer deleteUser(String id){
        return eventDao.deleteUser(id);
    }
    @Transactional
    public Integer deleteMerchant(String id){
        return eventDao.deleteMerchant(id);
    }
    @Transactional
    public Integer deleteFeedBck(String id){
        return eventDao.deleteFeedBck(id);
    }
    public Integer findEventYear(){
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        date.getYear();
        return null;
    }
    @Transactional
    public Integer insertAbout(JSONObject data){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",data.get("id"));
        map.put("name",data.get("name"));
        map.put("hotline",data.get("hotline"));
        map.put("img",data.get("img"));
        map.put("remark", data.get("remark"));
        return eventDao.insertAbout(map);
    }
    @Transactional
    public Integer delMImgs(JSONObject data){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("merchantUid",data.get("merchantUid"));
        map.put("md5",data.getJSONArray("md5"));
        return eventDao.delMImgs(map);
    }
}
