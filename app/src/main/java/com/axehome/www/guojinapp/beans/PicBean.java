package com.axehome.www.guojinapp.beans;


/**
 * @Author: jaywechen
 * @Description: 商户信息
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class PicBean {
    private String pic_name;
    private String pic_url;
    private boolean is_must;

    public String getPic_name() {
        return pic_name == null ? "" : pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getPic_url() {
        return pic_url == null ? "" : pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public boolean isIs_must() {
        return is_must;
    }

    public void setIs_must(boolean is_must) {
        this.is_must = is_must;
    }
}
