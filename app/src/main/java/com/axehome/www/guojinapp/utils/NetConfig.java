package com.axehome.www.guojinapp.utils;

/**
 * Created by Administrator on 2017/12/5.
 */

public class NetConfig {

    //项目基地址
    //public final static String baseurl = "http://157.10.1.11/";//服务器图片地址

    public final static String baseurl = "http://app.haideyipai.com/";//服务器IP地址
    //public final static String baseurl_sxf = `"https://openapi-test.tianquetech.com/";//随行付

    //验证码
    public final static String sendcode = baseurl + "app/mobile/code";//user_phone
    public final static String userRegister = baseurl+"user/save";//password//phone
    public final static String userLogin = baseurl+"app/user_login";//password//phone
    public final static String passwordModify = baseurl+"user/password/modify";//password//phone
    public final static String userUpgroup = baseurl+"user/upgroup";//user;role_type

    public final static String fileUpload = baseurl +"file/upload";//上传文件

    public final static String getBankName = baseurl +"shop/getBankName";//获取总行分类
    public final static String getFenHangClass = baseurl +"shop/getFenHangClass";//获取分行分类

    public final static String newestApp = baseurl +"app/checkAndroidVersion";//app在线升级;
    public final static String getBaseurl = baseurl +"banner/list";//轮播图List;
    public final static String getMsg = baseurl +"msg/list";//消息List;
    public final static String getGoodsClass = baseurl +"goods/getClass";//消息List;
    public final static String getGoodsList = baseurl +"goods/goods_list_normal";//获取商品列表
    public final static String getGoodsListForShangPu = baseurl +"goods/shangpu/goods_list_normal";//获取商品列表

    /*order*/
    public final static String saveOrder = baseurl +"app/order/save";//用户下单
    public final static String getAddressList = baseurl +"app/address/list";//获取用户地址列表
    public final static String saveAddress = baseurl +"app/adress/save";//我的地址/添加地址
    public final static String updateAddress = baseurl +"app/address/update";//修改用户地址信息
    public final static String delAddress = baseurl +"app/address/delByUserId";//我的地址/添加地址

    public final static String userModify = baseurl +"app/user/modify";//修改我的信息;
    public final static String userDetail = baseurl +"app/user/detail";//用户详情;
    public final static String userUpdate = baseurl +"user/update";//修改用户信息;
    public final static String userList = baseurl +"user/lists";//用户下的落地代表;
    public final static String shopSave = baseurl +"shop/save";//商户进件;
    public final static String getShopList = baseurl +"shop/getShopList";//用户下商户列表;

    public final static String submitTuoGuan = baseurl +"app/tuoguan/insert";//用户托管服务;
    public final static String submitJianDing = baseurl +"app/jianding/insert";//用户鉴定服务;

    public final static String roleList = baseurl +"user/role_list";//角色列表;

    public final static String getStoreForShop = baseurl +"shop/getStoreForShop";//获取商户下门店列表;
    public final static String getStoreForShopAll = baseurl +"shop/getStoreForShopAll";//获取商户下all门店列表;
    public final static String getStoreAllForUserId = baseurl +"store/getStoreAllForUserId";//获取用户下all门店列表;
    public final static String addStroe = baseurl +"store/create";//获取商户下门店列表;
    public final static String getTerminalListByStore = baseurl +"terminal/getTerminalListByStore";//获取商户下门店列表;
    public final static String addTerminal = baseurl +"terminal/create";//新增设备;

    public final static String getRecommentLogList = baseurl +"recomment/getListByUserId";//个人中心余额账单详情;


    //客服接口
    public final static String kefuList = baseurl +"app/kefu/sysList";//获取客服List;
    public final static String getSysinfo = baseurl +"app/sysinfo/detail";//系统信息
    public final static String getJianDingUrl = baseurl +"app/jianding/list";//用户鉴定服务列表
    public final static String getCourseClassUrl = baseurl +"app/course/classList";//课程类别列表
    public final static String getCourseUrl = baseurl +"app/course/list";//课程类别列表
    public final static String getCompanyDetail = baseurl +"app/company/detail";
    public final static String toPayCourse = baseurl +"app/usercourse/insert";//用户课程服务下单


    public final static String toGongyingshang = baseurl +"app/apply/gongyingshang";//用户审请供应商

    public final static String getMyCouponList = baseurl +"app/coupon/myList";//获取我的优惠券列表
    public final static String getMyUsercourseList = baseurl +"app/usercourse/list";//用户课程列表
    public final static String getMyTuoGuanList = baseurl +"app/tuoguan/list";//用户课程列表

    public final static String getUserAddPhone = baseurl +"app/user_add_phone";//用户绑定手机
    public final static String getOrderListUrl = baseurl +"app/order/list";//获取订单列表
    public final static String getOrderDetailUrl = baseurl +"app/order/detail";//获取订单详情
    public final static String toPayForOrderUrl = baseurl +"app/order/toPayForOrder";//获取订单详情
    public final static String OrderUpdateUrl = baseurl +"app/order/Update";//获取订单详情

    public final static String getServiceList = baseurl +"app/getHaideMallList";//获取平台列表
    public final static String getHaideMallJiFen = baseurl +"app/getHaideMallJiFen";//获取平台积分
    public final static String getUserJifen = baseurl +"app/submitUserJifen";//兑换平台积分

    public final static String getWuLuDetail = "https://wuliu.market.alicloudapi.com/kdi";//获取物流信息
//--------------------------------------------------------------------
    //
//                                  _oo8oo_
//                                 o8888888o
//                                 88" . "88
//                                 (| -_- |)
//                                 0\  =  /0
//                               ___/'==='\___
//                             .' \\|     |// '.
//                            / \\|||  :  |||// \
//                           / _||||| -:- |||||_ \
//                          |   | \\\  -  /// |   |
//                          | \_|  ''\---/''  |_/ |
//                          \  .-\__  '-'  __/-.  /
//                        ___'. .'  /--.--\  '. .'___
//                     ."" '<  '.___\_<|>_/___.'  >' "".
//                    | | :  `- \`.:`\ _ /`:.`/ -`  : | |
//                    \  \ `-.   \_ __\ /__ _/   .-` /  /
//                =====`-.____`.___ \_____/ ___.`____.-`=====
//                                  `=---=`
//
//
//       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//              佛祖保佑                                代码无Bug
}
