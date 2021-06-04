package com.axehome.www.guojinapp.beans;


import java.util.Date;

/**
 * @Author: jaywechen
 * @Description: 商户信息
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class JianDingBean {

    private Integer id;
    private String good_name;
    private String good_imgs;
    private String good_detail;
    private Date setup_datetime;
    private String good_guige;
    private String good_zhengshu;
    private String need_zheng;//是否需要证书 0：不需要；1：需要
    private String status;//'0：待鉴定；1：已鉴定；2：待支付',
    private Integer user_id;
    private String order_value;
    private String pay_way;
    private String real_num;
    private Double good_value;

    private String username;
    private String head_img;

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

    public String getNeed_zheng() {
        return need_zheng == null ? "" : need_zheng;
    }

    public void setNeed_zheng(String need_zheng) {
        this.need_zheng = need_zheng;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getOrder_value() {
        return order_value == null ? "" : order_value;
    }

    public void setOrder_value(String order_value) {
        this.order_value = order_value;
    }

    public String getPay_way() {
        return pay_way == null ? "" : pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getReal_num() {
        return real_num == null ? "" : real_num;
    }

    public void setReal_num(String real_num) {
        this.real_num = real_num;
    }

    public Double getGood_value() {
        return good_value;
    }

    public void setGood_value(Double good_value) {
        this.good_value = good_value;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_img() {
        return head_img == null ? "" : head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
}
