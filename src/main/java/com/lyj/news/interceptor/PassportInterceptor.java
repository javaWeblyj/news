package com.lyj.news.interceptor;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/14 13:00
 * @Description:
 */

import com.lyj.news.dao.LoginTicketDao;
import com.lyj.news.dao.UserDao;
import com.lyj.news.model.HostHolder;
import com.lyj.news.model.LoginTicket;
import com.lyj.news.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @program: news
 * @description: 拦截器 controller 前中后都有对应方法
 * @author: babyL.FinlayL
 * @createDate: 2018-10-14 13:00
 **/
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null){
            for (Cookie cookie : httpServletRequest.getCookies()){
                if (cookie.getName().equals("ticket")) {
                    // 登录或者注册登录时服务器都给客服端下发了cookie（cookie内容就是ticket）
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if(ticket != null){
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date())
                    || loginTicket.getStatus() != 0){
                return true;
            }

            // 跟longinTicket 中的 userId 把用户临时存放在 ThreadLocal 里面
            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);

        }
        return true;
        // return false 就直接结束了
        // return true 后面跟着处理
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // 在渲染之前，当前ThreadLocal线程这一次访问（也就是用户）可以让页面跟服务器访问
        if (modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 这个方法是做收尾工作的
        hostHolder.clear();
        // 如果不清理，每次登陆都放一个用户到 ThreadLocal线程那就肯定影响性能
        // 甚至线程崩毁
    }
}
