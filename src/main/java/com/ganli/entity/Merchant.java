package com.ganli.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by hao.cheng on 2016/3/18.
 */
public class Merchant implements Serializable{
    private String merchantUid;            //商家id
    private String merchantName;            //商家名称
    private String merchantImg;            //商家图片
    private String merchantDesc;           //商家描述
    private String merchantPhone;           //商家电话
    private String merchantLocation;        //商家地址
    private String merchantScore;           //商家评分
    private String merchantType;            //类型
    private String[] imgs;      //图片数组
    private String merchantPrice;   //商家价格

    public String getMerchantUid() {
        return merchantUid;
    }

    public void setMerchantUid(String merchantUid) {
        this.merchantUid = merchantUid;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantImg() {
        return merchantImg;
    }

    public void setMerchantImg(String merchantImg) {
        this.merchantImg = merchantImg;
    }

    public String getMerchantDesc() {
        return merchantDesc;
    }

    public void setMerchantDesc(String merchantDesc) {
        this.merchantDesc = merchantDesc;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public String getMerchantScore() {
        return merchantScore;
    }

    public void setMerchantScore(String merchantScore) {
        this.merchantScore = merchantScore;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public String getMerchantPrice() {
        return merchantPrice;
    }

    public void setMerchantPrice(String merchantPrice) {
        this.merchantPrice = merchantPrice;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantUid='" + merchantUid + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantImg='" + merchantImg + '\'' +
                ", merchantDesc='" + merchantDesc + '\'' +
                ", merchantPhone='" + merchantPhone + '\'' +
                ", merchantLocation='" + merchantLocation + '\'' +
                ", merchantScore='" + merchantScore + '\'' +
                ", merchantType='" + merchantType + '\'' +
                ", imgs=" + Arrays.toString(imgs) +
                ", merchantPrice='" + merchantPrice + '\'' +
                '}';
    }
}
