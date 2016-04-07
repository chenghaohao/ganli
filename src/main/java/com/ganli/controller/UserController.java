package com.ganli.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganli.common.entity.ResponseMessage;
import com.ganli.common.util.SendMsgUtil;
import com.ganli.entity.User;
import com.ganli.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by hao.cheng on 2016/3/11.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{
    Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    UserService userService;
    /**
     * 发送手机验证码
     * @param phone
     * @param callback
     * @param request
     * @return
     */
    @RequestMapping("sendPhoneCode")
    public Object sendPhoneCode(String phone,String callback,HttpServletRequest request){
        String msg = SendMsgUtil.randomNum();
        request.getSession().setAttribute("phoneCode",msg);
        msg = "礼金理账号注册手机验证码:" + msg;
        System.out.println(msg);
        SendMsgUtil.sendMsg(phone,msg);
        return jsonpHandler(rm,callback);
    }

    /**
     * 验证验证码是否正确
     * @param callback
     * @param request
     * @param code
     * @return
     */
    @RequestMapping("checkPhoneCode")
    public Object checkPhoneCode(String callback,HttpServletRequest request,String code){
        rm = new ResponseMessage();
        String phoneCode = (String)request.getSession().getAttribute("phoneCode");
        if(!phoneCode.equals(code)){
            rm.setMsg("验证码错误");
            rm.setCode("000001");
        }
        return jsonpHandler(rm,callback);
    }

    /**
     * 保存用户信息
     * @param callback
     * @param data
     * @return
     */
    @RequestMapping("saveUser")
    public Object saveUser(String callback,String data){
        rm = new ResponseMessage();
        try{
            User user = JSONObject.parseObject(data,User.class);
            if(user.getUserPhone() != null){
                User u = userService.findUserByPhone(user.getUserPhone());
                if(u != null){
                    rm.setCode("000002");
                    rm.setMsg("用户已存在");
                    rm.setData(u);
                    return jsonpHandler(rm,callback);
                }
            }
            if(user.getUserUid() == null){
                user.setUserUid(UUID.randomUUID().toString());
            }
            userService.saveUser(user);
            rm.setData(user);
            return jsonpHandler(rm,callback);
        }catch (Exception e){
            log.info(e.getMessage());
            rm.setMsg("系统错误");
            rm.setCode("100001");
            return  jsonpHandler(rm,callback);
        }
    }

    /**
     * 检测用户是否存在
     * @param callback
     * @param data
     * @return
     */
    @RequestMapping("checkUser")
    public Object checkUser(String callback,String data){
        rm = new ResponseMessage();
        JSONObject obj = JSONObject.parseObject(data);
        String phone = obj.getString("phone");
        User user = null;
        if(!StringUtils.isBlank(phone)){
            user = userService.findUserByPhone(phone);
            if(user != null){
                rm.setData(user);
                return jsonpHandler(rm,callback);
            }else{
                rm.setMsg("用户不存在");
                rm.setCode("000001");
                return jsonpHandler(rm,callback);
            }
        }
        String pwd = obj.getString("pwd");
        if(!StringUtils.isBlank(pwd)){
            user = userService.findUserByPwd(pwd);
            if(user != null){
                rm.setData(user);
                return jsonpHandler(rm,callback);
            }else{
                rm.setMsg("用户不存在");
                rm.setCode("000001");
                return jsonpHandler(rm,callback);
            }
        }
        return null;
    }
}
