package com.ganli.dao;

import com.ganli.entity.*;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hao.cheng on 2016/3/15.
 */
@Repository
public class EventDao extends BaseDao{
    public final static String NAMESPACE = "com.ganli.dao.EventDao";
    public Integer updateEvent(int status,String uid){  //删除事件
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",status);
        map.put("uid",uid);
        return this.getSqlSession().update(NAMESPACE + ".updateEvent", map);
    }
    public Integer deleteEvent(String uid){         //通过用户ID删除事件
        return this.getSqlSession().delete(NAMESPACE + ".deleteEvent", uid);
    }
    public Integer deleteGift(String uid){     //通过用户ID删除收礼单
        return this.getSqlSession().delete(NAMESPACE + ".deleteGift",uid);
    }
    public Integer deleteRepay(String uid){     //通过用户ID删除还礼单
        return this.getSqlSession().delete(NAMESPACE + ".deleteRepay", uid);
    }
    public Integer saveEvent(List<Event> list){     //批量保存事件
        return this.getSqlSession().insert(NAMESPACE + ".saveEvent", list);
    }
    public Integer saveGift(List<GiftList> list){   //批量保存收礼单
        return this.getSqlSession().insert(NAMESPACE + ".saveGift",list);
    }
    public Integer saveRepay(List<RepayList> list){    //批量保存还礼单
        return this.getSqlSession().insert(NAMESPACE + ".saveRepay", list);
    }
    public List<Event> findEventList(String uid) {     //通过用户id查询事件列表
        return this.getSqlSession().selectList(NAMESPACE + ".findEventList", uid);
    }
    public List<GiftList> findGiftList(String uid, Integer start, Integer limit){     //通过用户id查询收礼单列表
        if(start == null || limit == null)
            return this.getSqlSession().selectList(NAMESPACE + ".findGiftList", uid);
        else
            return this.getSqlSession().selectList(NAMESPACE + ".findGiftList", uid, new RowBounds(start, limit));
    }
    public Integer countGiftList(String uid){
        return this.getSqlSession().selectOne(NAMESPACE + ".countGiftList", uid);
    }
    public List<RepayList> findRepayList(String uid){   //通过用户id查询还礼单列表
        return this.getSqlSession().selectList(NAMESPACE + ".findRepayList", uid);
    }
    public Integer countRepayList(String uid){
        return this.getSqlSession().selectOne(NAMESPACE + ".countRepayList", uid);
    }
    public Integer saveFeedBack(FeedBack feedBack){     //保存反馈信息
        return this.getSqlSession().insert(NAMESPACE + ".saveFeedBack", feedBack);
    }
    public Integer saveMerchant(Merchant merchant){
        return this.getSqlSession().insert(NAMESPACE + ".saveMerchant", merchant);
    }
    public List<Merchant> findMerchantList(int start,int limit,Merchant merchant){
        return this.getSqlSession().selectList(NAMESPACE + ".findMerchantList", merchant, new RowBounds(start, limit));
    }
    public List<Map<String,Object>> findFeedBckList(int start,int limit,FeedBack feedBack){
        return this.getSqlSession().selectList(NAMESPACE + ".findFeedBckList", feedBack, new RowBounds(start, limit));
    }
    public List<Map<String,Object>> findUserList(int start,int limit,User user){
        return this.getSqlSession().selectList(NAMESPACE + ".findUserList",user, new RowBounds(start, limit));
    }
    public Integer coutUser(){
        return  this.getSqlSession().selectOne(NAMESPACE + ".coutUser");
    }
    public Integer countMerchant(Merchant merchant){
        return this.getSqlSession().selectOne(NAMESPACE + ".countMerchant", merchant);
    }
    public Integer countFeedback(){
        return this.getSqlSession().selectOne(NAMESPACE + ".countFeedback");
    }
    public Integer insertRecordInstall(String phoneId,String location, String phone){     //插入装机统计
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneId", phoneId);
        map.put("location", location);
        map.put("phone", phone);
        return this.getSqlSession().insert(NAMESPACE + ".insertRecordInstall", map);
    }
    public Integer insertRecordEvent(String phoneId,String type,String location, String phone){  //插入新建事件统计
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("phoneId",phoneId);
        map.put("type",type);
        map.put("location", location);
        map.put("phone", phone);
        return this.getSqlSession().insert(NAMESPACE+".insertRecordEvent",map);
    }
    public Integer insertRecordMerchant(String phoneId,String location, String phone){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneId", phoneId);
        map.put("location", location);
        map.put("phone", phone);
        return this.getSqlSession().insert(NAMESPACE+".insertRecordMerchant",map);
    }
    public Integer countRecordInstall(String startTime,String endTime,String phoneId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("phoneId",phoneId);
        return this.getSqlSession().selectOne(NAMESPACE + ".countRecordInstall", map);
    }
    public Integer countRecordEvent(String startTime,String endTime,String type){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("type",type);
        return this.getSqlSession().selectOne(NAMESPACE + ".countRecordEvent", map);
    }
    public Integer countRecordMerchant(String startTime,String endTime,String phoneId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("phoneId",phoneId);
        return this.getSqlSession().selectOne(NAMESPACE + ".countRecordMerchant", map);
    }
    public Integer batchUpdateEvent(List<Event> list){  //批量更新
        return this.getSqlSession().insert(NAMESPACE + ".batchUpdateEvent", list);
    }
    public Integer batchUpdateGift(List<GiftList> list){
        return this.getSqlSession().insert(NAMESPACE + ".batchUpdateGift",list);
    }
    public Integer batchUpdateRepay(List<RepayList> list){
        return this.getSqlSession().insert(NAMESPACE + ".batchUpdateRepay",list);
    }
    public Integer batchDeleteEvent(List<Event> list){
        return this.getSqlSession().delete(NAMESPACE + ".batchDeleteEvent", list);
    }
    public Integer batchDeleteGift(List<GiftList> list){
        return this.getSqlSession().delete(NAMESPACE + ".batchDeleteGift",list);
    }
    public Integer batchDeleteRepay(List<RepayList> list){
        return this.getSqlSession().delete(NAMESPACE + ".batchDeleteRepay",list);
    }
    public Integer deleteUser(String id){
        return this.getSqlSession().delete(NAMESPACE + ".deleteUser",id);
    }
    public Integer deleteMerchant(String id){
        return this.getSqlSession().delete(NAMESPACE + ".deleteMerchant",id);
    }
    public Integer deleteFeedBck(String id){
        return this.getSqlSession().delete(NAMESPACE + ".deleteFeedBck",id);
    }
    public List<Map<String,Object>> findImgs(List<String> list){
        return this.getSqlSession().selectList(NAMESPACE + ".findImgs", list);
    }
    public Integer insertImgs(List<Map<String,Object>> list){
        return this.getSqlSession().insert(NAMESPACE + ".insertImgs", list);
    }
    public Integer findInstallYear(String date){
        return this.getSqlSession().selectOne(NAMESPACE + ".findInstallYear", date);
    }
    public Integer findEventYear(String date){
        return this.getSqlSession().selectOne(NAMESPACE+".findEventYear",date);
    }
    public Integer findMerchantYear(String date){
        return this.getSqlSession().selectOne(NAMESPACE+".findMerchantYear",date);
    }
    public Integer insertAbout(Map<String,Object> map){
        return this.getSqlSession().insert(NAMESPACE + ".insertAbout", map);
    }
    public List<Map<String,Object>> findAbout(){
        return this.getSqlSession().selectList(NAMESPACE + ".findAbout");
    }
    public Integer deleteImgs(String id){
        return this.getSqlSession().delete(NAMESPACE + ".deleteImgs", id);
    }
    public Integer delMImgs(Map<String,Object> map){
        return this.getSqlSession().delete(NAMESPACE+".delMImgs",map);
    }
    public Integer countInstall(String date){
        return this.getSqlSession().selectOne(NAMESPACE + ".countInstall", date);
    }
    public List<Map<String, Object>> findGifts(String uId){
        return this.getSqlSession().selectList(NAMESPACE + ".findGifts", uId);
    }
}
