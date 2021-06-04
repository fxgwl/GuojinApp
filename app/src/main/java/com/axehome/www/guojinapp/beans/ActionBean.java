package com.axehome.www.guojinapp.beans;

public class ActionBean {
    /*CREATE TABLE `action` (
            `action_id` int(11) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(2) DEFAULT NULL COMMENT '活动类型 1：支付有礼；2：智慧商圈；3：附近发券；4：花呗分期',
            `action_status` varchar(2) DEFAULT NULL,
  `action_setup` varchar(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
            `action_name` varchar(128) DEFAULT NULL COMMENT '活动名称',
            `action_start` varchar(20) DEFAULT NULL COMMENT '活动开始时间',
            `action_end` varchar(20) DEFAULT NULL COMMENT '活动结束时间',
            `action_quan_name` varchar(128) DEFAULT NULL COMMENT '券名称',
            `action_pinpai_name` varchar(128) DEFAULT NULL COMMENT '品牌名称',
            `action_value_leve` varchar(128) DEFAULT NULL COMMENT '门槛金额',
            `action_rule` varchar(128) DEFAULT NULL COMMENT '优惠规则',
            `action_quan_xian_zhi` varchar(128) DEFAULT NULL COMMENT '优惠券使用限制',
            `action_quan_num` varchar(128) DEFAULT NULL COMMENT '优惠券数量',
            `action_quan_start` varchar(20) DEFAULT NULL COMMENT '优惠券生效时间',
            `action_quan_end` varchar(20) DEFAULT NULL COMMENT '优惠券结束时间',
            `action_quan_ling_yong` varchar(128) DEFAULT NULL COMMENT '领用限制',
            `action_quan_detail` varchar(128) DEFAULT NULL COMMENT '使用说明',
            `action_logo` varchar(255) DEFAULT NULL COMMENT '商户logo',
            `action_tiexi_value` varchar(128) DEFAULT NULL COMMENT '贴息定单金额区间',
            `action_tiexi_feilv` varchar(128) DEFAULT NULL COMMENT '定单商户贴息费率',
            `action_yusuan_value` varchar(128) DEFAULT NULL COMMENT '预算预警金额',
            `action_yusuan_tel` varchar(20) DEFAULT NULL COMMENT '预算手机号',
            `action_quan_man` varchar(128) DEFAULT NULL,
  `action_quan_jian` varchar(128) DEFAULT NULL,
  `action_quan_store_name` varchar(128) DEFAULT NULL COMMENT '推广门店',
            `action_quan_sex` varchar(2) DEFAULT NULL COMMENT '推广姓别',
            `action_age_start` varchar(10) DEFAULT NULL COMMENT '推广起始年龄',
            `action_age_end` varchar(10) DEFAULT NULL COMMENT '推广结止年龄',
    PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='智慧经营 活动';*/
    private Integer action_id;

    private String action_type;

    private String action_status;

    private String action_setup;

    private Integer user_id;

    private String action_name;

    private String action_start;

    private String action_end;

    private String action_quan_name;

    private String action_pinpai_name;

    private String action_value_leve;

    private String action_rule;

    private String action_quan_user_ruler;

    private String action_quan_num;

    private String action_quan_start;

    private String action_quan_end;

    private String action_quan_ling_yong;

    private String action_quan_detail;

    private String action_logo;

    private String action_tiexi_value_s;

    private String action_tiexi_value_b;


    private String action_tiexi_feilv;

    private String action_yusuan_value;

    private String action_yusuan_tel;

    private String action_quan_man;

    private String action_quan_jian;

    private String action_quan_store_name;

    private String action_quan_sex;

    private String action_age_start;

    private String action_age_end;

    public Integer getAction_id() {
        return action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type == null ? null : action_type.trim();
    }

    public String getAction_status() {
        return action_status;
    }

    public void setAction_status(String action_status) {
        this.action_status = action_status == null ? null : action_status.trim();
    }

    public String getAction_setup() {
        return action_setup;
    }

    public void setAction_setup(String action_setup) {
        this.action_setup = action_setup == null ? null : action_setup.trim();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name == null ? null : action_name.trim();
    }

    public String getAction_start() {
        return action_start;
    }

    public void setAction_start(String action_start) {
        this.action_start = action_start == null ? null : action_start.trim();
    }

    public String getAction_end() {
        return action_end;
    }

    public void setAction_end(String action_end) {
        this.action_end = action_end == null ? null : action_end.trim();
    }

    public String getAction_quan_name() {
        return action_quan_name;
    }

    public void setAction_quan_name(String action_quan_name) {
        this.action_quan_name = action_quan_name == null ? null : action_quan_name.trim();
    }

    public String getAction_pinpai_name() {
        return action_pinpai_name;
    }

