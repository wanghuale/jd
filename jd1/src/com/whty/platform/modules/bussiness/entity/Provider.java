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
 * 服务提供方Entity
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Entity
@Table(name = "tab_provider")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Provider extends DataEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String code;// 接入方编号 code varchar(200) 200 FALSE FALSE FALSE
	private String name;// 接入方名称 name varchar(200) 200 FALSE FALSE FALSE
	private String description; // 接入方描述 description varchar(1000) 1000 FALSE
	private String protocol;// 通讯协议 protocol varchar(30) 30 FALSE FALSE FALSE
	private String ip;// IP ip varchar(100) 100 FALSE FALSE FALSE
	private int port;// 通讯端口 port varchar(10) 10 FALSE FALSE FALSE
	private String isused; // 启用 isused varchar(10) 10 FALSE FALSE FALSE

	public Provider() {
		super();
	}

	public Provider(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIsused() {
		return isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
