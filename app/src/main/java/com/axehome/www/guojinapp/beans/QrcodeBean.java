package com.axehome.www.guojinapp.beans;

/**
 * Created by Axehome_Mr.z on 2020/8/20 12:36
 */
public class QrcodeBean {
    private String rate;
    private String rateType;

    public String getRate() {
        return rate == null ? "" : rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateType() {
        return rateType == null ? "" : rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
}
