package com.ganli.dao;

import com.ganli.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hao.cheng on 2016/3/13.
 */
@Repository
public class UserDao extends BaseDao{
    public static final String NAMESPACE = "com.ganli.dao.UserDao";

    /**
     * 不存在则新增，存在则更新
     * @param user
     * @return
     */
    public Integer saveUser(User user){
        return this.getSqlSession().insert(NAMESPACE + ".saveUser", user);
    }

    /**
     * 通过手机号查询用户
     * @param phone
     * @return
     */
    public User findUserByPhone(String phone){
        return this.getSqlSession().selectOne(NAMESPACE + ".findUserByPhone", phone);
    }

    /**
     * 通过密码查询用户
     * @param pwd
     * @return
     */
    public User findUserByPwd(String pwd,String phone){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pwd",pwd);
        map.put("phone",phone);
        return this.getSqlSession().selectOne(NAMESPACE + ".findUserByPwd",map);
    }
}
