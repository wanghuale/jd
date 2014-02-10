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

import com.whty.platform.common.persistence.BaseEntity;

/**
 * 分润 Entity
 * 
 * @author 舒海洋
 * @version 2013-10-15
 */
@Entity
@Table(name = "sys_profit_region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProfitRegion extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 分润设置表id
	 */
	private Long profitSetId;

	/**
	 * 利润区间 开始
	 */
	private String startregion;

	/**
	 * 利润区间结束
	 */
	private String endregion;

	/**
	 * 利润金额
	 */
	private Double money;

	public ProfitRegion() {
		super();
	}

	public ProfitRegion(Long id) {
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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getStartregion() {
		return startregion;
	}

	public void setStartregion(String startregion) {
		this.startregion = startregion;
	}

	public String getEndregion() {
		return endregion;
	}

	public void setEndregion(String endregion) {
		this.endregion = endregion;
	}

	public Long getProfitSetId() {
		return profitSetId;
	}

	public void setProfitSetId(Long profitSetId) {
		this.profitSetId = profitSetId;
	}

}
