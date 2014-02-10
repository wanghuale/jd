/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.enterprise.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.common.utils.excel.annotation.ExcelField;

/**
 * 企业员工Entity
 * 
 * @author qimin
 * @version 2013-10-11
 */
@Entity
@Table(name = "front_reg_userinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Userinfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 注册用户名
     */
    private String loginname;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型（1：个人用户 2：家庭用户 3：企业用户）
     */
    private String userType;

    /**
     * 终端卡卡号
     */
    private String cardCode;

    /**
     * 卡类型 1:个人卡 2：家庭卡 3：企业卡
     */
    private String cardType;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件号码
     */
    private String dentity;

    /**
     * 性别 0:女 1：男
     */
    private String sex;

    /**
     * 形象照片
     */
    private String imgpath;

    /**
     * 形象照片缩略图
     */
    private String thumbimgpath;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobilephone;

    /**
     * qq
     */
    private String qq;

    /**
     * 通讯地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后登陆ip
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * （0：不通过；1：通过）
     */
    private String verify;

    /**
     * 删除标记（0：正常；1：删除）
     */
    private String delFlag;

    /**
     * （0：是；1：不是）是否是企业员工
     */
    private String isemploy;

    /**
     * 企业注册信息表id
     */
    private Enterprise enterprise;

    /**
     * 部门表id号
     */
    private FrontOffice office;

    /**
     * 是否有终端卡
     */
    private String hascard;

    /**
     * 如福利专员
     */
    private String role;
    
    /**
     * 一卡通卡号
     */
    private String cardId;
    /**
     * 注册用户帐户信息表
     */
    // private UserAccount userAccount;

    public Userinfo() {
	super();
    }

    public Userinfo(Long id) {
	this();
	this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "reg_enterpriseid")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @NotNull(message = "归属公司不能为空")
    @ExcelField(title = "归属公司", align = 2, sort = 20)
    public Enterprise getEnterprise() {
	return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
	this.enterprise = enterprise;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeid")
    @NotFound(action = NotFoundAction.IGNORE)
    public FrontOffice getOffice() {
	return office;
    }

    public void setOffice(FrontOffice office) {
	this.office = office;
    }

    @Length(min = 1, max = 50)
    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getLoginname() {
	return loginname;
    }

    public void setLoginname(String loginname) {
	this.loginname = loginname;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Length(min = 0, max = 20)
    public String getNickname() {
	return nickname;
    }

    public void setNickname(String nickname) {
	this.nickname = nickname;
    }

    @Length(min = 1, max = 20)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Length(min = 1, max = 20)
    public String getDentity() {
	return dentity;
    }

    public void setDentity(String dentity) {
	this.dentity = dentity;
    }

    @Length(min = 1, max = 1)
    public String getSex() {
	return sex;
    }

    public void setSex(String sex) {
	this.sex = sex;
    }

    @Length(min = 0, max = 200)
    public String getImgpath() {
	return imgpath;
    }

    public void setImgpath(String imgpath) {
	this.imgpath = imgpath;
    }

    @Length(min = 0, max = 200)
    public String getThumbimgpath() {
	return thumbimgpath;
    }

    public void setThumbimgpath(String thumbimgpath) {
	this.thumbimgpath = thumbimgpath;
    }

    public Date getBirthday() {
	return birthday;
    }

    public void setBirthday(Date birthday) {
	this.birthday = birthday;
    }

    @Length(min = 1, max = 20)
    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    @Length(min = 0, max = 20)
    public String getMobilephone() {
	return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
	this.mobilephone = mobilephone;
    }

    @Length(min = 0, max = 20)
    public String getQq() {
	return qq;
    }

    public void setQq(String qq) {
	this.qq = qq;
    }

    @Length(min = 0, max = 200)
    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    @Length(min = 0, max = 10)
    public String getPostcode() {
	return postcode;
    }

    public void setPostcode(String postcode) {
	this.postcode = postcode;
    }

    @Length(min = 1, max = 1)
    public String getVerify() {
	return verify;
    }

    public void setVerify(String verify) {
	this.verify = verify;
    }

    @Length(min = 0, max = 1)
    public String getIsemploy() {
	return isemploy;
    }

    public void setIsemploy(String isemploy) {
	this.isemploy = isemploy;
    }

    @Length(min = 0, max = 1)
    public String getHascard() {
	return hascard;
    }

    public void setHascard(String hascard) {
	this.hascard = hascard;
    }

    @Length(min = 0, max = 20)
    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public String getCardCode() {
	return cardCode;
    }

    public void setCardCode(String cardCode) {
	this.cardCode = cardCode;
    }

    public String getCardType() {
	return cardType;
    }

    public void setCardType(String cardType) {
	this.cardType = cardType;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public String getLoginIp() {
	return loginIp;
    }

    public void setLoginIp(String loginIp) {
	this.loginIp = loginIp;
    }

    public Date getLoginDate() {
	return loginDate;
    }

    public void setLoginDate(Date loginDate) {
	this.loginDate = loginDate;
    }

    public String getDelFlag() {
	return delFlag;
    }

    public void setDelFlag(String delFlag) {
	this.delFlag = delFlag;
    }

    public String getUserType() {
	return userType;
    }

    public void setUserType(String userType) {
	this.userType = userType;
    }

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

    
    // @ManyToOne
    // @JoinColumn(name = "id")
    // @NotFound(action = NotFoundAction.IGNORE)
    // public UserAccount getUserAccount() {
    // return userAccount;
    // }
    //
    // public void setUserAccount(UserAccount userAccount) {
    // this.userAccount = userAccount;
    // }

}
