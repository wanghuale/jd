package com.whty.platform.modules.enterprise.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.common.utils.excel.annotation.ExcelField;

/**
 * 企业账户表
 * @author jincheng
 * @version 2013-10-12
 */
@Entity
@Table(name = "sys_enterprise_account")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnterpriseAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long id;		// 编号
//	private String regEnterpriseid;	// 企业表ID
	private Double buymoney;	// 购买额度
	private Enterprise enterprise; //注册用户id
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getBuymoney() {
		return buymoney;
	}
	public void setBuymoney(Double buymoney) {
		this.buymoney = buymoney;
	}
	@ManyToOne
	@JoinColumn(name = "regEnterpriseid")
	@NotFound(action = NotFoundAction.IGNORE)
	public Enterprise getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
}
