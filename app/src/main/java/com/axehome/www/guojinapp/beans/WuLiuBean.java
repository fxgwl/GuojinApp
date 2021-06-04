package com.axehome.www.guojinapp.beans;


/**
 * @Author: jaywechen
 * @Description: 商户信息
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class WuLiuBean {
    private String time;
    private String status;

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
