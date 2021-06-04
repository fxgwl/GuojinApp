package com.axehome.www.guojinapp.beans;


/**
 * @Author: jaywechen
 * @Description: 商户信息
 * @Created by: IntelliJ IDEA
 * @Modified By: jaywechen
 * @Date: 16:11 2018/1/20
 */
public class ShopBean {

    /*CREATE TABLE `shop_info` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_img` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_fl` double(10,4) DEFAULT NULL COMMENT '实际进件费率',
  `shop_pic1` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_pic2` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_pic3` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_pic4` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `b_user_id` int(11) DEFAULT NULL COMMENT '所属id',
  `setup_time` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shop_state` int(1) DEFAULT NULL,
  `shop_num` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `inst_no` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构编号，我司分配',
  `trace_no` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求流水号，不带“-”的uuid',
  `merchant_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户名称，我司系统全局唯一不可重复，最多50个汉字且不能包含特殊符号参考验重接口',
  `merchant_alias` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户简称，最多15个汉字且不能包含特殊符号',
  `merchant_company` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户注册名称/公司全称，须与营业执照名称保持一致，最多30个汉字且不能包含特殊符号',
  `merchant_province` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在省',
  `merchant_province_code` varchar(3) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省编码',
  `merchant_city` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在市',
  `merchant_city_code` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '市编码',
  `merchant_county` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在区县',
  `merchant_county_code` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在区县编码',
  `merchant_address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户详细地址',
  `merchant_person` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户联系人姓名',
  `merchant_phone` varchar(13) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户联系人电话（唯一）',
  `merchant_email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户联系人邮箱（唯一）',
  `merchant_service_phone` varchar(13) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客服电话',
  `daily_timely_status` int(10) DEFAULT NULL COMMENT 'D1状态,0不开通，1开通',
  `daily_timely_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'D1手续费代码,取值范围见下面D1费率表',
  `business_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '行业类目名称',
  `business_code` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '行业类目编码，由我司技术支持提供表格',
  `merchant_business_type` int(2) DEFAULT NULL COMMENT '商户类型:1企业，2个体工商户，3小微商户',
  `account_type` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账户类型，1对公，2对私',
  `settlement_type` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算类型:1.法人结算 2.非法人结算',
  `license_type` int(2) DEFAULT NULL COMMENT '营业证件类型：0营业执照，1三证合一，2手持身份证',
  `license_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '营业证件号码',
  `license_expire` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '营业证件到期日（格式YYYY-MM-DD）',
  `artif_nm` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法人名称',
  `legalIdnum` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法人身份证号',
  `legalIdnumExpire` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法人身份证有效期（格式YYYY-MM-DD）',
  `merchant_id_no` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算人身份证号码',
  `merchant_id_expire` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算人身份证有效期，格式YYYYMMDD，长期填写29991231',
  `account_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账银行卡开户名（结算人姓名/公司名）',
  `account_no` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账银行卡卡号',
  `account_phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账银行预留手机号',
  `bank_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账银行卡开户支行',
  `bank_no` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开户支行联行号，由我司技术支持提供表格',
  `company_account_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对公户结算账户开户名',
  `company_account_no` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对公户结算账户开户号',
  `company_bank_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对公户结算账户开户支行',
  `company_bank_no` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对公户结算账户开户支行联行号，由我司技术支持提供表格',
  `no_credit` int(2) DEFAULT NULL COMMENT '限制信用卡使用,0不限制，1限制',
  `settle_type` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '清算类型：1自动结算；2手动结算，',
  `settle_amount` int(11) DEFAULT NULL COMMENT '自动清算金额（单位分），清算类型为自动清算时有效，指帐户余额达到此值才清算。注：当前固定值为1分',
  `rate_code` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付费率代码，默认千分之六，取值范围见下面支付费率表',
  `greenstatus` int(2) DEFAULT NULL COMMENT '是否绿洲商户：0非绿洲，1开通绿洲',
  `blueseastatus` int(2) DEFAULT NULL COMMENT '是否蓝海商户：0非蓝海 1开通蓝海',
  `img_license` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '营业执照照片',
  `img_idcard_a` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法人身份证正面照片',
  `img_idcard_b` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法人身份证反面照片',
  `img_bankcard_a` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账银行卡正面照片',
  `img_bankcard_b` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账银行卡反面照片',
  `img_logo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户门头照片',
  `img_indoor` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内部前台照片',
  `img_contract` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店内环境照片',
  `img_other` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '其他证明材料',
  `img_idcard_holding` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '本人手持身份证照片',
  `img_open_permits` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开户许可证照片',
  `img_org_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构代码证照片',
  `img_tax_reg` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '税务登记证照片',
  `img_unincorporated` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入账非法人证明照片',
  `img_private_idcard_a` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对私账户身份证正面照片',
  `img_private_idcard_b` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对私账户身份证反面照片',
  `img_standard_protocol` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户总分店关系证明',
  `img_val_add_protocol` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户增值协议照片',
  `img_sub_account_promiss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分账承诺函',
  `img_cashier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信支付物料照片',
  `img_3rd_part` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '第三方平台截图',
  `img_alicashier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付宝支付物料照片',
  `img_salesman_logo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务员门头合照',
  `img_green_annex` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绿洲活动补充材料',
  `img_green_food_hygiene_permit` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绿洲食品卫生许可证',
  `img_green_promiss` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绿洲承诺函',
  `notify_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核状态通知地址',
  `wx_appid` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信公众号appid，普通商户公众号支付使用',
  `wx_appsecret` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信公众号appsecret，普通商户公众号支付使用',
  `key_sign` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签名检验串,拼装所有非空参数+令牌，32位md5加密转换',
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商户表';*/


