package com.axehome.www.guojinapp.beans;


import java.util.Date;

/**
 * @Author: jaywechen
 * @Description: 用户课程实体
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class UserCourseBean {

    private Integer id;
    private String course_name;
    private Date order_datetime;
    private String course_status;
    private String course_value;
    private String company_logo;
    private Integer user_id;
    private Integer company_id;
    private String company_name;
    private CourseBean courseBean;

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

    public Date getOrder_datetime() {
        return order_datetime;
    }

    public void setOrder_datetime(Date order_datetime) {
        this.order_datetime = order_datetime;
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

    public String getCompany_logo() {
        return company_logo == null ? "" : company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name == null ? "" : company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public CourseBean getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(CourseBean courseBean) {
        this.courseBean = courseBean;
    }
}
