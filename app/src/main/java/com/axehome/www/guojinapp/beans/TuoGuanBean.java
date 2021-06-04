package com.axehome.www.guojinapp.beans;


import java.util.Date;

/**
 * @Author: jaywechen
 * @Description: 托管实体
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class TuoGuanBean {

    private Integer id;
    private String good_name;
    private String good_imgs;
    private String good_detail;
    private Date setup_datetime;
    private String good_guige;
    private String good_zhengshu;
    private String status;
    private String username;
    private Integer user_id;
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGood_name() {
        return good_name == null ? "" : good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_imgs() {
        return good_imgs == null ? "" : good_imgs;
    }

    public void setGood_imgs(String good_imgs) {
        this.good_imgs = good_imgs;
    }

    public String getGood_detail() {
        return good_detail == null ? "" : good_detail;
    }

    public void setGood_detail(String good_detail) {
        this.good_detail = good_detail;
    }

    public Date getSetup_datetime() {
        return setup_datetime;
    }

    public void setSetup_datetime(Date setup_datetime) {
        this.setup_datetime = setup_datetime;
    }

    public String getGood_guige() {
        return good_guige == null ? "" : good_guige;
    }

    public void setGood_guige(String good_guige) {
        this.good_guige = good_guige;
    }

    public String getGood_zhengshu() {
        return good_zhengshu == null ? "" : good_zhengshu;
    }

    public void setGood_zhengshu(String good_zhengshu) {
        this.good_zhengshu = good_zhengshu;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
