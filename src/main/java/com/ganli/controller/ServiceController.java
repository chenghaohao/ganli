package com.ganli.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ganli.common.entity.ResponseMessage;
import com.ganli.common.util.CommonUtils;
import com.ganli.common.util.Constants;
import com.ganli.common.util.HttpClientUtil;
import com.ganli.dao.EventDao;
import com.ganli.dao.UserDao;
import com.ganli.entity.*;
import com.ganli.service.EventService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Resource
    EventDao eventDao;
    @Resource
    UserDao userDao;

    /**
     * 同步上传数据
     * @param data
     * @param callback
     * @return
     */
    @RequestMapping("syncUpload")
    public Object syncUpload(@RequestParam()String data,String callback){
        rm = new ResponseMessage();
        try{
//            data = new String(data.getBytes("ISO-8859-1"),"utf-8");
            JSONObject obj = JSONObject.parseObject(data);
            List events =  Arrays.asList(obj.getJSONArray("event").toArray());     //获取事件列表
            List gifts = Arrays.asList(obj.getJSONArray("gift").toArray());        //获取收礼单列表
            List repays = Arrays.asList(obj.getJSONArray("repay").toArray());      //获取还礼单列表
            List<Integer> temp = new ArrayList<Integer>();
            Event e = null;
            GiftList g = null;
            RepayList r = null;

            List<Event> addEvents = new ArrayList<Event>();
            List<GiftList> addGifts = new ArrayList<GiftList>();
            List<RepayList> addRepays = new ArrayList<RepayList>();

            List<Event> updateEvents = new ArrayList<Event>();
            List<GiftList> updateGifts = new ArrayList<GiftList>();
            List<RepayList> updateRepays = new ArrayList<RepayList>();

            List<Event> deleteEvents = new ArrayList<Event>();
            List<GiftList> deleteGifts = new ArrayList<GiftList>();
            List<RepayList> deleteRepays = new ArrayList<RepayList>();

            for(Object o : events){
                e = JSONObject.parseObject(o.toString(),Event.class);
                if(e.getStatus() == 1){
                    addEvents.add(e);
                }else if (e.getStatus() == 2){
                    updateEvents.add(e);
                }else if(e.getStatus() == 3){
                    deleteEvents.add(e);
                }
            }
            for(Object o : gifts){
                g = JSONObject.parseObject(o.toString(),GiftList.class);;
                if(g.getStatus() == 1){
                    addGifts.add(g);
                }else if(g.getStatus() == 2){
                    updateGifts.add(g);
                }else if(g.getStatus() == 3){
                    deleteGifts.add(g);
                }
            }
            for(Object o : repays){
                r = JSONObject.parseObject(o.toString(),RepayList.class);
                if(r.getStatus() == 1){
                    addRepays.add(r);
                }else if(r.getStatus() == 2){
                    updateRepays.add(r);
                }else if(r.getStatus() == 3){
                    deleteRepays.add(r);
                }
            }
            eventService.saveDataOnly(addEvents,addGifts,addRepays);
            eventService.updateDataOnly(updateEvents,updateGifts,updateRepays);
            eventService.deleteDataOnly(deleteEvents,deleteGifts,deleteRepays);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("系统异常");
            log.info(e.getMessage());
            log.error(e.getMessage());
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
        User user= JSONObject.parseObject(data, User.class);
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

            List<Map<String,Object>> imgsList = new ArrayList<Map<String, Object>>();
            String[] imgs = merchant.getImgs();
            if (imgs != null){
                for(String m: imgs){
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("merchant_uid",merchant.getMerchantUid());
                    map.put("md5",m);
                    imgsList.add(map);
                }
            }
            eventService.saveMerchant(merchant, imgsList);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("操作异常");
            log.info(e.getMessage());
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
        int start = Integer.valueOf(obj.getString("start")) - 1;
        int limit = Integer.valueOf(obj.getString("limit"));
        Merchant merchant = JSONObject.parseObject(obj.getString("merchant"), Merchant.class);
        List merchants = eventService.findMerchantList(start, limit, merchant);
        if(merchants.size() == 0){
            return jsonpHandler(rm,callback);
        }
        List<Merchant> merchantList = new ArrayList<Merchant>();
        List<String> merchantIds = new ArrayList<String>();
        Merchant m = null;
        for (Object o: merchants){
            m = (Merchant)o;
            merchantIds.add(m.getMerchantUid());
            merchantList.add(m);
        }

        List<Map<String,Object>> imgs = eventDao.findImgs(merchantIds);

        List<Object> datas = new ArrayList<Object>();
        for(Merchant merchant1:merchantList){
            List<Map<String,Object>> imgs2 = new ArrayList<Map<String, Object>>();
            String[] imgs3 = null;
            for(Map<String, Object> map:imgs){
                if(merchant1.getMerchantUid().equals(map.get("merchant_uid"))){
                    imgs2.add(map);
                }
            }
            imgs3 = new String[imgs2.size()];
            for(int i = 0;i < imgs2.size();i++){
                imgs3[i] = imgs2.get(i).get("md5") + "";
            }
            merchant1.setImgs(imgs3);
            datas.add(merchant1);
        }
        Integer count = eventService.countMerchant(merchant);
        rm.setTotal(count);
        rm.setDatas(datas);
        return jsonpHandler(rm,callback);
    }

    /**
     *
     * @param data
     * @param callback
     * @return
     */
    @RequestMapping("findFeedbackList")
    public Object findFeedbackList(String data,String callback){
        rm = new ResponseMessage();
        JSONObject obj = JSONObject.parseObject(data);
        int start = Integer.valueOf(obj.getString("start")) - 1;
        int limit = Integer.valueOf(obj.getString("limit"));
        List feedbacks = eventDao.findFeedBckList(start, limit, null);
        List<Map<String,String>> mapList = new ArrayList<Map<String, String>>();
        for(Object o: feedbacks){
            Map<String,String> map = (Map)o;
            map.put("c_time", String.valueOf(map.get("c_time")));
            mapList.add(map);
        }
        Integer count = eventDao.countFeedback();
        rm.setTotal(count);
        rm.setData(mapList);
        return jsonpHandler(rm,callback);
    }
    @RequestMapping("findUserList")
    public Object findUserList(String data,String callback){
        rm = new ResponseMessage();
        JSONObject obj = JSONObject.parseObject(data);
        int start = Integer.valueOf(obj.getString("start")) - 1;
        int limit = Integer.valueOf(obj.getString("limit"));
        List users = eventDao.findUserList(start, limit, null);
        List<Map<String,String>> mapList = new ArrayList<Map<String, String>>();
        for(Object o: users){
            Map<String,String> map = (Map)o;
            map.put("create_time", String.valueOf(map.get("create_time")));
            mapList.add(map);
        }
        Integer count = eventDao.coutUser();
        rm.setTotal(count);
        rm.setData(mapList);
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
                                @RequestParam(defaultValue = "", required = false) String type, String phoneId,String callback,
                                @RequestParam(defaultValue = "", required = false) String phone,
                                @RequestParam(defaultValue = "",required = false) String location) {
        rm = new ResponseMessage();
        try {
            switch (tp) {
                case 1:
                    eventService.insertRecordInstall(phoneId, location, phone);
                    break;
                case 2:
                    eventService.insertRecordEvent(phoneId, type, location, phone);
                    break;
                case 3:
                    eventService.insertRecordMerchant(phoneId,phone,location);
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
    @RequestMapping("delete/{tp}")
    public Object deleteData(@PathVariable("tp")Integer tp,String id,String callback){ //1用户，2商家，3反馈信息
        rm = new ResponseMessage();
        try{
            if(id == null || id == ""){
                rm.setCode("000001");
                rm.setMsg("id不能为空");
                return jsonpHandler(rm,callback);
            }
            switch (tp) {
                case 1:
                    eventService.deleteUser(id);
                    break;
                case 2:
                    eventService.deleteMerchant(id);
                    break;
                case 3:
                    eventService.deleteFeedBck(id);
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
        }catch (Exception e) {
            rm.setCode("000001");
            rm.setMsg("系统异常");
            return jsonpHandler(rm,callback);
        }
    }

    /**
     * 获取装机总量
     * @param tp  0:总量  1：新增
     * @return
     */
    @RequestMapping("countInstall/{tp}")
    public Object countInstall(@PathVariable("tp")Integer tp){
        rm = new ResponseMessage();
        Integer all = eventDao.countInstall(null); //去重获取装机总量
        switch (tp){
            case 0: rm.setData(all);break;
            case 1:
                Date d = new Date();
                SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(d);
                Integer yesterday = eventDao.countInstall(date);
                Integer newInstall = all - yesterday;
                rm.setData(newInstall);
                break;
        }
        return rm;
    }
    @RequestMapping("countYear/{tp}")
    public Object countYear(@PathVariable("tp")Integer tp,String data,String callback){
        rm = new ResponseMessage();
        try{
            List<Integer> list = null;
            Map<String,Object> map = null;
            List<Map<String,Object>> datas = null;
            String[] dates = data.split(",");
            Calendar c = Calendar.getInstance();
            switch (tp){
                case 1:
                    map = new HashMap<String, Object>();
                    datas = new ArrayList<Map<String, Object>>();
                    for(String s:dates){
                        list = new ArrayList<Integer>();
                        for(int i = 1;i < 13;i++){
                            if(i < 10){
                                list.add(eventDao.findInstallYear(s + "-0" + i));
                            }else{
                                list.add(eventDao.findInstallYear(s + i + ""));
                            }
                        }
                        map.put(s, list);
                    }
                    datas.add(map);
                    break;
                case 2:
                    map = new HashMap<String, Object>();
                    datas = new ArrayList<Map<String, Object>>();
                    for(String s:dates){
                        list = new ArrayList<Integer>();
                        for(int i = 1;i < 13;i++){
                            if(i < 10){
                                list.add(eventDao.findEventYear(s + "-0" + i));
                            }else{
                                list.add(eventDao.findEventYear(s + i + ""));
                            }
                        }
                        map.put(s, list);
                    }
                    datas.add(map);
                    break;
                case 3:
                    map = new HashMap<String, Object>();
                    datas = new ArrayList<Map<String, Object>>();
                    for(String s:dates){
                        list = new ArrayList<Integer>();
                        for(int i = 1;i < 13;i++){
                            if(i < 10){
                                list.add(eventDao.findMerchantYear(s + "-0" + i));
                            }else{
                                list.add(eventDao.findMerchantYear(s + i + ""));
                            }
                        }
                        map.put(s, list);
                    }
                    datas.add(map);
                    break;
            }
            rm.setData(datas);
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
    /**
     * 上传图片
     *
     * @return
     * @author chenghao
     */
    @RequestMapping("upload")
    public Object uploadImage(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletResponse response,String callback) {
        rm = new ResponseMessage();
        try {
            File lf = new File(System.currentTimeMillis() + ".jpg");
            file.transferTo(lf);
            String httpAddr = Constants.IMG_URL + "upload";
            String rsp = HttpClientUtil.upload(httpAddr, "file", lf).toString();
            Pattern pattern = Pattern.compile("<h1>MD5: ([^<]*)</h1>");
            Matcher matcher = pattern.matcher(rsp);
            while (matcher.find()) {
                String code = matcher.group(1);
                if (StringUtils.isNotEmpty(code)) {
                    rm.setData(code);
                    return jsonpHandler(rm,callback);
                }
            }
            lf.delete();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return jsonpHandler(rm, callback);
    }
    @RequestMapping("aboutSave")
    public Object about(String data,String callback){
        rm = new ResponseMessage();
        try{
            JSONObject obj = JSONObject.parseObject(data);
            if(obj == null){
                rm.setCode("000001");
                rm.setMsg("参数不能为空");
                return jsonpHandler(rm,callback);
            }
            if(StringUtils.isEmpty(obj.getString("id"))){
                List<Map<String,Object>> maps = eventDao.findAbout();
                if(maps.size() > 0){
                    rm.setCode("000001");
                    rm.setMsg("数据库已存在，只能修改");
                    return jsonpHandler(rm,callback);
                }
            }
            eventService.insertAbout(obj);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("系统异常");
            return jsonpHandler(rm,callback);
        }
        return jsonpHandler(rm, callback);
    }
    @RequestMapping("aboutFind")
    public Object findAbout(){
        rm = new ResponseMessage();
        List<Map<String,Object>> maps = eventDao.findAbout();
        rm.setData(maps);
        return jsonpHandler(rm,null);
    }
    @RequestMapping("delMImgs")
    public Object delMImgs(String data){
        rm = new ResponseMessage();
        try{
            JSONObject obj = JSONObject.parseObject(data);
            eventService.delMImgs(obj);
        }catch (Exception e){
            rm.setCode("000001");
            rm.setMsg("系统异常");
            return jsonpHandler(rm,null);
        }
        return jsonpHandler(rm,null);
    }
    @RequestMapping("findUserData")
    public Object findUserData(String data){
        rm = new ResponseMessage();
        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = userDao.findUserByPhone(jsonObject.getString("phone"));
        if(user == null){
            rm.setMsg("用户不存在");
            rm.setCode("000001");
        }else{
            if("gift".equals(jsonObject.getString("tp"))){
                List<GiftList> giftLists = eventDao.findGiftList(user.getUserUid(),null, null);
                Integer count = eventDao.countGiftList(user.getUserUid());
                rm.setTotal(count);
                rm.setData(giftLists);
            }else if("repay".equals(jsonObject.getString("tp"))){
                List<RepayList> repayLists = eventDao.findRepayList(user.getUserUid());
                Integer count = eventDao.countRepayList(user.getUserUid());
                rm.setTotal(count);
                rm.setData(repayLists);
            }
        }
        return rm;
    }
}
