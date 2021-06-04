package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/9/3 12:31
 */
public class StoreBean implements Comparable<StoreBean>{
    private Integer store_id;
    private String merchant_no;
    private String store_name;
    private String store_addre;
    private String store_person;
    private String store_phone;
    private String store_email;
    private Integer store_state;
    private String store_code;
    private String store_lng;
    private String store_lat;
    private String store_logo;
    private String store_setup;
    private Double jiuli;

    private ShopBean shopBean;
    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getStore_name() {
        return store_name == null ? "" : store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_addre() {
        return store_addre == null ? "" : store_addre;
    }

    public void setStore_addre(String store_addre) {
        this.store_addre = store_addre;
    }

    public String getStore_person() {
        return store_person == null ? "" : store_person;
    }

    public void setStore_person(String store_person) {
        this.store_person = store_person;
    }

    public String getStore_phone() {
        return store_phone == null ? "" : store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getStore_email() {
        return store_email == null ? "" : store_email;
    }

    public void setStore_email(String store_email) {
        this.store_email = store_email;
    }

    public Integer getStore_state() {
        return store_state;
    }

    public void setStore_state(Integer store_state) {
        this.store_state = store_state;
    }

    public String getStore_code() {
        return store_code == null ? "" : store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getStore_lng() {
        return store_lng == null ? "" : store_lng;
    }

    public void setStore_lng(String store_lng) {
        this.store_lng = store_lng;
    }

    public String getStore_lat() {
        return store_lat == null ? "" : store_lat;
    }

    public void setStore_lat(String store_lat) {
        this.store_lat = store_lat;
    }

    public ShopBean getShopBean() {
        return shopBean;
    }

    public void setShopBean(ShopBean shopBean) {
        this.shopBean = shopBean;
    }

    public String getStore_logo() {
        return store_logo == null ? "" : store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public String getStore_setup() {
        return store_setup == null ? "" : store_setup;
    }

    public void setStore_setup(String store_setup) {
        this.store_setup = store_setup;
    }

    public Double getJiuli() {
        return jiuli;
    }

    public void setJiuli(Double jiuli) {
        this.jiuli = jiuli;
    }

    @Override
    public int compareTo(StoreBean storeBean) {
        //降序
        //return o.age - this.age;
        //升序
        if(this.jiuli - storeBean.jiuli<0){
            return -1;
        }else if(this.jiuli - storeBean.jiuli>0){
            return 1;
        }else{
            return 0;
        }
    }
}
