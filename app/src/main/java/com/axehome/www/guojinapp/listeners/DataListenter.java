package com.axehome.www.guojinapp.listeners;

/**
 * Created by Axehome_Mr.z on 2019/5/23 12:36
 * $Describe
 */
public interface DataListenter {
    void initSuccess(String bean);

    void initError(String msg);

    void showLoading();

    void hideLoading();
}
