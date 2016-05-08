package com.ganli.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by hao.cheng on 2016/3/11.
 * @desc 反馈实体类
 * @date 2016-3-11 10:16:40
 */
public class FeedBack implements Serializable {
    private String feedBckUid;              //反馈uuid
    private String feedBackDesc;            //反馈描述
    private String userUid;                 //用户uuid
    private Date cTime;

    public String getFeedBckUid() {
        return feedBckUid;
    }

    public void setFeedBckUid(String feedBckUid) {
        this.feedBckUid = feedBckUid;
    }

    public String getFeedBackDesc() {
        return feedBackDesc;
    }

    public void setFeedBackDesc(String feedBackDesc) {
        this.feedBackDesc = feedBackDesc;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "feedBckUid='" + feedBckUid + '\'' +
                ", feedBackDesc='" + feedBackDesc + '\'' +
                ", userUid='" + userUid + '\'' +
                '}';
    }
}
