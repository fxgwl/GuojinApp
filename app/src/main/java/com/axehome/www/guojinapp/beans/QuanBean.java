package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/8/20 12:36
 */
public class QuanBean {
    private String total;
    private String value;

    public String getTotal() {
        return total == null ? "" : total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "total='" + total + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
