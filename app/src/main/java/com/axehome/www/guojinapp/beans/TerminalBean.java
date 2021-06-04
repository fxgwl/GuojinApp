package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/9/3 12:31
 */
public class TerminalBean {
    private Integer id;
    private String  terminal_id;
    private String merchant_no;
    private String store_code;
    private String terminal_name;
    private Integer terminal_state;
    private StoreBean storeBean;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerminal_id() {
        return terminal_id == null ? "" : terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getMerchant_no() {
        return merchant_no == null ? "" : merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getStore_code() {
        return store_code == null ? "" : store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getTerminal_name() {
        return terminal_name == null ? "" : terminal_name;
    }

    public void setTerminal_name(String terminal_name) {
        this.terminal_name = terminal_name;
    }

    public Integer getTerminal_state() {
        return terminal_state;
    }

    public void setTerminal_state(Integer terminal_state) {
        this.terminal_state = terminal_state;
    }

    public StoreBean getStoreBean() {
        return storeBean;
    }

    public void setStoreBean(StoreBean storeBean) {
        this.storeBean = storeBean;
    }
}
