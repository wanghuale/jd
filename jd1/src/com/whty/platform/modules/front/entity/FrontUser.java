/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.front.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.modules.sp.utils.DecodeUtils;

/**
 * 服务消费者Entity
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Entity
@Table(name = "tab_consumer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FrontUser extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String username;
	private String password;
	private String code; // 使用方编号 code varchar(200) 200 FALSE FALSE FALSE
	private String name; // 使用方名称 name varchar(200) 200 FALSE FALSE FALSE
	private String ip; // IP ip varchar(100) 100 FALSE FALSE FALSE
	private String description; // 使用方描述 description varchar(1000) 1000 FALSE
	private String isused; // 启用 isused varchar(10) 10 FALSE FALSE FALSE
	private String notifyurl;

	public FrontUser() {
		super();
	}

	public FrontUser(Long id) {
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsused() {
		return isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	@Transient
	public String getkey() {
		return DecodeUtils.getKey(this.id, this.username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNotifyurl() {
		return notifyurl;
	}

	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}

}
