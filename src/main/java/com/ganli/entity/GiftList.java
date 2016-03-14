package com.ganli.entity;

import java.io.Serializable;

/**
 * Created by hao.cheng on 2016/3/11.
 * @desc 收礼单实体类
 * @date 2016-3-11 10:30:52
 */
public class GiftList implements Serializable {
    private String giftUid;             //收礼单uuid
    private String giftPerson;          //收礼单给礼人
    private String giftMoney;           //收礼单金额
    private String giftRemark;          //收礼单备注
    private String eventUid;            //收单对应的uuid

    public String getGiftUid() {
        return giftUid;
    }

    public void setGiftUid(String giftUid) {
        this.giftUid = giftUid;
    }

    public String getGiftPerson() {
        return giftPerson;
    }

    public void setGiftPerson(String giftPerson) {
        this.giftPerson = giftPerson;
    }

    public String getGiftMoney() {
        return giftMoney;
    }

    public void setGiftMoney(String giftMoney) {
        this.giftMoney = giftMoney;
    }

    public String getGiftRemark() {
        return giftRemark;
    }

    public void setGiftRemark(String giftRemark) {
        this.giftRemark = giftRemark;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }

    @Override
    public String toString() {
        return "GiftList{" +
                "giftUid='" + giftUid + '\'' +
                ", giftPerson='" + giftPerson + '\'' +
                ", giftMoney='" + giftMoney + '\'' +
                ", giftRemark='" + giftRemark + '\'' +
                ", eventUid='" + eventUid + '\'' +
                '}';
    }
}
