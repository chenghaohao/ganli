package com.ganli.entity;

import java.io.Serializable;

/**
 * Created by hao.cheng on 2016/3/11.
 * @desc 用户实体类
 * @date 2016-3-11 10:11:31
 */
public class User implements Serializable{
    private String userUid;                 //用户uuid
    private String userName;                //用户名称--默认为电话号码
    private Integer userSex;                    //用户性别
    private String userPhone;               //用户电话
    private String userLocation;            //用户住址
    private String userPwd;                 //用户登录密码
    private String img;     //头像

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid='" + userUid + '\'' +
                ", userName='" + userName + '\'' +
                ", userSex=" + userSex +
                ", userPhone='" + userPhone + '\'' +
                ", userLocation='" + userLocation + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
