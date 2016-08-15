package com.ganli.entity;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by hao.cheng on 2016/3/11.
 * @desc 还礼单实体类
 * @date 2016-3-11 17:30:51
 */
public class RepayList implements Serializable {
    private String repayUid;                //还礼uuid
    private String repayPerson;             //还礼人
    private String repayMoney;              //还礼金额
    private String repayRemark;             //还礼备注
    private String giftUid;                 //还礼单对应的收礼单id
    private String eventUid;                //还礼单对应的事件id
    private String userUid;                 //用户id
    private Integer status;             //还礼单状态
    private String repayTime;         //还礼时间

    public String getRepayUid() {
        return repayUid;
    }

    public void setRepayUid(String repayUid) {
        this.repayUid = repayUid;
    }

    public String getRepayPerson() {
        return repayPerson;
    }

    public void setRepayPerson(String repayPerson) {
        this.repayPerson = repayPerson;
    }

    public String getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(String repayMoney) {
        this.repayMoney = repayMoney;
    }

    public String getRepayRemark() {
        return repayRemark;
    }

    public void setRepayRemark(String repayRemark) {
        this.repayRemark = repayRemark;
    }

    public String getGiftUid() {
        return giftUid;
    }

    public void setGiftUid(String giftUid) {
        this.giftUid = giftUid;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
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

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    @Override
    public String toString() {
        return "RepayList{" +
                "repayUid='" + repayUid + '\'' +
                ", repayPerson='" + repayPerson + '\'' +
                ", repayMoney='" + repayMoney + '\'' +
                ", repayRemark='" + repayRemark + '\'' +
                ", giftUid='" + giftUid + '\'' +
                ", eventUid='" + eventUid + '\'' +
                ", userUid='" + userUid + '\'' +
                ", status=" + status +
                ", repayTime='" + repayTime + '\'' +
                '}';
    }
}
