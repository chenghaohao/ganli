package com.ganli.service;

import com.ganli.dao.UserDao;
import com.ganli.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hao.cheng on 2016/3/13.
 */
@Service
public class UserService {
    @Resource
    UserDao userDao;

    /**
     * 保存用户
     * @param user
     * @return
     */
    @Transactional
    public Integer saveUser(User user){
        return userDao.saveUser(user);
    }
    public User findUserByPhone(String phone){     //通过手机号查询用户
        return userDao.findUserByPhone(phone);
    }
    public User findUserByPwd(String pwd,String phone){      //通过密码查询用户
        return userDao.findUserByPwd(pwd,phone);
    }
}
