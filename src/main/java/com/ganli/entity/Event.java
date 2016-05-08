package com.ganli.entity;

import java.io.Serializable;

/**
 * Created by hao.cheng on 2016/3/11.
 * @desc 事件实体类
 * @date 2016-3-11 10:03:22
 */
public class Event implements Serializable{
    private String eventUid;                //事件uuid
    private String eventName;               //事件名称
    private Integer eventType;              //事件类型
    private String eventLocation;           //事件地址
    private String eventTime;               //事件发生时间
    private String userUid;                 //用户uuid
    private Integer status;                 //事件状态

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventUid='" + eventUid + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventType=" + eventType +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", userUid='" + userUid + '\'' +
                '}';
    }
}