    private Integer shop_id;
    private Integer b_user_id;
    private String shop_name;
    private String shop_address;
    private String shop_img;
    private Double shop_fl;
    private String shop_pic1;
    private String shop_pic2;
    private String shop_pic3;
    private String shop_pic4;
    private String setup_time;
    private Integer shop_state;
    private String shop_num;
    private Integer shop_del;

    private String inst_no;//机构编号，我司分配
    private String trace_no;//请求流水号，不带“-”的uuid
    private String merchant_no;
    private String merchant_name;
    private String merchant_alias;//商户简称，最多15个汉字且不能包含特殊符号
    private String merchant_company;
    private String merchant_province;
    private String merchant_province_code;//省编码
    private String merchant_city;//所在市
    private String merchant_city_code;//市编码
    private String merchant_county;//所在区县
    private String merchant_county_code;//所在区县编码
    private String merchant_address;//商户详细地址
    private String merchant_person;//商户联系人姓名
    private String merchant_phone;//商户联系人电话（唯一）
    private String merchant_email;//商户联系人邮箱（唯一）
    private String img_merchant_person_idcard;//联系人身份证照片（正面）
    private String merchant_service_phone;//客服电话
    private Integer daily_timely_status;//D1状态,0不开通，1开通
    private String daily_timely_code;//D1手续费代码,取值范围见下面D1费率表
    private String business_name;//行业类目名称
    private String business_code;//行业类目编码，由我司技术支持提供表格
    private Integer merchant_business_type;//商户类型:1企业，2个体工商户，3小微商户
    private String account_type;//账户类型，1对公，2对私
    private String settlement_type;//结算类型:1.法人结算 2.非法人结算
    private Integer license_type;//营业证件类型：0营业执照，1三证合一，2手持身份证
    private String license_no;//营业证件号码
    private String license_expire;//营业证件到期日（格式YYYY-MM-DD）
    private String artif_nm;//法人名称
    private String legalIdnum;//法人身份证号
    private String legalIdnumExpire;//法人身份证有效期（格式YYYY-MM-DD）
    private String merchant_id_no;//结算人身份证号码
    private String merchant_id_expire;//结算人身份证有效期，格式YYYYMMDD，长期填写29991231
    private String account_name;//入账银行卡开户名（结算人姓名/公司名）
    private String account_no;//入账银行卡卡号
    private String account_phone;//入账银行预留手机号
    private String bank_name;//入账银行卡开户支行
    private String bank_no;//开户支行联行号，由我司技术支持提供表格
    private String company_account_name;//对公户结算账户开户名
    private String company_account_no;//对公户结算账户开户号
    private String company_bank_name;//对公户结算账户开户支行
    private String company_bank_no;//对公户结算账户开户支行联行号，由我司技术支持提供表格
    private Integer no_credit;//限制信用卡使用,0不限制，1限制
    private String settle_type;//清算类型：1自动结算；2手动结算，
    private Integer settle_amount;//自动清算金额（单位分），清算类型为自动清算时有效，指帐户余额达到此值才清算。注：当前固定值为1分
    private String rate_code;//支付费率代码，默认千分之六，取值范围见下面支付费率表
    private Integer greenstatus;//是否绿洲商户：0非绿洲，1开通绿洲
    private Integer blueseastatus;//是否蓝海商户：0非蓝海 1开通蓝海
    private String img_license;//营业执照照片
    private String img_idcard_a;//法人身份证正面照片
    private String img_idcard_b;//法人身份证反面照片
    private String img_bankcard_a;//入账银行卡正面照片
    private String img_bankcard_b;//入账银行卡反面照片
    private String img_logo;//商户门头照片
    private String img_indoor;//内部前台照片
    private String img_contract;//店内环境照片
    private String img_other;//其他证明材料
    private String img_idcard_holding;//本人手持身份证照片
    private String img_open_permits;//开户许可证照片
    private String img_org_code;//组织机构代码证照片
    private String img_tax_reg;//税务登记证照片
    private String img_unincorporated;//入账非法人证明照片
    private String img_private_idcard_a;//对私账户身份证正面照片
    private String img_private_idcard_b;//对私账户身份证反面照片
    private String img_standard_protocol;//商户总分店关系证明
    private String img_val_add_protocol;//商户增值协议照片
    private String img_sub_account_promiss;//分账承诺函
    private String img_cashier;//微信支付物料照片
    private String img_3rd_part;//第三方平台截图
    private String img_alicashier;//支付宝支付物料照片
    private String img_salesman_logo;//业务员门头合照
    private String img_green_annex;//绿洲活动补充材料
    private String img_green_food_hygiene_permit;//绿洲食品卫生许可证
    private String img_green_promiss;//绿洲承诺函
    private String notify_url;//审核状态通知地址
    private String wx_appid;//微信公众号appid，普通商户公众号支付使用
    private String wx_appsecret;//微信公众号appsecret，普通商户公众号支付使用
    private String key_sign;//签名检验串,拼装所有非空参数+令牌，32位md5加密转换

