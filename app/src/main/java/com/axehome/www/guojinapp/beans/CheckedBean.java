package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_fxg on 2021/4/29 11:51
 */
public class CheckedBean {
    private String name;
    private boolean checked;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
