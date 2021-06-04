package com.axehome.www.guojinapp.beans;

import java.util.Date;

public class OrderBean {
    private Integer order_id;//订单表主键自增序列

    private String order_num;//订单编号

    private String order_status;//0:待付款；1：待发货；2：待收货；3：已完成；4:已删除；5：待确认

    private Double order_good_value;//产品金额

    private Integer order_good_id;//产品编号

    private Date order_datetime;//下单时间

    private String order_address;//收货地址

    private String order_pay_way;//alipay、wxpay

    private Integer order_buy_user_id;//买家ID

    private Integer ordre_sale_user_id;//卖家ID  如果是自营 为空

    private String order_wulv_name;//物流公司名称

    private String order_wulv_num;//运单号

    private String order_type;//订单类型 1:普通商品；2：积分商城；3：天天推荐；4：非遗文化；5：精选推荐; 6：自强如你；

    private String order_backup;//预留

    private String order_shouhuo_name;//收货人姓名

    private String order_shouhuo_phone;//收货人手机号

    private String order_sale_name;//卖家姓名

    private String order_bank_name;//开户行名称  暂未使用

    private String order_bank_zhi_name;//支行名称  暂未使用

    private String order_card_num;//银行卡号 暂未使用

    private String order_sale_phone;//卖家电话

    private String order_good_name;//产品名称

    private String order_good_img;//产品图

    private Integer coupon_id;//优惠券ID  如果用了就记录，如果没有用就传空

    private Double coupon_value;//优惠券金额

    private Double order_value;//订单支付金额=产品价格-优惠券金额


    /*暂存*/
    private PreGoodsBean goodsBean;

    private User saleUser;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status == null ? null : order_status.trim();
    }

    public Double getOrder_good_value() {
        return order_good_value;
    }

    public void setOrder_good_value(Double order_good_value) {
        this.order_good_value = order_good_value;
    }

    public Integer getOrder_good_id() {
        return order_good_id;
    }

    public void setOrder_good_id(Integer order_good_id) {
        this.order_good_id = order_good_id;
    }

    public Date getOrder_datetime() {
        return order_datetime;
    }

    public void setOrder_datetime(Date order_datetime) {
        this.order_datetime = order_datetime;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address == null ? null : order_address.trim();
    }

    public String getOrder_pay_way() {
        return order_pay_way;
    }

    public void setOrder_pay_way(String order_pics) {
        this.order_pay_way = order_pics == null ? null : order_pics.trim();
    }

    public Integer getOrder_buy_user_id() {
        return order_buy_user_id;
    }

    public void setOrder_buy_user_id(Integer order_buy_user_id) {
        this.order_buy_user_id = order_buy_user_id;
    }

    public Integer getOrdre_sale_user_id() {
        return ordre_sale_user_id;
    }

    public void setOrdre_sale_user_id(Integer ordre_sale_user_id) {
        this.ordre_sale_user_id = ordre_sale_user_id;
    }

    public String getOrder_wulv_name() {
        return order_wulv_name;
    }

    public void setOrder_wulv_name(String order_wulv_name) {
        this.order_wulv_name = order_wulv_name == null ? null : order_wulv_name.trim();
    }

    public String getOrder_wulv_num() {
        return order_wulv_num;
    }

    public void setOrder_wulv_num(String order_wulv_num) {
        this.order_wulv_num = order_wulv_num == null ? null : order_wulv_num.trim();
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type == null ? null : order_type.trim();
    }

    public String getOrder_backup() {
        return order_backup;
    }

    public void setOrder_backup(String order_backup) {
        this.order_backup = order_backup == null ? null : order_backup.trim();
    }

    public String getOrder_shouhuo_name() {
        return order_shouhuo_name;
    }

    public void setOrder_shouhuo_name(String order_shouhuo_name) {
        this.order_shouhuo_name = order_shouhuo_name == null ? null : order_shouhuo_name.trim();
    }

    public String getOrder_shouhuo_phone() {
        return order_shouhuo_phone;
    }

    public void setOrder_shouhuo_phone(String order_shouhuo_phone) {
        this.order_shouhuo_phone = order_shouhuo_phone == null ? null : order_shouhuo_phone.trim();
    }

    public String getOrder_sale_name() {
        return order_sale_name;
    }

    public void setOrder_sale_name(String order_sale_name) {
        this.order_sale_name = order_sale_name == null ? null : order_sale_name.trim();
    }

    public String getOrder_bank_name() {
        return order_bank_name;
    }

    public void setOrder_bank_name(String order_bank_name) {
        this.order_bank_name = order_bank_name == null ? null : order_bank_name.trim();
    }

    public String getOrder_bank_zhi_name() {
        return order_bank_zhi_name;
    }

    public void setOrder_bank_zhi_name(String order_bank_zhi_name) {
        this.order_bank_zhi_name = order_bank_zhi_name == null ? null : order_bank_zhi_name.trim();
    }

    public String getOrder_card_num() {
        return order_card_num;
    }

    public void setOrder_card_num(String order_card_num) {
        this.order_card_num = order_card_num == null ? null : order_card_num.trim();
    }

    public String getOrder_sale_phone() {
        return order_sale_phone;
    }

    public void setOrder_sale_phone(String order_sale_phone) {
        this.order_sale_phone = order_sale_phone;
    }

    public PreGoodsBean getGoodsBean() {
        return goodsBean;
    }

    public void setGoodsBean(PreGoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    public User getSaleUser() {
        return saleUser;
    }

    public void setSaleUser(User saleUser) {
        this.saleUser = saleUser;
    }

    public String getOrder_good_name() {
        return order_good_name;
    }

    public void setOrder_good_name(String order_good_name) {
        this.order_good_name = order_good_name;
    }

    public String getOrder_good_img() {
        return order_good_img;
    }

    public void setOrder_good_img(String order_good_img) {
        this.order_good_img = order_good_img;
    }

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Double getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(Double coupon_value) {
        this.coupon_value = coupon_value;
    }

    public Double getOrder_value() {
        return order_value;
    }

    public void setOrder_value(Double order_value) {
        this.order_value = order_value;
    }
}