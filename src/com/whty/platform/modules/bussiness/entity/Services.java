/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.entity;

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

import com.whty.platform.common.persistence.DataEntity;

/**
 * 服务Entity
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Entity
@Table(name = "tab_services")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Services extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String code;// 服务编号 code varchar(200) 200 FALSE FALSE FALSE
	private String bussinessType;// 业务类型
	private String name;// 服务名称 name varchar(200) 200 FALSE FALSE FALSE
	private Provider provider;// 接入方ID provider_id int(15) 15 FALSE TRUE FALSE
	private String description;// 服务描述 description varchar(1000) 1000 FALSE
	private String uri;// 服务URI uri varchar(300) 300 FALSE FALSE FALSE
	private String businessHandler;// 本地对应服务
	private String isused;// 启用 isused varchar(10) 10 FALSE FALSE FALSE

	public Services() {
		super();
	}

	public Services(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIsused() {
		return isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	public String getBusinessHandler() {
		return businessHandler;
	}

	public void setBusinessHandler(String businessHandler) {
		this.businessHandler = businessHandler;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

}
