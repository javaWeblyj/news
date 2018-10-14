package com.lyj.news.controller;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/14 00:31
 * @Description:
 */

import com.lyj.news.service.UserService;
import com.lyj.news.util.NewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: news
 * @description: 用户登录业务
 * @author: babyL.FinlayL
 * @createDate: 2018-10-14 00:31
 **/
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = { "/reg/" }, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0" )int rememberme,
                      HttpServletResponse response){
        try {
            Map<String, Object> map = userService.register(username,password);
            // {"code":0,"msg":"xxx"} JSON 数据格式，要返回这种形式
            // 把数据一JSON数据格式返回给前端
            if (map.containsKey("ticket")){
                // 注册成功需要把数据写到用户cookie
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());

                // 设置cookie 全栈有效
                cookie.setPath("/");
                // 根据条件要求设计cookie 最大保留时间，不设置的话，默认一关闭浏览器cookie 就没了
                if (rememberme > 0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return NewsUtil.getJSONString(0,"注册成功");
            }else {
                return NewsUtil.getJSONString(1,map);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return NewsUtil.getJSONString(1,"注册异常");
        }
    }

    @RequestMapping(value = { "/login/" }, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0" ) int rememberme,
                       HttpServletResponse response ){
        try {
            Map<String, Object> map = userService.login(username,password);
            // {"code":0,"msg":"xxx"} JSON 数据格式，要返回这种形式

            if (map.containsKey("ticket")){
                // 登录成功需要把数据写到用户cookie
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());

                // 设置cookie 全栈有效
                cookie.setPath("/");
                // 根据条件要求设计cookie 最大保留时间，不设置的话，默认一关闭浏览器cookie 就没了
                if (rememberme > 0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return NewsUtil.getJSONString(0,"登录成功");
            }else {
                return NewsUtil.getJSONString(1,map);
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return NewsUtil.getJSONString(1,"登录异常");
        }
    }

    // 退出其实就是把 ticket 状态设为非0
    @RequestMapping(value = { "/logout/" }, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
