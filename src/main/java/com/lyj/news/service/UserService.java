package com.lyj.news.service;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/12 21:33
 * @Description:
 */


import com.lyj.news.dao.LoginTicketDao;
import com.lyj.news.dao.UserDao;
import com.lyj.news.model.LoginTicket;
import com.lyj.news.model.User;
import com.lyj.news.util.NewsUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: news
 * @description: 负责用户业务模块的逻辑应用设计
 * @author: babyL.FinlayL
 * @createDate: 2018-10-12 21:33
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUser(int id) {
        return userDao.selectById(id);
    }

    /*
    * @Description: 注册登录 
    * @Param:  
    * @return:
    * @Author: babyL.FinlayL
    * @Date: 2018/10/14 1:21
    */ 
    public Map<String ,Object> register(String username, String password){
        Map<String ,Object> map = new HashMap<String,Object>();
        // 注册验证应该前后端都需要做
        if (StringUtils.isBlank(username)){
            map.put("msgname", "用户名不能为空");
            return map;
        }
        // 这里要注意一下，只要出现注册出错就直接返回出错，return跳出函数
        if (StringUtils.isBlank(password)){
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);
        if (user != null){
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        // 这里可以根据需求写的更严谨一些。比如密码强度验证。邮箱、电话绑定等

        // 准备写入数据库
        user = new User();
        user.setName(username);
        // user.setPassword(password); 这样明文保存是不可以的
        // 考虑用户信息安全，要加强密码并以秘钥形式保存数据库

        // md5 salt 使用UUID 给用户生成唯一的salt，这里取生成UUID的前五位
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000) ) );
        user.setPassword(NewsUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);
        
        // 注册成功，下发ticket，直接登陆
        // 给用户下发一个ticket，标志用户登录状态。前后端都可以查看
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public Map<String ,Object> login(String username, String password) {
        Map<String ,Object> map = new HashMap<String,Object>();
        // 注册验证应该前后端都需要做
        if (StringUtils.isBlank(username)){
            map.put("msgname", "用户名不能为空");
            return map;
        }
        // 这里要注意一下，只要出现注册出错就直接返回出错，return跳出函数
        if (StringUtils.isBlank(password)){
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        // 检查用户是否存在
        User user = userDao.selectByName(username);
        if (user == null){
            map.put("msgname", "用户名不存在");
            return map;
        }

        // 验证密码 Objects.requireNonNull() 运行时非空检查，参数值空时抛出空指针异常
        if (NewsUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd", "密码错误");
            logger.error("密码错误！！！！！！！！！！！");
            return map;
        }

        // 给用户下发一个ticket，标志用户登录状态。前后端都可以查看
        // 登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        // 设计ticket 可持续时间
        date.setTime(date.getTime() + 1000*3600*1);
        ticket.setExpired(date);
        ticket.setStatus(0);
        // 使用 UUID生成唯一的 ticket ，并把ticket 中“-”替换成 “”
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }
}
