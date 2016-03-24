import com.ganli.common.util.Constants;
import com.ganli.common.util.SendMsgUtil;
import com.ganli.dao.EventDao;
import com.ganli.dao.Test;
import com.ganli.dao.UserDao;
import com.ganli.entity.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    @Resource
    EventDao eventDao;
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
    @org.junit.Test
    public void saveEvent(){
        List<Event> list = new ArrayList<Event>();
        for(int i = 0; i < 5; i ++){
            Event event = new Event();
            event.setEventUid(UUID.randomUUID().toString());
            event.setEventName("生孩");
            event.setUserUid(i + "2");
            list.add(event);
        }
        eventDao.saveEvent(list);
    }
    @org.junit.Test
    public void saveGift(){
        List<GiftList> list = new ArrayList<GiftList>();
        for(int i = 0; i < 5; i ++){
            GiftList giftList = new GiftList();
            giftList.setGiftUid(i + "");
            giftList.setUserUid(i + "");
            list.add(giftList);
        }
        eventDao.saveGift(list);
    }
    @org.junit.Test
    public void saveRepay(){
        List<RepayList> list = new ArrayList<RepayList>();
        for(int i = 0; i < 5; i++){
            RepayList repayList = new RepayList();
            repayList.setRepayUid(i + "");
            repayList.setUserUid(i + "");
            list.add(repayList);
        }
        eventDao.saveRepay(list);
    }
    @org.junit.Test
    public void delete(){
        String uid = "0";
        eventDao.deleteEvent(uid);
        eventDao.deleteGift(uid);
        eventDao.deleteRepay(uid);
    }
    @org.junit.Test
    public void find(){
        String uid = "123";
        List<Event> events = eventDao.findEventList(uid);
        List<GiftList> giftLists = eventDao.findGiftList(uid);
        List<RepayList> repayLists = eventDao.findRepayList(uid);
        for(Event e: events){
            System.out.println(e.toString());
        }
        for(GiftList giftList: giftLists){
            System.out.println(giftList.toString());
        }
        for(RepayList repayList: repayLists){
            System.out.println(repayList.toString());
        }
    }
    @org.junit.Test
    public void saveFeedBack(){
        FeedBack feedBack = new FeedBack();
        feedBack.setFeedBckUid("123");
        feedBack.setFeedBackDesc("测试");
        feedBack.setUserUid("123");
        eventDao.saveFeedBack(feedBack);
    }
    @org.junit.Test
    public void saveMerchant(){
        Merchant merchant = new Merchant();
        merchant.setMerchantUid("123");
        merchant.setMerchantDesc("测试2");
        eventDao.saveMerchant(merchant);
    }
    @org.junit.Test
    public void findMerchant(){
        Merchant merchant = new Merchant();
//        merchant.setMerchantLocation("123");
        merchant.setMerchantName("测");
        List<Merchant> list = eventDao.findMerchantList(0,3,merchant);
        System.out.print(list.size());
    }



}
