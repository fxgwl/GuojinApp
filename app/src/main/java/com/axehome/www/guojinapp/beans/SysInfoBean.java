package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/9/1 12:24
 * 系统支付信息
 */
public class SysInfoBean {
    private Integer id;
    private String sys_name;
    private String sys_address;
    private String sys_phone;
    private String sys_wechat_pay;
    private String sys_ali_pay;
    private String sys_hua_bei_pay;
    private String sys_ali_account;
    private String sys_type;
    private String sys_setup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSys_name() {
        return sys_name == null ? "" : sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getSys_address() {
        return sys_address == null ? "" : sys_address;
    }

    public void setSys_address(String sys_address) {
        this.sys_address = sys_address;
    }

    public String getSys_phone() {
        return sys_phone == null ? "" : sys_phone;
    }

    public void setSys_phone(String sys_phone) {
        this.sys_phone = sys_phone;
    }

    public String getSys_wechat_pay() {
        return sys_wechat_pay == null ? "" : sys_wechat_pay;
    }

    public void setSys_wechat_pay(String sys_wechat_pay) {
        this.sys_wechat_pay = sys_wechat_pay;
    }

    public String getSys_ali_pay() {
        return sys_ali_pay == null ? "" : sys_ali_pay;
    }

    public void setSys_ali_pay(String sys_ali_pay) {
        this.sys_ali_pay = sys_ali_pay;
    }

    public String getSys_hua_bei_pay() {
        return sys_hua_bei_pay == null ? "" : sys_hua_bei_pay;
    }

    public void setSys_hua_bei_pay(String sys_hua_bei_pay) {
        this.sys_hua_bei_pay = sys_hua_bei_pay;
    }

    public String getSys_ali_account() {
        return sys_ali_account == null ? "" : sys_ali_account;
    }

    public void setSys_ali_account(String sys_ali_account) {
        this.sys_ali_account = sys_ali_account;
    }

    public String getSys_type() {
        return sys_type == null ? "" : sys_type;
    }

    public void setSys_type(String sys_type) {
        this.sys_type = sys_type;
    }

    public String getSys_setup() {
        return sys_setup == null ? "" : sys_setup;
    }

    public void setSys_setup(String sys_setup) {
        this.sys_setup = sys_setup;
    }
}
