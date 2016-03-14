package com.ganli.common.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by hao.cheng on 2016/2/29.
 * 短信验证码工具类
 */
public class SendMsgUtil {
    static Logger logger = LoggerFactory.getLogger(SendMsgUtil.class);

    /**
     * 调用短信接口发送短信
     * @param phone
     * @param msg
     */
    public static void sendMsg(String phone,String msg){
        try{
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(Constants.MSG_URL);
            post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
            NameValuePair[] data ={
                    new NameValuePair("Uid", Constants.MSG_USER),
                    new NameValuePair("Key", Constants.MSG_KEY),
                    new NameValuePair("smsMob",phone),
                    new NameValuePair("smsText",msg)};
            post.setRequestBody(data);
            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:"+statusCode);
            for(Header h : headers) {
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
            System.out.println(result); //打印返回消息状态
            post.releaseConnection();

        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    /**
     * 生成随机的6位数
     * @return
     */
    public static String randomNum(){
        Random random = new Random();
        int a = random.nextInt(1000000);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6;i ++){
            int b = random.nextInt(10);
            System.out.println(b);
            sb.append(b);
        }
        return sb.toString();
    }
}
