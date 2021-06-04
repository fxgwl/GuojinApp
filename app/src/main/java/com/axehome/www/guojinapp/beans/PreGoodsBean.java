package com.axehome.www.guojinapp.beans;


import java.util.Date;

public class PreGoodsBean {
    private Integer good_id;

    private String good_name;

    private Double good_value;

    private Double good_value1;

    private Double good_value2;

    private String good_type;

    private String good_status;

    private String good_detail;

    private Date good_datetime;

    private Integer good_user_id;

    private Integer good_del;

    private String good_img;

    private Date good_up_datatime;

    private String good_come;

    private Double good_value_hight;

    private Double good_rate;

    private Integer good_class_id;
    private Double good_jifen;
    private Double good_gong_yi;
    private String is_coupon;

    private User userBean;

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name == null ? null : good_name.trim();
    }

    public Double getGood_value() {
        return good_value;
    }

    public void setGood_value(Double good_value) {
        this.good_value = good_value;
    }

    public Double getGood_value2() {
        return good_value2;
    }

    public void setGood_value2(Double good_value2) {
        this.good_value2 = good_value2;
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type == null ? null : good_type.trim();
    }

    public String getGood_status() {
        return good_status;
    }

    public void setGood_status(String good_status) {
        this.good_status = good_status == null ? null : good_status.trim();
    }

    public String getGood_detail() {
        return good_detail;
    }

    public void setGood_detail(String good_detail) {
        this.good_detail = good_detail == null ? null : good_detail.trim();
    }

    public Date getGood_datetime() {
        return good_datetime;
    }

    public void setGood_datetime(Date good_datetime) {
        this.good_datetime = good_datetime;
    }

    public Integer getGood_user_id() {
        return good_user_id;
    }

    public void setGood_user_id(Integer good_user_id) {
        this.good_user_id = good_user_id;
    }

    public Integer getGood_del() {
        return good_del;
    }

    public void setGood_del(Integer good_del) {
        this.good_del = good_del;
    }

    public String getGood_img() {
        return good_img;
    }

    public void setGood_img(String good_img) {
        this.good_img = good_img == null ? null : good_img.trim();
    }

    public Date getGood_up_datatime() {
        return good_up_datatime;
    }

    public void setGood_up_datatime(Date good_up_datatime) {
        this.good_up_datatime = good_up_datatime;
    }

    public String getGood_come() {
        return good_come;
    }

    public void setGood_come(String good_come) {
        this.good_come = good_come == null ? null : good_come.trim();
    }

    public Double getGood_value_hight() {
        return good_value_hight;
    }

    public void setGood_value_hight(Double good_value_hight) {
        this.good_value_hight = good_value_hight;
    }

    public Double getGood_rate() {
        return good_rate;
    }

    public void setGood_rate(Double good_rate) {
        this.good_rate = good_rate;
    }

    public Double getGood_value1() {
        return good_value1;
    }

    public void setGood_value1(Double good_value1) {
        this.good_value1 = good_value1;
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public Integer getGood_class_id() {
        return good_class_id;
    }

    public void setGood_class_id(Integer good_class_id) {
        this.good_class_id = good_class_id;
    }

    public Double getGood_jifen() {
        return good_jifen;
    }

    public void setGood_jifen(Double good_jifen) {
        this.good_jifen = good_jifen;
    }

    public Double getGood_gong_yi() {
        return good_gong_yi;
    }

    public void setGood_gong_yi(Double good_gong_yi) {
        this.good_gong_yi = good_gong_yi;
    }

    public String getIs_coupon() {
        return is_coupon;
    }

    public void setIs_coupon(String is_coupon) {
        this.is_coupon = is_coupon;
    }
}