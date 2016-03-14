import com.ganli.common.util.Constants;
import com.ganli.common.util.SendMsgUtil;
import com.ganli.dao.Test;
import com.ganli.dao.UserDao;
import com.ganli.entity.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by hao.cheng on 2016/2/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/servlet-context.xml")
public class TestCase {
    @Autowired
    Test test;
    @Autowired
    ApplicationContext ac;
    @Resource
    UserDao userDao;
    @org.junit.Test
    public void test1(){
        test.test();
    }
    @org.junit.Test
    public void testSpringJdbc() throws SQLException {
        DataSource ds = ac.getBean("dataSource",DataSource.class);
        System.out.println(ds.getConnection().getMetaData().getDatabaseProductName());
    }
    @org.junit.Test
    public void testSendMsg(){
        SendMsgUtil.sendMsg("18302841703", "验证码:518");
    }
    @org.junit.Test
    public void testConstants(){
        Random random = new Random();
        int a = random.nextInt(1000000);
//        System.out.println(a);
//        System.out.print((int)((Math.random()*9+1)));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6;i ++){
            int b = random.nextInt(10);
            System.out.println(b);
            sb.append(b);
        }
        System.out.println(sb);
    }

    @org.junit.Test
    public void testSaveUser(){
        String uid = UUID.randomUUID().toString();
        User user = new User();
        user.setUserUid("583ef77a-f387-4401-8b13-6e01c72a8571");
        user.setUserPhone("123");
        user.setUserPwd("234");
        userDao.saveUser(user);
    }
    @org.junit.Test
    public void findUserByPhone(){
        String phone = "123";
        User user = userDao.findUserByPhone(phone);
        System.out.print(user.toString());
    }
    @org.junit.Test
    public void findUserByPwd(){
        String pwd = "234";
        User user = userDao.findUserByPwd(pwd);
        System.out.print(user.toString());
    }



}
