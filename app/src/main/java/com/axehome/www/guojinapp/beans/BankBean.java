package com.axehome.www.guojinapp.beans;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jaywechen
 * @Description: 商户信息
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class BankBean {
    private String zong_hang_code;
    private String zong_hang_name;
    private String zhi_hang_code;
    private String zhi_hang_name;
    private String city_code;
    private String zhi_hang_address;
    private List<BankBean> clist;

    public String getZong_hang_code() {
        return zong_hang_code == null ? "" : zong_hang_code;
    }

    public void setZong_hang_code(String zong_hang_code) {
        this.zong_hang_code = zong_hang_code;
    }

    public String getZong_hang_name() {
        return zong_hang_name == null ? "" : zong_hang_name;
    }

    public void setZong_hang_name(String zong_hang_name) {
        this.zong_hang_name = zong_hang_name;
    }

    public String getZhi_hang_code() {
        return zhi_hang_code == null ? "" : zhi_hang_code;
    }

    public void setZhi_hang_code(String zhi_hang_code) {
        this.zhi_hang_code = zhi_hang_code;
    }

    public String getZhi_hang_name() {
        return zhi_hang_name == null ? "" : zhi_hang_name;
    }

    public void setZhi_hang_name(String zhi_hang_name) {
        this.zhi_hang_name = zhi_hang_name;
    }

    public String getCity_code() {
        return city_code == null ? "" : city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getZhi_hang_address() {
        return zhi_hang_address == null ? "" : zhi_hang_address;
    }

    public void setZhi_hang_address(String zhi_hang_address) {
        this.zhi_hang_address = zhi_hang_address;
    }

    public List<BankBean> getClist() {
        if (clist == null) {
            return new ArrayList<>();
        }
        return clist;
    }

    public void setClist(List<BankBean> clist) {
        this.clist = clist;
    }
}
