package com.lyj.news.dao;

import com.lyj.news.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/14 10:36
 * @Description: ticket标志用户登录状态实体类数据库持久化操作
 */
@Mapper
@Component
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, user_id, ticket, expired, status ";

    // 注意这里的#{ var }这里的占位符 var 要跟具体的实体类的属性对应
    // 而不是表的字段
    @Insert({"insert into ", TABLE_NAME , "(", INSERT_FIELDS,
            ") values (#{userId}, #{ticket}, #{expired}, #{status})"  })
    int addTicket(LoginTicket ticket);

    @Select( { "select ", SELECT_FIELDS, "from", TABLE_NAME, " where ticket=#{ticket}" } )
    LoginTicket selectByTicket(String ticket);

    @Update( { "update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}" } )
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
