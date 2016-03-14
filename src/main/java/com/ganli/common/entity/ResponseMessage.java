package com.ganli.common.entity;

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
    private List<Object> datas = null;
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

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
