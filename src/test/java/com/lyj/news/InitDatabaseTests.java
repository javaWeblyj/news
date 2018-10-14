package com.lyj.news;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/12 11:52
 * @Description:
 */

import com.lyj.news.dao.LoginTicketDao;
import com.lyj.news.dao.NewsDao;
import com.lyj.news.dao.UserDao;
import com.lyj.news.model.LoginTicket;
import com.lyj.news.model.News;
import com.lyj.news.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;
// import org.springframework.test.context.jdbc.Sql;

/**
 * @program: news
 * @description: 数据库相关操作测试
 * @author: babyL.FinlayL
 * @createDate: 2018-10-12 11:52
 **/
// 1.@Sql("/init-schema.sql") 这里可以执行数据表建立操作sql语句，跑之前执行sql 语句
    // 2. spring boot 1.4 以后就不支持 这个@SpringApplicationConfiguration 改用 @SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(classes = NewsApplication.class)
@SpringApplicationConfiguration(classes = NewsApplication.class)
public class InitDatabaseTests {
    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;

    @Autowired
    LoginTicketDao loginTicketDao;


    @Test
    public void test(){

    }

    @Test
    public void initData() {
        for (int i = 0; i <= 20; i++) {
            Random random = new Random();
            Date date = new Date();

            //测试 UserDao 操作
//            User user = new User();
/*            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("USER%d",i));
            user.setPassword(" ");
            user.setSalt(" ");
            userDao.addUser(user);*/
//            user.setId(i+1);
//            user.setPassword("password");
//            userDao.updatePassword(user);

     //测试 UserDao 操作
/*            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(1+i);
            news.setLink(String.format("http://www.newcoder.com/%d.html", i));
            news.setTitle(String.format("TITLE%d",i));

            newsDao.addNews(news);*/

            LoginTicket ticket = new LoginTicket();
            ticket.setExpired(date);
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setTicket(String.format("TICKET%d",i+1));

            // loginTicketDao.addTicket(ticket);
            loginTicketDao.updateStatus(ticket.getTicket(),2);
        }
        // junit.Assert 是测试用类，测试一般都用它
        Assert.assertEquals("password", userDao.selectById(2).getPassword() );
        userDao.deleteById(1);
        Assert.assertNull(userDao.selectById(1));

        Assert.assertEquals(1,loginTicketDao.selectByTicket("TICKET1").getId());
        Assert.assertEquals(2,loginTicketDao.selectByTicket("TICKET1").getStatus());

    }
}
