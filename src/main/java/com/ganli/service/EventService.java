package com.ganli.service;

import com.ganli.dao.EventDao;
import com.ganli.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public List<Event> findEvents(String uid){
        return eventDao.findEventList(uid);
    }
    public List<GiftList> findGifts(String uid){
        return eventDao.findGiftList(uid);
    }
    public List<RepayList> findRepays(String uid){
        return eventDao.findRepayList(uid);
    }
    @Transactional
    public Integer saveFeedBack(FeedBack feedBack){ //保存反馈信息
        return eventDao.saveFeedBack(feedBack);
    }
    @Transactional
    public Integer saveMerchant(Merchant merchant){
        return eventDao.saveMerchant(merchant);
    }
    public List<Merchant> findMerchantList(int start,int limit,Merchant merchant){
        return eventDao.findMerchantList(start,limit,merchant);
    }
}
