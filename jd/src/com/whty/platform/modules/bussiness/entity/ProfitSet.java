/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.whty.platform.common.persistence.BaseEntity;

/**
 * 分润 Entity
 * 
 * @author 舒海洋
 * @version 2013-10-15
 */
@Entity
@Table(name = "sys_profit_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProfitSet extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id号
	 */
	private Long id;

	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 联系人
	 */
	private String linkman;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 联系地址
	 */
	private String address;

	/**
	 * 分润方式(数据字典)
	 */
	private Long distriType;

	/**
	 * 分红比例
	 */
	private Double rate;

	private List<ProfitRegion> profitRegions = Lists.newArrayList(); //

	public ProfitSet() {
		super();
	}

	public ProfitSet(Long id) {
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

	@Length(min = 0, max = 20)
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Length(min = 0, max = 20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Length(min = 0, max = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getDistriType() {
		return distriType;
	}

	public void setDistriType(Long distriType) {
		this.distriType = distriType;
	}

	@Transient
	public List<ProfitRegion> getProfitRegions() {
		return profitRegions;
	}

	public void setProfitRegions(List<ProfitRegion> profitRegions) {
		this.profitRegions = profitRegions;
	}

}
