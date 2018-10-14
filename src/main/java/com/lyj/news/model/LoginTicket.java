package com.lyj.news.model;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/14 10:28
 * @Description:
 */

import java.util.Date;

/**
 * @program: news
 * @description: ticket标志用户登录状态的实体类
 * @author: babyL.FinlayL
 * @createDate: 2018-10-14 10:28
 **/

public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private Date expired;// ticket 有效时间
    private int status; // 是否弃用ticket 的标志（0有效，1无效），实现逻辑删除ticket

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
