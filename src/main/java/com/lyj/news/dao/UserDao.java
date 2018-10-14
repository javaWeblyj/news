package com.lyj.news.dao;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/12 11:35
 * @Description:
 */

import com.lyj.news.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @program: news
 * @description: 用户持久层
 * @author: babyL.FinlayL
 * @createDate: 2018-10-12 11:35
 **/
@Mapper
@Component(value = "userDao")
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url ";

    // 注意这里的#{ var }这里的占位符 var 要跟具体的实体类的属性对应
    // 而不是表的字段
    @Insert({"insert into ", TABLE_NAME , "(", INSERT_FIELDS,
    ") values (#{name}, #{password}, #{salt}, #{headUrl})"  })
    int addUser(User user);

    @Select( { "select ", SELECT_FIELDS, "from", TABLE_NAME, " where id=#{id}" } )
    User selectById(int id);

    @Select( { "select ", SELECT_FIELDS, "from", TABLE_NAME, " where name=#{name}" } )
    User selectByName(String name);


    @Update( { "update ", TABLE_NAME, " set password=#{password} where id=#{id}" } )
    void updatePassword(User user);

    @Delete( { "delete from ", TABLE_NAME, "where id=#{id}" } )
    void deleteById(int id);
}
