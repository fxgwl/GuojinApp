package com.axehome.www.guojinapp.utils;

import android.location.Location;

/**
 * Created by Axehome_Mr.z on 2019/5/21 14:33
 * $Describe
 */
public class Distance_towPoint {
    /**
     * 依据两点间经纬度坐标（double值），计算两点间距离，
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离：单位为公里
     */
    public static Double TowPiont(Double lat1, Double lng1, Double lat2, Double lng2) {
//        double radLat1 = rad(lat1);
//        double radLat2 = rad(lat2);
//        double a = radLat1 - radLat2;
//        double b = rad(lng1) - rad(lng2);
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000) / 10000;
//        Log.i("距离", s + "");
//        return s;
        float[] results=new float[1];
        Location.distanceBetween(lat1, lng1, lat2, lng2, results);
        return Double.valueOf(results[0]/1000);
    }
//    private static double rad(double d) {
//        return d * Math.PI / 180.0;
//    }
}
