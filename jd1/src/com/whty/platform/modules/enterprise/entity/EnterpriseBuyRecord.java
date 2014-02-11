/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.enterprise.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.sun.istack.internal.NotNull;
import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.common.utils.excel.annotation.ExcelField;


/**
 * 企业购买额度记录表Entity
 * @author jincheng
 * @version 2013-10-12
 */
@Entity
@Table(name = "sys_buy_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnterpriseBuyRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 企业信息表id
	 */
	private Enterprise enterprise;
	
	/**
	 * 企业账户信息表id
	 */
	private EnterpriseAccount enterpriseAccount;
	
	/**
	 * 购买金额
	 */
	private Double buymoney;
	
	/**
	 * 交易时间
	 */
	private Date tradingTime;
	
	/**
	 * 手续费率
	 */
	private Double poundageRate;
	
	/**
	 * 手续费
	 */
	private Double poundage;
	
	/**
	 * 合计金额=交易金额+手续费
	 */
	private Double totalTradingMoney;
	
	/**
	 * 充值前卡金额
	 */
	private Double preMoney;
	
	/**
	 * 卡内余额
	 */
	private Double remainMoney;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 审核情况： 0-待审核   1-审核通过   2-审核未过
	 */
	private String verify;
	
	/**
	 * 审核意见
	 */
	private String suggest;
	
	/**
	 * 现金，电话
	 */
	private String buytype;
	
	/**
	 * 数据标识：0-购买审核数据，1-企业购买支付数据
	 */
	private String flag;
	
	
	
	public EnterpriseBuyRecord() {
		super();
	}

	public EnterpriseBuyRecord(Long id){
		this();
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "regEnterpriseid")
	@NotFound(action = NotFoundAction.IGNORE)
	public Enterprise getEnterprise() {
		return enterprise;
	}
	
	public void setEnterpriseAccount(EnterpriseAccount enterpriseAccount) {
		this.enterpriseAccount = enterpriseAccount;
	}
	@ManyToOne
	@JoinColumn(name = "enterpriseAccountId")
	@NotFound(action = NotFoundAction.IGNORE)
	public EnterpriseAccount getEnterpriseAccount() {
		return enterpriseAccount;
	}
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Double getBuymoney() {
		return buymoney;
	}

	public void setBuymoney(Double buymoney) {
		this.buymoney = buymoney;
	}
	
	public Date getTradingTime() {
		return tradingTime;
	}

	public void setTradingTime(Date tradingTime) {
		this.tradingTime = tradingTime;
	}

	public Double getPoundageRate() {
		return poundageRate;
	}

	public void setPoundageRate(Double poundageRate) {
		this.poundageRate = poundageRate;
	}

	public Double getPoundage() {
		return poundage;
	}

	public void setPoundage(Double poundage) {
		this.poundage = poundage;
	}

	public Double getTotalTradingMoney() {
		return totalTradingMoney;
	}

	public void setTotalTradingMoney(Double totalTradingMoney) {
		this.totalTradingMoney = totalTradingMoney;
	}

	public Double getPreMoney() {
		return preMoney;
	}

	public void setPreMoney(Double preMoney) {
		this.preMoney = preMoney;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getBuytype() {
		return buytype;
	}

	public void setBuytype(String buytype) {
		this.buytype = buytype;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}


