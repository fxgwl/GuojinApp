package com.axehome.www.guojinapp.beans;

import java.io.Serializable;

public class User implements Serializable {
    private Integer user_id;
    private Integer user_level;
    private Integer parent_id;
    private String register_time;
    private String update_time;
    private String username;
    private String realname;
    private String nickname;
    private String password;
    private String reg_ip;
    private Integer status;
    private String login_times;
    private String last_login_ip;
    private String last_login_time;
    private String head_img;
    private Integer role_id;
    private String token;
    private Double balance;//余额
    private String pro;
    private String city;
    private String phone;
    private String invitation_code;
    private String openId;
    private String user_type;
    private Integer shop_id;

    private String user_identity;//身份证号
    private String user_address;//常驻地址
    private String ali_name;//支付宝账户名称
    private String ali_num;//支付宝账号
    private String bank_name;//开户行名称
    private String bank_zhi_name;//支行名称
    private String band_card;//银行账号
    private Integer address_id;
    private String identity_zheng;
    private String identity_fan;
    private Double sale_value;
    private Double jiangli_value;
    private Double lirun_value;
    private Double user_jifen;
    private String buy_password;

    private Double value;//暂存
    private AddressBean addressBean;
    private String order_num;
    private String buy_value;
    /**
     * just for mapping
     */
    private RoleBean roleBean;
    private User pUser;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_level() {
        return user_level;
    }

    public void setUser_level(Integer user_level) {
        this.user_level = user_level;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getRegister_time() {
        return register_time == null ? "" : register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getUpdate_time() {
        return update_time == null ? "" : update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname == null ? "" : realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReg_ip() {
        return reg_ip == null ? "" : reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLogin_times() {
        return login_times == null ? "" : login_times;
    }

    public void setLogin_times(String login_times) {
        this.login_times = login_times;
    }

    public String getLast_login_ip() {
        return last_login_ip == null ? "" : last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return last_login_time == null ? "" : last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getHead_img() {
        return head_img == null ? "" : head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPro() {
        return pro == null ? "" : pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInvitation_code() {
        return invitation_code == null ? "" : invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getOpenId() {
        return openId == null ? "" : openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUser_type() {
        return user_type == null ? "" : user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public String getUser_identity() {
        return user_identity == null ? "" : user_identity;
    }

    public void setUser_identity(String user_identity) {
        this.user_identity = user_identity;
    }

    public String getUser_address() {
        return user_address == null ? "" : user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getAli_name() {
        return ali_name == null ? "" : ali_name;
    }

    public void setAli_name(String ali_name) {
        this.ali_name = ali_name;
    }

    public String getAli_num() {
        return ali_num == null ? "" : ali_num;
    }

    public void setAli_num(String ali_num) {
        this.ali_num = ali_num;
    }

    public String getBank_name() {
        return bank_name == null ? "" : bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_zhi_name() {
        return bank_zhi_name == null ? "" : bank_zhi_name;
    }

    public void setBank_zhi_name(String bank_zhi_name) {
        this.bank_zhi_name = bank_zhi_name;
    }

    public String getBand_card() {
        return band_card == null ? "" : band_card;
    }

    public void setBand_card(String band_card) {
        this.band_card = band_card;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public String getIdentity_zheng() {
        return identity_zheng == null ? "" : identity_zheng;
    }

    public void setIdentity_zheng(String identity_zheng) {
        this.identity_zheng = identity_zheng;
    }

    public String getIdentity_fan() {
        return identity_fan == null ? "" : identity_fan;
    }

    public void setIdentity_fan(String identity_fan) {
        this.identity_fan = identity_fan;
    }

    public Double getSale_value() {
        return sale_value;
    }

    public void setSale_value(Double sale_value) {
        this.sale_value = sale_value;
    }

    public Double getJiangli_value() {
        return jiangli_value;
    }

    public void setJiangli_value(Double jiangli_value) {
        this.jiangli_value = jiangli_value;
    }

    public Double getLirun_value() {
        return lirun_value;
    }

    public void setLirun_value(Double lirun_value) {
        this.lirun_value = lirun_value;
    }

    public Double getUser_jifen() {
        return user_jifen;
    }

    public void setUser_jifen(Double user_jifen) {
        this.user_jifen = user_jifen;
    }

    public String getBuy_password() {
        return buy_password == null ? "" : buy_password;
    }

    public void setBuy_password(String buy_password) {
        this.buy_password = buy_password;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public AddressBean getAddressBean() {
        return addressBean;
    }

    public void setAddressBean(AddressBean addressBean) {
        this.addressBean = addressBean;
    }

    public String getOrder_num() {
        return order_num == null ? "" : order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getBuy_value() {
        return buy_value == null ? "" : buy_value;
    }

    public void setBuy_value(String buy_value) {
        this.buy_value = buy_value;
    }

    public RoleBean getRoleBean() {
        return roleBean;
    }

    public void setRoleBean(RoleBean roleBean) {
        this.roleBean = roleBean;
    }

    public User getpUser() {
        return pUser;
    }

    public void setpUser(User pUser) {
        this.pUser = pUser;
    }
}
