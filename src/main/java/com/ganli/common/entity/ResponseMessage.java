package com.ganli.common.entity;

import com.ganli.entity.Event;
import com.ganli.entity.GiftList;
import com.ganli.entity.RepayList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hao.cheng on 2016/3/12.
 * @desc 统一返回信息实体类
 * @date 2016-3-12 10:55:24
 */
public class ResponseMessage implements Serializable {
    private String msg = "操作成功";        //返回信息
    private String code = "000000";        //返回编码
    private Integer total = null;           //返回总数
    private List<Object> datas = null;
    private List<Event> event = null;
    private List<GiftList> gift = null;
    private List<RepayList> repay = null;
    private Object data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public List<GiftList> getGift() {
        return gift;
    }

    public void setGift(List<GiftList> gift) {
        this.gift = gift;
    }

    public List<RepayList> getRepay() {
        return repay;
    }

    public void setRepay(List<RepayList> repay) {
        this.repay = repay;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
