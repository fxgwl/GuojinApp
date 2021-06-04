package com.axehome.www.guojinapp.beans;

/*CREATE TABLE `pay_inifo` (
        `id` int(11) NOT NULL,
        `pay_ver` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本号，当前版本210',
        `card_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户银联卡类型 0储蓄卡，1信用卡，2未知',
        `merchant_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户号',
        `merchant_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户名称',
        `terminal_id` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '终端号',
        `terminal_time` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '终端交易时间，yyyyMMddHHmmss，全局统一时间格式（01时参与拼接）',
        `terminal_trace` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '终端流水号，此处传商户发起预支付或公众号支付时所传入的交易流水号',
        `out_trade_no` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '我司唯一订单号',
        `out_refund_no` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款订单号',
        `total_fee` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '金额，单位分',
        `pay_type` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付方式，010微信，020 支付宝，030银行卡，060qq钱包，080京东钱包，090口碑,100翼支付，110银联二维码',
        `end_time` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付完成时间，yyyyMMddHHmmss，全局统一时间格式',
        `attach` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '附加数据，原样返回',
        `user_id` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付款方用户id，“微信openid”、“支付宝账户”、“qq号”等',
        `channel_trade_no` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通道订单号，微信订单号、支付宝订单号等',
        `pay_status_code` int(10) DEFAULT NULL COMMENT '支付状态，1支付成功，2支付失败，3支付中，4已撤销，5退款成功， 6退款失败',
        `key_sign` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签名字符串,拼装所有非null参数+令牌，32位md5加密转换',
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;*/
public class PayInfoBean {
    private Integer id;
    private String pay_ver;
    private String card_type;
    private String merchant_no;
    private String merchant_name;
    private String terminal_id;
    private String terminal_time;
    private String terminal_trace;
    private String out_trade_no;
    private String out_refund_no;
    private String total_fee;
    private String pay_type;
    private String end_time;
    private String attach;
    private String user_id;
    private String channel_trade_no;
    private Integer pay_status_code;
    private String key_sign;
    private String store_code;

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getPay_ver() {
        return pay_ver;
    }

    public void setPay_ver(String pay_ver) {
        this.pay_ver = pay_ver;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getTerminal_time() {
        return terminal_time;
    }

    public void setTerminal_time(String terminal_time) {
        this.terminal_time = terminal_time;
    }

    public String getTerminal_trace() {
        return terminal_trace;
    }

    public void setTerminal_trace(String terminal_trace) {
        this.terminal_trace = terminal_trace;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getChannel_trade_no() {
        return channel_trade_no;
    }

    public void setChannel_trade_no(String channel_trade_no) {
        this.channel_trade_no = channel_trade_no;
    }

    public Integer getPay_status_code() {
        return pay_status_code;
    }

    public void setPay_status_code(Integer pay_status_code) {
        this.pay_status_code = pay_status_code;
    }

    public String getKey_sign() {
        return key_sign;
    }

    public void setKey_sign(String key_sign) {
        this.key_sign = key_sign;
    }
}
