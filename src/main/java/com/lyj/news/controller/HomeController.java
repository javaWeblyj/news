package com.lyj.news.controller;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/13 01:57
 * @Description:
 */

import com.lyj.news.model.News;
import com.lyj.news.model.ViewObject;
import com.lyj.news.service.NewsService;
import com.lyj.news.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: news
 * @description: 首页业务逻辑层
 * @author: babyL.FinlayL
 * @createDate: 2018-10-13 01:57
 **/
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    private List<ViewObject> getNews(int userId, int offset, int limit){
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);

        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList){
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
    // test
    @RequestMapping("/test")
    @ResponseBody
    public String index() {
        logger.info("Visit Test");
        return "Hello Spring boot!";
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        // logger.info("Test Test Test!");
        model.addAttribute("vos", getNews(0, 0, 10));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop",defaultValue = "0") int pop) {

        logger.info(" Test Test Test!" + "userId:" + userId);
        model.addAttribute("pop",pop);
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }
}
