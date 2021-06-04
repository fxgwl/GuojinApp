package com.axehome.www.guojinapp.beans;

public class RoleOrderBean {

    private Integer order_id;
    private String order_code;
    private String order_type;//1:角色费用
    private Integer order_state;//0：待支付;1:已支付;2：已完成
    private String order_setup;//
    private Integer user_id;//
    private Double order_value;//
    private Integer goods_id;//
    private String order_pic1;//
    private String order_pic2;//
    private String pay_way;//

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_code() {
        return order_code == null ? "" : order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getOrder_type() {
        return order_type == null ? "" : order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public Integer getOrder_state() {
        return order_state;
    }

    public void setOrder_state(Integer order_state) {
        this.order_state = order_state;
    }

    public String getOrder_setup() {
        return order_setup == null ? "" : order_setup;
    }

    public void setOrder_setup(String order_setup) {
        this.order_setup = order_setup;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Double getOrder_value() {
        return order_value;
    }

    public void setOrder_value(Double order_value) {
        this.order_value = order_value;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getOrder_pic1() {
        return order_pic1 == null ? "" : order_pic1;
    }

    public void setOrder_pic1(String order_pic1) {
        this.order_pic1 = order_pic1;
    }

    public String getOrder_pic2() {
        return order_pic2 == null ? "" : order_pic2;
    }

    public void setOrder_pic2(String order_pic2) {
        this.order_pic2 = order_pic2;
    }

    public String getPay_way() {
        return pay_way == null ? "" : pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }
}
