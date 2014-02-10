/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whty.platform.common.persistence.DataEntity;

/**
 * 本地服务Entity
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Entity
@Table(name = "tab_localservices")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Localservices extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String code; // 接口编号 code varchar(200) 200 FALSE FALSE FALSE
	private String name; // 接口名称 name varchar(200) 200 FALSE FALSE FALSE
	private String protocol; // 协议类型 protocol varchar(30) 30 FALSE FALSE FALSE
	private String port; // 端口 port varchar(15) 15 FALSE FALSE FALSE
	private String uri; // URI uri varchar(300) 300 FALSE FALSE FALSE
	private String isused; // 是否启用 isused varchar(10) 10 FALSE FALSE FALSE
	private String description; // 接口描述 description varchar(1000) 1000 FALSE

	public Localservices() {
		super();
	}

	public Localservices(Long id) {
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
