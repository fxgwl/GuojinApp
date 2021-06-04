package com.axehome.www.guojinapp.beans;

import java.util.List;

public class GoodClassBean {
    private Integer id;

    private String good_class_name;

    private String good_type;

    private List<PreGoodsBean> goodsBeanList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGood_class_name() {
        return good_class_name;
    }

    public void setGood_class_name(String good_class_name) {
        this.good_class_name = good_class_name == null ? null : good_class_name.trim();
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type == null ? null : good_type.trim();
    }

    public List<PreGoodsBean> getGoodsBeanList() {
        return goodsBeanList;
    }

    public void setGoodsBeanList(List<PreGoodsBean> goodsBeanList) {
        this.goodsBeanList = goodsBeanList;
    }
}