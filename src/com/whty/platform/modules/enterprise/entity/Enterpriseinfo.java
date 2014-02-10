package com.whty.platform.modules.enterprise.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.whty.platform.common.persistence.BaseEntity;


/***
 * 企业注册审核资料信息图片表
 * @author xushenghua
 * @version 2013-10-14
 */
@Entity
@Table(name = "front_verify_enterpriseinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enterpriseinfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7632731435733074845L;
	
	private Long id;
	private Long regEnterpriseId;
	private String name;
	private String path;
	private String imgpath;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRegEnterpriseId() {
		return regEnterpriseId;
	}
	public void setRegEnterpriseId(Long regEnterpriseId) {
		this.regEnterpriseId = regEnterpriseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
}
