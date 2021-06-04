package com.axehome.www.guojinapp.beans;


/**
 * @Author: fxg
 * @Description: 视频信息
 * @Created by: IntelliJ IDEA
 * @Date: 16:11 2020/10/27
 */
public class VideoBean {
    private Integer video_id;
    private String video_tital;
    private String video_content;
    private String video_path;
    private String video_setup;
    private String video_type;
    private String video_state;
    private String video_pic;

    public Integer getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }

    public String getVideo_tital() {
        return video_tital == null ? "" : video_tital;
    }

    public void setVideo_tital(String video_tital) {
        this.video_tital = video_tital;
    }

    public String getVideo_content() {
        return video_content == null ? "" : video_content;
    }

    public void setVideo_content(String video_content) {
        this.video_content = video_content;
    }

    public String getVideo_path() {
        return video_path == null ? "" : video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getVideo_setup() {
        return video_setup == null ? "" : video_setup;
    }

    public void setVideo_setup(String video_setup) {
        this.video_setup = video_setup;
    }

    public String getVideo_type() {
        return video_type == null ? "" : video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_state() {
        return video_state == null ? "" : video_state;
    }

    public void setVideo_state(String video_state) {
        this.video_state = video_state;
    }

    public String getVideo_pic() {
        return video_pic == null ? "" : video_pic;
    }

    public void setVideo_pic(String video_pic) {
        this.video_pic = video_pic;
    }
}
