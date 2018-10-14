package com.lyj.news.model;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/14 13:53
 * @Description:
 */

import org.springframework.stereotype.Component;

/**
 * @program: news
 * @description: 存储（表示）当前访问（这条线程）用户是谁
 * @author: babyL.FinlayL
 * @createDate: 2018-10-14 13:53
 **/
@Component
public class HostHolder {
    // 多用户同时访问时以ThreadLocal 来区分每一条用户。
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
