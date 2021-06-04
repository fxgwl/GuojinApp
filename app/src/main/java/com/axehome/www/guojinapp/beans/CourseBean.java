package com.axehome.www.guojinapp.beans;


import java.util.Date;

/**
 * @Author: jaywechen
 * @Description: 课程实体
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class CourseBean {

    private Integer id;
    private String course_name;
    private String course_detail;
    private String course_tel;
    private Date course_datetime;
    private String course_imgs;
    private String course_status;
    private String course_value;
    private String course_list;
    private Integer company_id;
    private String course_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name == null ? "" : course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_detail() {
        return course_detail == null ? "" : course_detail;
    }

    public void setCourse_detail(String course_detail) {
        this.course_detail = course_detail;
    }

    public String getCourse_tel() {
        return course_tel == null ? "" : course_tel;
    }

    public void setCourse_tel(String course_tel) {
        this.course_tel = course_tel;
    }

    public Date getCourse_datetime() {
        return course_datetime;
    }

    public void setCourse_datetime(Date course_datetime) {
        this.course_datetime = course_datetime;
    }

    public String getCourse_imgs() {
        return course_imgs == null ? "" : course_imgs;
    }

    public void setCourse_imgs(String course_imgs) {
        this.course_imgs = course_imgs;
    }

    public String getCourse_status() {
        return course_status == null ? "" : course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getCourse_value() {
        return course_value == null ? "" : course_value;
    }

    public void setCourse_value(String course_value) {
        this.course_value = course_value;
    }

    public String getCourse_list() {
        return course_list == null ? "" : course_list;
    }

    public void setCourse_list(String course_list) {
        this.course_list = course_list;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCourse_type() {
        return course_type == null ? "" : course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }
}
