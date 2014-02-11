package com.whty.platform.modules.enterprise.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.common.utils.excel.annotation.ExcelField;

/**
 * 企业注册信息表
 * @author jincheng
 * @version 2013-10-10
 */
@Entity
@Table(name = "front_reg_enterprise")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enterprise extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;		// 编号
	private String name;	// 企业名称
	private String address;	// 企业地址
	private String email;   // 企业邮箱
	private String tel;     // 联系电话
	private String linkname;// 联系人姓名
	private String linktel;	// 联系人电话
	private String linkindetitytype;// 联系人证件类型
	private String linkIDCard;	// 联系人证件号码
	private String lawername;	// 法人姓名
	private String idcard;	// 法人身份证
	private String licenseCard; //营业执照号码
	private String checkstatus; //审核状态
	private String suggest;  //审核意见
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLinkname() {
		return linkname;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	public String getLinktel() {
		return linktel;
	}
	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}
	public String getLinkindetitytype() {
		return linkindetitytype;
	}
	public void setLinkindetitytype(String linkindetitytype) {
		this.linkindetitytype = linkindetitytype;
	}
	public String getLinkIDCard() {
		return linkIDCard;
	}
	public void setLinkIDCard(String linkIDCard) {
		this.linkIDCard = linkIDCard;
	}
	public String getLawername() {
		return lawername;
	}
	public void setLawername(String lawername) {
		this.lawername = lawername;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getLicenseCard() {
		return licenseCard;
	}
	public void setLicenseCard(String licenseCard) {
		this.licenseCard = licenseCard;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	
	
}
