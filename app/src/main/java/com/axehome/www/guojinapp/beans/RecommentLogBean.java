package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/9/4 16:34
 */
public class RecommentLogBean {
    private Integer recommend_log_id;
    private String recommend_type;
    private String recommend_detail;
    private Integer recommend_user_id;
    private Integer recommend_b_user_id;
    private String recommend_user_value;
    private String recommend_b_user_value;
    private String recommend_setup;
    private Integer recommend_status;

    public Integer getRecommend_log_id() {
        return recommend_log_id;
    }

    public void setRecommend_log_id(Integer recommend_log_id) {
        this.recommend_log_id = recommend_log_id;
    }

    public String getRecommend_type() {
        return recommend_type == null ? "" : recommend_type;
    }

    public void setRecommend_type(String recommend_type) {
        this.recommend_type = recommend_type;
    }

    public String getRecommend_detail() {
        return recommend_detail == null ? "" : recommend_detail;
    }

    public void setRecommend_detail(String recommend_detail) {
        this.recommend_detail = recommend_detail;
    }

    public Integer getRecommend_user_id() {
        return recommend_user_id;
    }

    public void setRecommend_user_id(Integer recommend_user_id) {
        this.recommend_user_id = recommend_user_id;
    }

    public Integer getRecommend_b_user_id() {
        return recommend_b_user_id;
    }

    public void setRecommend_b_user_id(Integer recommend_b_user_id) {
        this.recommend_b_user_id = recommend_b_user_id;
    }

    public String getRecommend_user_value() {
        return recommend_user_value == null ? "" : recommend_user_value;
    }

    public void setRecommend_user_value(String recommend_user_value) {
        this.recommend_user_value = recommend_user_value;
    }

    public String getRecommend_b_user_value() {
        return recommend_b_user_value == null ? "" : recommend_b_user_value;
    }

    public void setRecommend_b_user_value(String recommend_b_user_value) {
        this.recommend_b_user_value = recommend_b_user_value;
    }

    public String getRecommend_setup() {
        return recommend_setup == null ? "" : recommend_setup;
    }

    public void setRecommend_setup(String recommend_setup) {
        this.recommend_setup = recommend_setup;
    }

    public Integer getRecommend_status() {
        return recommend_status;
    }

    public void setRecommend_status(Integer recommend_status) {
        this.recommend_status = recommend_status;
    }
}
