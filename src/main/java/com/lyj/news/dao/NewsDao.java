package com.lyj.news.dao;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/12 11:36
 * @Description:
 */

import com.lyj.news.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: news
 * @description: 咨询表持久层
 * @author: babyL.FinlayL
 * @createDate: 2018-10-12 11:36
 **/
@Mapper
@Component
public interface NewsDao {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 注意这里的#{ var }这里的占位符 var 要跟具体的实体类的属性对应
    // 而不是表的字段
    @Insert({"insert into ", TABLE_NAME , "(", INSERT_FIELDS, ") " +
            "values (#{title}, #{link}, #{image}, #{likeCount}, #{commentCount}, #{createdDate}, #{userId})"  })
    int addNews(News news);


    // 这个是xml配置要求 xml命名与对应Mapper interface名字一致，并要求放在同一个包文件下。
    List<News> selectByUserIdAndOffset(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit
                                       );

}
