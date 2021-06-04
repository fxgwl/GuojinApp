package com.axehome.www.guojinapp.beans;


import java.util.Date;

/**
 * @Author: jaywechen
 * @Description: 课程实体
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class UserCouponBean {

    private Integer id;
    private Date setup_datetime;
    private Integer coupon_id;
    private Integer user_id;
    private String coupon_type;
    private CouponBean couponBean;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSetup_datetime() {
        return setup_datetime;
    }

    public void setSetup_datetime(Date setup_datetime) {
        this.setup_datetime = setup_datetime;
    }

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCoupon_type() {
        return coupon_type == null ? "" : coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public CouponBean getCouponBean() {
        return couponBean;
    }

    public void setCouponBean(CouponBean couponBean) {
        this.couponBean = couponBean;
    }

    public class CouponBean{
        private Integer id;
        private Date coupon_datetime;
        private String coupon_type;
        private Double coupon_value;
        private Date coupon_datetime_star;
        private Date coupon_datetime_end;
        private Integer coupon_num;
        private Integer coupon_condition;
        private String coupon_name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Date getCoupon_datetime() {
            return coupon_datetime;
        }

        public void setCoupon_datetime(Date coupon_datetime) {
            this.coupon_datetime = coupon_datetime;
        }

        public String getCoupon_type() {
            return coupon_type == null ? "" : coupon_type;
        }

        public void setCoupon_type(String coupon_type) {
            this.coupon_type = coupon_type;
        }

        public Double getCoupon_value() {
            return coupon_value;
        }

        public void setCoupon_value(Double coupon_value) {
            this.coupon_value = coupon_value;
        }

        public Date getCoupon_datetime_star() {
            return coupon_datetime_star;
        }

        public void setCoupon_datetime_star(Date coupon_datetime_star) {
            this.coupon_datetime_star = coupon_datetime_star;
        }

        public Date getCoupon_datetime_end() {
            return coupon_datetime_end;
        }

        public void setCoupon_datetime_end(Date coupon_datetime_end) {
            this.coupon_datetime_end = coupon_datetime_end;
        }

        public Integer getCoupon_num() {
            return coupon_num;
        }

        public void setCoupon_num(Integer coupon_num) {
            this.coupon_num = coupon_num;
        }

        public Integer getCoupon_condition() {
            return coupon_condition;
        }

        public void setCoupon_condition(Integer coupon_condition) {
            this.coupon_condition = coupon_condition;
        }

        public String getCoupon_name() {
            return coupon_name == null ? "" : coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }
    }
}
