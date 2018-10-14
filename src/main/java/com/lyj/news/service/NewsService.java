package com.lyj.news.service;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/13 14:54
 * @Description:
 */

import com.lyj.news.dao.NewsDao;
import com.lyj.news.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: news
 * @description: 咨询业务逻辑层
 * @author: babyL.FinlayL
 * @createDate: 2018-10-13 14:54
 **/
@Service
public class NewsService {

    // Spring Team建议“始终在bean中使用基于构造函数的依赖注入。始终使用断言来强制依赖”。
    @Autowired
    NewsDao newsDao;

    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }
}
