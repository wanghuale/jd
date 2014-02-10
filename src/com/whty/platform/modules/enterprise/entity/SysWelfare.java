package com.whty.platform.modules.enterprise.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.whty.platform.common.persistence.BaseEntity;

/**
 * 福利发放Entity
 * 
 * @author xushenghua
 * @version 2013-10-11
 */
@Entity
@Table(name = "sys_welfare")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysWelfare extends BaseEntity {

	private static final long serialVersionUID = -5232676313643932672L;

	private Long id; 				// 主键
	private String welfareName; 	// 福利名称
	private Double welfareMoney; 	// 福利金额
	
	private FrontOffice office; 	
	private Userinfo userinfo; 		
	
	
	private String receiveType; 	// 领取方式
	private String isreceive; 		// 是否已经领取
	private Date grandTime;			//	发放时间
	private Date receiveTime; 		// 领取时间
	private String remark; 			// 备注

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWelfareName() {
		return welfareName;
	}

	public void setWelfareName(String welfareName) {
		this.welfareName = welfareName;
	}

	public Double getWelfareMoney() {
		return welfareMoney;
	}

	public void setWelfareMoney(Double welfareMoney) {
		this.welfareMoney = welfareMoney;
	}

	@ManyToOne
	@JoinColumn(name = "departid")
	public FrontOffice getOffice() {
		return office;
	}

	public void setOffice(FrontOffice office) {
		this.office = office;
	}

	/**
	 * 用户ID 获取员工名称及相关信息
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name = "reg_userid")
	public Userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(String isreceive) {
		this.isreceive = isreceive;
	}

	public Date getGrandTime() {
		return grandTime;
	}

	public void setGrandTime(Date grandTime) {
		this.grandTime = grandTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}