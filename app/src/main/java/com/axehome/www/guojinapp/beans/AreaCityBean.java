package com.axehome.www.guojinapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axehome_Mr.z on 2020/8/20 12:36
 */
public class AreaCityBean {
    /*pro_code":"310","city_code":"2900","dist_code":"2900","pro_name":"上海市","city_name":"上海市","dist_name":"黄浦区","clist":*/
    private String code;
    private String name;
    private List<AreaCityBean> children;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaCityBean> getChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<AreaCityBean> children) {
        this.children = children;
    }
}
