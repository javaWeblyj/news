package com.lyj.news.configuration;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/14 14:24
 * @Description:
 */

import com.lyj.news.interceptor.LoginRequireInterceptor;
import com.lyj.news.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: news
 * @description: 把拦截器注册到MVC里面，把用户注册
 * @author: babyL.FinlayL
 * @createDate: 2018-10-14 14:24
 **/
@Component
public class NewsWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequireInterceptor loginRequireInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，每次请求都需要调用一遍
        registry.addInterceptor(passportInterceptor); // 看用户是谁（全局的，看所有页面）
        // 注册登录拦截器
        registry.addInterceptor(loginRequireInterceptor).addPathPatterns("/m"); //用户访问页面是否符合权限要求

        super.addInterceptors(registry);
    }
}
