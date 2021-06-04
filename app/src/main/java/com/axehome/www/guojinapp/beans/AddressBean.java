package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_fxg on 2021/4/8 16:24
 */
public class AddressBean {
    
    private Integer address_id;
    private String address_pro;
    private String address_city;
    private String address_area;
    private String address_detail;
    private Integer address_use_id;
    private String address_datetime;
    private String address_name;
    private String address_phone;
    private String is_checked;

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public String getAddress_pro() {
        return address_pro == null ? "" : address_pro;
    }

    public void setAddress_pro(String address_pro) {
        this.address_pro = address_pro;
    }

    public String getAddress_city() {
        return address_city == null ? "" : address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_area() {
        return address_area == null ? "" : address_area;
    }

    public void setAddress_area(String address_area) {
        this.address_area = address_area;
    }

    public String getAddress_detail() {
        return address_detail == null ? "" : address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public Integer getAddress_use_id() {
        return address_use_id;
    }

    public void setAddress_use_id(Integer address_use_id) {
        this.address_use_id = address_use_id;
    }

    public String getAddress_datetime() {
        return address_datetime == null ? "" : address_datetime;
    }

    public void setAddress_datetime(String address_datetime) {
        this.address_datetime = address_datetime;
    }

    public String getAddress_name() {
        return address_name == null ? "" : address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getAddress_phone() {
        return address_phone == null ? "" : address_phone;
    }

    public void setAddress_phone(String address_phone) {
        this.address_phone = address_phone;
    }

    public String getIs_checked() {
        return is_checked == null ? "" : is_checked;
    }

    public void setIs_checked(String is_checked) {
        this.is_checked = is_checked;
    }
}
