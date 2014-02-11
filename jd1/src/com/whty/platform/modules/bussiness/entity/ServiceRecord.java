/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.whty.platform.common.persistence.BaseEntity;

/**
 * 服务Entity
 * 
 * @author qimin
 * @version 2013-07-16
 */
@Entity
@Table(name = "tab_service_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String username;// 消费者主机
	private String ip; // 消费者IP
	private String reData; // 请求数据包
	private String doData;// 返回数据包
	private String status;// 请求状态
	private String reService; // SP服务
	private Services doService; // 供应商提供服务
	private Timestamp createDate;// create_date datetime '创建时间',
	private String remarks;// remarks varchar(255) default NULL comment '备注信息',
	private String delFlag; // del_flag char(1) not '删除标记（0：正常；1：删除）',
	private Double tradeMoney = 0D;

	public ServiceRecord() {
		super();
	}

	public ServiceRecord(Long id) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReData() {
		return reData;
	}

	public void setReData(String reData) {
		this.reData = reData;
	}

	public String getDoData() {
		return doData;
	}

	public void setDoData(String doData) {
		if (StringUtils.isNotBlank(doData) && doData.length() >= 5000) {
			this.doData = doData.substring(0, 1999);
		} else {
			this.doData = doData;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReService() {
		return reService;
	}

	public void setReService(String reService) {
		this.reService = reService;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "do_service")
	@NotFound(action = NotFoundAction.IGNORE)
	public Services getDoService() {
		return doService;
	}

	public void setDoService(Services doService) {
		this.doService = doService;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

}
