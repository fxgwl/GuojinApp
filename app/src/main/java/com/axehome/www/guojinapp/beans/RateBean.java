package com.axehome.www.guojinapp.beans;


/**
 * @Author: jaywechen
 * @Description: 商户信息
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class RateBean {
    private String name;
    private String value;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
