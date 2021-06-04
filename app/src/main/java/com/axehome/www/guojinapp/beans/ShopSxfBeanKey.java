package com.axehome.www.guojinapp.beans;

public class ShopSxfBeanKey {
    private Integer shop_id;

    private String applicationId;

    private String mno;

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }

    public String getMno() {
        return mno == null ? "" : mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }
}