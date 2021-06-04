package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/8/27 10:47
 */
public class MsgBean {
    private Integer msg_id;
    private String msg_tital;
    private String msg_content;
    private String msg_setup;
    private Integer msg_type;
    private String msg_http;
    private String msg_num;

    public Integer getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Integer msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_tital() {
        return msg_tital == null ? "" : msg_tital;
    }

    public void setMsg_tital(String msg_tital) {
        this.msg_tital = msg_tital;
    }

    public String getMsg_content() {
        return msg_content == null ? "" : msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_setup() {
        return msg_setup == null ? "" : msg_setup;
    }

    public void setMsg_setup(String msg_setup) {
        this.msg_setup = msg_setup;
    }

    public Integer getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(Integer msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_http() {
        return msg_http == null ? "" : msg_http;
    }

    public void setMsg_http(String msg_http) {
        this.msg_http = msg_http;
    }

    public String getMsg_num() {
        return msg_num == null ? "" : msg_num;
    }

    public void setMsg_num(String msg_num) {
        this.msg_num = msg_num;
    }
}
