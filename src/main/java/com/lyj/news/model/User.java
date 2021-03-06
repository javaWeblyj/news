package com.lyj.news.model;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/12 11:16
 * @Description:
 */

/**
 * @program: news
 * @description: 用户表对应实体类
 * @author: babyL.FinlayL
 * @createDate: 2018-10-12 11:16
 **/
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;// 密码加强后缀
    private String headUrl;// 头像地址

    public User() {

    }

    public User(String name) {
        this.name = name;
        this.password = " ";
        this.salt = " ";
        this.headUrl = " ";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
