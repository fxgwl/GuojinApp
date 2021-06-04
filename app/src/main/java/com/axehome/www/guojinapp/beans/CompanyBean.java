package com.axehome.www.guojinapp.beans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: jaywechen
 * @Description: 课程实体
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class CompanyBean {

    private Integer id;
    private String company_name;
    private String company_detail;
    private String company_tel;
    private Date company_datetime;
    private String company_imgs;
    private String company_status;
    private String company_logo;
    private List<CourseBean> courseBeanList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name == null ? "" : company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_detail() {
        return company_detail == null ? "" : company_detail;
    }

    public void setCompany_detail(String company_detail) {
        this.company_detail = company_detail;
    }

    public String getCompany_tel() {
        return company_tel == null ? "" : company_tel;
    }

    public void setCompany_tel(String company_tel) {
        this.company_tel = company_tel;
    }

    public Date getCompany_datetime() {
        return company_datetime;
    }

    public void setCompany_datetime(Date company_datetime) {
        this.company_datetime = company_datetime;
    }

    public String getCompany_imgs() {
        return company_imgs == null ? "" : company_imgs;
    }

    public void setCompany_imgs(String company_imgs) {
        this.company_imgs = company_imgs;
    }

    public String getCompany_status() {
        return company_status == null ? "" : company_status;
    }

    public void setCompany_status(String company_status) {
        this.company_status = company_status;
    }

    public String getCompany_logo() {
        return company_logo == null ? "" : company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public List<CourseBean> getCourseBeanList() {
        if (courseBeanList == null) {
            return new ArrayList<>();
        }
        return courseBeanList;
    }

    public void setCourseBeanList(List<CourseBean> courseBeanList) {
        this.courseBeanList = courseBeanList;
    }
}