    private String khh_name;
    private String khdz;
    private String khdz_gong;
    private String getKhh_name_gong;




    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public Integer getB_user_id() {
        return b_user_id;
    }

    public void setB_user_id(Integer b_user_id) {
        this.b_user_id = b_user_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public Double getShop_fl() {
        return shop_fl;
    }

    public void setShop_fl(Double shop_fl) {
        this.shop_fl = shop_fl;
    }

    public String getShop_pic1() {
        return shop_pic1;
    }

    public void setShop_pic1(String shop_pic1) {
        this.shop_pic1 = shop_pic1;
    }

    public String getShop_pic2() {
        return shop_pic2;
    }

    public void setShop_pic2(String shop_pic2) {
        this.shop_pic2 = shop_pic2;
    }

    public String getShop_pic3() {
        return shop_pic3;
    }

    public void setShop_pic3(String shop_pic3) {
        this.shop_pic3 = shop_pic3;
    }

    public String getShop_pic4() {
        return shop_pic4;
    }

    public void setShop_pic4(String shop_pic4) {
        this.shop_pic4 = shop_pic4;
    }

    public String getSetup_time() {
        return setup_time;
    }

    public void setSetup_time(String setup_time) {
        this.setup_time = setup_time;
    }

    public Integer getShop_state() {
        return shop_state;
    }

    public void setShop_state(Integer shop_state) {
        this.shop_state = shop_state;
    }

    public String getShop_num() {
        return shop_num;
    }

    public void setShop_num(String shop_num) {
        this.shop_num = shop_num;
    }

    public Integer getShop_del() {
        return shop_del;
    }

    public void setShop_del(Integer shop_del) {
        this.shop_del = shop_del;
    }


    public String getInst_no() {
        return inst_no;
    }

    public void setInst_no(String inst_no) {
        this.inst_no = inst_no;
    }

    public String getTrace_no() {
        return trace_no;
    }

    public void setTrace_no(String trace_no) {
        this.trace_no = trace_no;
    }

    public String getMerchant_no() {
        return merchant_no == null ? "" : merchant_no;
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

    public String getMerchant_alias() {
        return merchant_alias;
    }

    public void setMerchant_alias(String merchant_alias) {
        this.merchant_alias = merchant_alias;
    }

    public String getMerchant_company() {
        return merchant_company;
    }

    public void setMerchant_company(String merchant_company) {
        this.merchant_company = merchant_company;
    }

    public String getMerchant_province() {
        return merchant_province;
    }

    public void setMerchant_province(String merchant_province) {
        this.merchant_province = merchant_province;
    }

    public String getMerchant_province_code() {
        return merchant_province_code;
    }

    public void setMerchant_province_code(String merchant_province_code) {
        this.merchant_province_code = merchant_province_code;
    }

    public String getMerchant_city() {
        return merchant_city;
    }

    public void setMerchant_city(String merchant_city) {
        this.merchant_city = merchant_city;
    }

    public String getMerchant_city_code() {
        return merchant_city_code;
    }

    public void setMerchant_city_code(String merchant_city_code) {
        this.merchant_city_code = merchant_city_code;
    }

    public String getMerchant_county() {
        return merchant_county;
    }

    public void setMerchant_county(String merchant_county) {
        this.merchant_county = merchant_county;
    }

    public String getMerchant_county_code() {
        return merchant_county_code;
    }

    public void setMerchant_county_code(String merchant_county_code) {
        this.merchant_county_code = merchant_county_code;
    }

    public String getMerchant_address() {
        return merchant_address;
    }

    public void setMerchant_address(String merchant_address) {
        this.merchant_address = merchant_address;
    }

    public String getMerchant_person() {
        return merchant_person;
    }

    public void setMerchant_person(String merchant_person) {
        this.merchant_person = merchant_person;
    }

    public String getMerchant_phone() {
        return merchant_phone;
    }

    public void setMerchant_phone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }

    public String getMerchant_email() {
        return merchant_email;
    }

    public void setMerchant_email(String merchant_email) {
        this.merchant_email = merchant_email;
    }

    public String getMerchant_service_phone() {
        return merchant_service_phone;
    }

    public void setMerchant_service_phone(String merchant_service_phone) {
        this.merchant_service_phone = merchant_service_phone;
    }

    public Integer getDaily_timely_status() {
        return daily_timely_status;
    }

    public void setDaily_timely_status(Integer daily_timely_status) {
        this.daily_timely_status = daily_timely_status;
    }

    public String getDaily_timely_code() {
        return daily_timely_code;
    }

    public void setDaily_timely_code(String daily_timely_code) {
        this.daily_timely_code = daily_timely_code;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public Integer getMerchant_business_type() {
        return merchant_business_type;
    }

    public void setMerchant_business_type(Integer merchant_business_type) {
        this.merchant_business_type = merchant_business_type;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getSettlement_type() {
        return settlement_type;
    }

    public void setSettlement_type(String settlement_type) {
        this.settlement_type = settlement_type;
    }

    public Integer getLicense_type() {
        return license_type;
    }

    public void setLicense_type(Integer license_type) {
        this.license_type = license_type;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getLicense_expire() {
        return license_expire;
    }

    public void setLicense_expire(String license_expire) {
        this.license_expire = license_expire;
    }

    public String getArtif_nm() {
        return artif_nm;
    }

    public void setArtif_nm(String artif_nm) {
        this.artif_nm = artif_nm;
    }

    public String getLegalIdnum() {
        return legalIdnum;
    }

    public void setLegalIdnum(String legalIdnum) {
        this.legalIdnum = legalIdnum;
    }

    public String getLegalIdnumExpire() {
        return legalIdnumExpire;
    }

    public void setLegalIdnumExpire(String legalIdnumExpire) {
        this.legalIdnumExpire = legalIdnumExpire;
    }

    public String getMerchant_id_no() {
        return merchant_id_no;
    }

    public void setMerchant_id_no(String merchant_id_no) {
        this.merchant_id_no = merchant_id_no;
    }

    public String getMerchant_id_expire() {
        return merchant_id_expire;
    }

    public void setMerchant_id_expire(String merchant_id_expire) {
        this.merchant_id_expire = merchant_id_expire;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getAccount_phone() {
        return account_phone;
    }

    public void setAccount_phone(String account_phone) {
        this.account_phone = account_phone;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getCompany_account_name() {
        return company_account_name;
    }

    public void setCompany_account_name(String company_account_name) {
        this.company_account_name = company_account_name;
    }

    public String getCompany_account_no() {
        return company_account_no;
    }

    public void setCompany_account_no(String company_account_no) {
        this.company_account_no = company_account_no;
    }

    public String getCompany_bank_name() {
        return company_bank_name;
    }

    public void setCompany_bank_name(String company_bank_name) {
        this.company_bank_name = company_bank_name;
    }

    public String getCompany_bank_no() {
        return company_bank_no;
    }

    public void setCompany_bank_no(String company_bank_no) {
        this.company_bank_no = company_bank_no;
    }

    public Integer getNo_credit() {
        return no_credit;
    }

    public void setNo_credit(Integer no_credit) {
        this.no_credit = no_credit;
    }

    public String getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(String settle_type) {
        this.settle_type = settle_type;
    }

    public Integer getSettle_amount() {
        return settle_amount;
    }

    public void setSettle_amount(Integer settle_amount) {
        this.settle_amount = settle_amount;
    }

    public String getRate_code() {
        return rate_code;
    }

    public void setRate_code(String rate_code) {
        this.rate_code = rate_code;
    }

    public Integer getGreenstatus() {
        return greenstatus;
    }

    public void setGreenstatus(Integer greenstatus) {
        this.greenstatus = greenstatus;
    }

    public Integer getBlueseastatus() {
        return blueseastatus;
    }

    public void setBlueseastatus(Integer blueseastatus) {
        this.blueseastatus = blueseastatus;
    }

    public String getImg_license() {
        return img_license;
    }

    public void setImg_license(String img_license) {
        this.img_license = img_license;
    }

    public String getImg_idcard_a() {
        return img_idcard_a;
    }

    public void setImg_idcard_a(String img_idcard_a) {
        this.img_idcard_a = img_idcard_a;
    }

    public String getImg_idcard_b() {
        return img_idcard_b;
    }

    public void setImg_idcard_b(String img_idcard_b) {
        this.img_idcard_b = img_idcard_b;
    }

    public String getImg_bankcard_a() {
        return img_bankcard_a;
    }

    public void setImg_bankcard_a(String img_bankcard_a) {
        this.img_bankcard_a = img_bankcard_a;
    }

    public String getImg_bankcard_b() {
        return img_bankcard_b;
    }

    public void setImg_bankcard_b(String img_bankcard_b) {
        this.img_bankcard_b = img_bankcard_b;
    }

    public String getImg_logo() {
        return img_logo;
    }

    public void setImg_logo(String img_logo) {
        this.img_logo = img_logo;
    }

    public String getImg_indoor() {
        return img_indoor;
    }

    public void setImg_indoor(String img_indoor) {
        this.img_indoor = img_indoor;
    }

    public String getImg_contract() {
        return img_contract;
    }

    public void setImg_contract(String img_contract) {
        this.img_contract = img_contract;
    }

    public String getImg_other() {
        return img_other;
    }

    public void setImg_other(String img_other) {
        this.img_other = img_other;
    }

    public String getImg_idcard_holding() {
        return img_idcard_holding;
    }

    public void setImg_idcard_holding(String img_idcard_holding) {
        this.img_idcard_holding = img_idcard_holding;
    }

    public String getImg_open_permits() {
        return img_open_permits;
    }

    public void setImg_open_permits(String img_open_permits) {
        this.img_open_permits = img_open_permits;
    }

    public String getImg_org_code() {
        return img_org_code;
    }

    public void setImg_org_code(String img_org_code) {
        this.img_org_code = img_org_code;
    }

    public String getImg_tax_reg() {
        return img_tax_reg;
    }

    public void setImg_tax_reg(String img_tax_reg) {
        this.img_tax_reg = img_tax_reg;
    }

    public String getImg_unincorporated() {
        return img_unincorporated;
    }

    public void setImg_unincorporated(String img_unincorporated) {
        this.img_unincorporated = img_unincorporated;
    }

    public String getImg_private_idcard_a() {
        return img_private_idcard_a;
    }

    public void setImg_private_idcard_a(String img_private_idcard_a) {
        this.img_private_idcard_a = img_private_idcard_a;
    }

    public String getImg_private_idcard_b() {
        return img_private_idcard_b;
    }

    public void setImg_private_idcard_b(String img_private_idcard_b) {
        this.img_private_idcard_b = img_private_idcard_b;
    }

    public String getImg_standard_protocol() {
        return img_standard_protocol;
    }

    public void setImg_standard_protocol(String img_standard_protocol) {
        this.img_standard_protocol = img_standard_protocol;
    }

    public String getImg_val_add_protocol() {
        return img_val_add_protocol;
    }

    public void setImg_val_add_protocol(String img_val_add_protocol) {
        this.img_val_add_protocol = img_val_add_protocol;
    }

    public String getImg_sub_account_promiss() {
        return img_sub_account_promiss;
    }

    public void setImg_sub_account_promiss(String img_sub_account_promiss) {
        this.img_sub_account_promiss = img_sub_account_promiss;
    }

    public String getImg_cashier() {
        return img_cashier;
    }

    public void setImg_cashier(String img_cashier) {
        this.img_cashier = img_cashier;
    }

    public String getImg_3rd_part() {
        return img_3rd_part;
    }

    public void setImg_3rd_part(String img_3rd_part) {
        this.img_3rd_part = img_3rd_part;
    }

    public String getImg_alicashier() {
        return img_alicashier;
    }

    public void setImg_alicashier(String img_alicashier) {
        this.img_alicashier = img_alicashier;
    }

    public String getImg_salesman_logo() {
        return img_salesman_logo;
    }

    public void setImg_salesman_logo(String img_salesman_logo) {
        this.img_salesman_logo = img_salesman_logo;
    }

    public String getImg_green_annex() {
        return img_green_annex;
    }

    public void setImg_green_annex(String img_green_annex) {
        this.img_green_annex = img_green_annex;
    }

    public String getImg_green_food_hygiene_permit() {
        return img_green_food_hygiene_permit;
    }

    public void setImg_green_food_hygiene_permit(String img_green_food_hygiene_permit) {
        this.img_green_food_hygiene_permit = img_green_food_hygiene_permit;
    }

    public String getImg_green_promiss() {
        return img_green_promiss;
    }

    public void setImg_green_promiss(String img_green_promiss) {
        this.img_green_promiss = img_green_promiss;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getWx_appid() {
        return wx_appid;
    }

    public void setWx_appid(String wx_appid) {
        this.wx_appid = wx_appid;
    }

    public String getWx_appsecret() {
        return wx_appsecret;
    }

    public void setWx_appsecret(String wx_appsecret) {
        this.wx_appsecret = wx_appsecret;
    }

    public String getKey_sign() {
        return key_sign;
    }

    public void setKey_sign(String key_sign) {
        this.key_sign = key_sign;
    }

    public String getImg_merchant_person_idcard() {
        return img_merchant_person_idcard == null ? "" : img_merchant_person_idcard;
    }

    public void setImg_merchant_person_idcard(String img_merchant_person_idcard) {
        this.img_merchant_person_idcard = img_merchant_person_idcard;
    }

    public String getKhh_name() {
        return khh_name == null ? "" : khh_name;
    }

    public void setKhh_name(String khh_name) {
        this.khh_name = khh_name;
    }

    public String getKhdz() {
        return khdz == null ? "" : khdz;
    }

    public void setKhdz(String khdz) {
        this.khdz = khdz;
    }

    public String getKhdz_gong() {
        return khdz_gong == null ? "" : khdz_gong;
    }

    public void setKhdz_gong(String khdz_gong) {
        this.khdz_gong = khdz_gong;
    }

    public String getGetKhh_name_gong() {
        return getKhh_name_gong == null ? "" : getKhh_name_gong;
    }

    public void setGetKhh_name_gong(String getKhh_name_gong) {
        this.getKhh_name_gong = getKhh_name_gong;
    }
}
