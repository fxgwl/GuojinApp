package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/8/27 10:47
 */
public class BannerBean {
    private Integer banner_id;
    private String banner_path;
    private String banner_order;
    private String create_time;
    private String ue_content;

    public Integer getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(Integer banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_path() {
        return banner_path == null ? "" : banner_path;
    }

    public void setBanner_path(String banner_path) {
        this.banner_path = banner_path;
    }

    public String getBanner_order() {
        return banner_order == null ? "" : banner_order;
    }

    public void setBanner_order(String banner_order) {
        this.banner_order = banner_order;
    }

    public String getCreate_time() {
        return create_time == null ? "" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUe_content() {
        return ue_content == null ? "" : ue_content;
    }

    public void setUe_content(String ue_content) {
        this.ue_content = ue_content;
    }
}