    public void setAction_pinpai_name(String action_pinpai_name) {
        this.action_pinpai_name = action_pinpai_name == null ? null : action_pinpai_name.trim();
    }

    public String getAction_value_leve() {
        return action_value_leve;
    }

    public void setAction_value_leve(String action_value_leve) {
        this.action_value_leve = action_value_leve == null ? null : action_value_leve.trim();
    }

    public String getAction_rule() {
        return action_rule;
    }

    public void setAction_rule(String action_rule) {
        this.action_rule = action_rule == null ? null : action_rule.trim();
    }

    public String getAction_quan_user_ruler() {
        return action_quan_user_ruler;
    }

    public void setAction_quan_user_ruler(String action_quan_user_ruler) {
        this.action_quan_user_ruler = action_quan_user_ruler == null ? null : action_quan_user_ruler.trim();
    }

    public String getAction_quan_num() {
        return action_quan_num;
    }

    public void setAction_quan_num(String action_quan_num) {
        this.action_quan_num = action_quan_num == null ? null : action_quan_num.trim();
    }

    public String getAction_quan_start() {
        return action_quan_start;
    }

    public void setAction_quan_start(String action_quan_start) {
        this.action_quan_start = action_quan_start == null ? null : action_quan_start.trim();
    }

    public String getAction_quan_end() {
        return action_quan_end;
    }

    public void setAction_quan_end(String action_quan_end) {
        this.action_quan_end = action_quan_end == null ? null : action_quan_end.trim();
    }

    public String getAction_quan_ling_yong() {
        return action_quan_ling_yong;
    }

    public void setAction_quan_ling_yong(String action_quan_ling_yong) {
        this.action_quan_ling_yong = action_quan_ling_yong == null ? null : action_quan_ling_yong.trim();
    }

    public String getAction_quan_detail() {
        return action_quan_detail;
    }

    public void setAction_quan_detail(String action_quan_detail) {
        this.action_quan_detail = action_quan_detail == null ? null : action_quan_detail.trim();
    }

    public String getAction_logo() {
        return action_logo;
    }

    public void setAction_logo(String action_logo) {
        this.action_logo = action_logo == null ? null : action_logo.trim();
    }

    public String getAction_tiexi_value_s() {
        return action_tiexi_value_s == null ? "" : action_tiexi_value_s;
    }

    public void setAction_tiexi_value_s(String action_tiexi_value_s) {
        this.action_tiexi_value_s = action_tiexi_value_s;
    }

    public String getAction_tiexi_value_b() {
        return action_tiexi_value_b == null ? "" : action_tiexi_value_b;
    }

    public void setAction_tiexi_value_b(String action_tiexi_value_b) {
        this.action_tiexi_value_b = action_tiexi_value_b;
    }

    public String getAction_tiexi_feilv() {
        return action_tiexi_feilv;
    }

    public void setAction_tiexi_feilv(String action_tiexi_feilv) {
        this.action_tiexi_feilv = action_tiexi_feilv == null ? null : action_tiexi_feilv.trim();
    }

    public String getAction_yusuan_value() {
        return action_yusuan_value;
    }

    public void setAction_yusuan_value(String action_yusuan_value) {
        this.action_yusuan_value = action_yusuan_value == null ? null : action_yusuan_value.trim();
    }

    public String getAction_yusuan_tel() {
        return action_yusuan_tel;
    }

    public void setAction_yusuan_tel(String action_yusuan_tel) {
        this.action_yusuan_tel = action_yusuan_tel == null ? null : action_yusuan_tel.trim();
    }

    public String getAction_quan_man() {
        return action_quan_man;
    }

    public void setAction_quan_man(String action_quan_man) {
        this.action_quan_man = action_quan_man == null ? null : action_quan_man.trim();
    }

    public String getAction_quan_jian() {
        return action_quan_jian;
    }

    public void setAction_quan_jian(String action_quan_jian) {
        this.action_quan_jian = action_quan_jian == null ? null : action_quan_jian.trim();
    }

    public String getAction_quan_store_name() {
        return action_quan_store_name;
    }

    public void setAction_quan_store_name(String action_quan_store_name) {
        this.action_quan_store_name = action_quan_store_name == null ? null : action_quan_store_name.trim();
    }

    public String getAction_quan_sex() {
        return action_quan_sex;
    }

    public void setAction_quan_sex(String action_quan_sex) {
        this.action_quan_sex = action_quan_sex == null ? null : action_quan_sex.trim();
    }

    public String getAction_age_start() {
        return action_age_start;
    }

    public void setAction_age_start(String action_age_start) {
        this.action_age_start = action_age_start == null ? null : action_age_start.trim();
    }

    public String getAction_age_end() {
        return action_age_end;
    }

    public void setAction_age_end(String action_age_end) {
        this.action_age_end = action_age_end == null ? null : action_age_end.trim();
    }
}