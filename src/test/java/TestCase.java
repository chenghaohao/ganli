import com.ganli.dao.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

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
    @org.junit.Test
    public void test1(){
        test.test();
    }
    @org.junit.Test
    public void testSpringJdbc() throws SQLException {
        DataSource ds = ac.getBean("dataSource",DataSource.class);
        System.out.println(ds.getConnection().getMetaData().getDatabaseProductName());
    }



}
