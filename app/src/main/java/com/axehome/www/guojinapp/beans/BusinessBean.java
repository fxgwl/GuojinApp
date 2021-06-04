package com.axehome.www.guojinapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axehome_Mr.z on 2020/8/20 12:36
 */
public class BusinessBean {
    private String id;
    private String one_class;
    private String two_class;
    private String three_class;
    private String class_id;
    private String fei_lv;
    private String zhou_qi;
    private List<BusinessBean> clist;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOne_class() {
        return one_class == null ? "" : one_class;
    }

    public void setOne_class(String one_class) {
        this.one_class = one_class;
    }

    public String getTwo_class() {
        return two_class == null ? "" : two_class;
    }

    public void setTwo_class(String two_class) {
        this.two_class = two_class;
    }

    public String getThree_class() {
        return three_class == null ? "" : three_class;
    }

    public void setThree_class(String three_class) {
        this.three_class = three_class;
    }

    public String getClass_id() {
        return class_id == null ? "" : class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getFei_lv() {
        return fei_lv == null ? "" : fei_lv;
    }

    public void setFei_lv(String fei_lv) {
        this.fei_lv = fei_lv;
    }

    public String getZhou_qi() {
        return zhou_qi == null ? "" : zhou_qi;
    }

    public void setZhou_qi(String zhou_qi) {
        this.zhou_qi = zhou_qi;
    }

    public List<BusinessBean> getClist() {
        if (clist == null) {
            return new ArrayList<>();
        }
        return clist;
    }

    public void setClist(List<BusinessBean> clist) {
        this.clist = clist;
    }
}
