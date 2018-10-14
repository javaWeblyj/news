package com.lyj.news.controller;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/5 23:14
 * @Description:
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: news
 * @description: l练习
 * @author: babyL.FinlayL
 * @createDate: 2018-10-05 23:14
 **/
 @Controller
public class IndexController {
//    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


/*    @RequestMapping({"/", "/index"})
    @ResponseBody
    public String index() {
//        logger.info("Visit Index");
        return "Hello Spring boot!";
    }*/

    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") String userId,
                          @RequestParam(value = "type", defaultValue = "1" ) int type,
                          @RequestParam(value = "key", defaultValue = "lyj") String key
                          ){
//        logger.info("Visit Index");
        return String.format("GID{%s},UID{%s},TYPE{%d},KEY{%s}",groupId, userId, type, key);
    }

    @RequestMapping(value = {"/m"})
    @ResponseBody
    public String news(Model model){
        model.addAttribute("value","hello!");
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i*i));
        }
        model.addAttribute("colors", colors);
        model.addAttribute("map", map);
        return "new";
    }
}
