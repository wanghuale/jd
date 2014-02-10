/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.dangpu.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.generate.codegen.DateUtils;
import com.whty.platform.modules.sys.entity.User;

/**
 * 当票Entity
 * 
 * @author wanghuaxing
 * @version 2013-12-30
 */
@Entity
@Table(name = "tab_ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ticket extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long id; // 编号
	private String name;// 姓名
	private String card;// 身份证
	private Double money; // 当出金额
	private String phone; // 电话
	private Date startTime; // 入当时间
	private Date endTime; // 赎当时间
	private User user; // 录入员工
	private Date createStartTime; // 录入实际日期
	private User redeemUser; // 录入员工
	private Date createEndTime; // 赎当实际日期
	private String status; // 状态：1入当、2已赎当
	private Double interest; // 利息
	private Double profit; // 利润
	private Double badDebt;// 坏账准备金
	private String warnFlag;// 判断是否预警  0:无1:不预警2：预警
	private Date warnTime;// 预警时间 
	protected String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private String remark;// 备注
	
	private Date searchStartTime;// 搜索开始时间
	private Date searchEndTime;// 搜索结束时间
	
	public Ticket() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}

	public Ticket(Long id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getMoney() {
		return money;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="redeem_user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public User getRedeemUser() {
		return redeemUser;
	}

	public void setRedeemUser(User redeemUser) {
		this.redeemUser = redeemUser;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getBadDebt() {
		return badDebt;
	}

	public void setBadDebt(Double badDebt) {
		this.badDebt = badDebt;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}
	
	
	public Date getWarnTime() {
		return warnTime;
	}
	
	
	@Transient
	public Date getSearchStartTime() {
		return searchStartTime;
	}

	public void setSearchStartTime(Date searchStartTime) {
		this.searchStartTime = searchStartTime;
	}

	@Transient
	public Date getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(Date searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	@Transient
	public String getWarnFlag() {
		warnFlag = "0";
		if(warnTime == null){
			return warnFlag;
		}
		if(DateUtils.parseDate(DateUtils.formatDate(warnTime, "yyyy-MM-dd")).getTime()>DateUtils.parseDate(DateUtils.getDate("yyyy-MM-dd")).getTime()){
			warnFlag = "1";
		}else{
			warnFlag = "2";
		}
		return warnFlag;
	}

	public void setWarnFlag(String warnFlag) {
		this.warnFlag = warnFlag;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	@Length(min=1, max=1)
	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Transient
	public String getSearchStartTimeStr(){
		if(searchStartTime==null){
			return "";
		}
		return DateUtils.formatDate(searchStartTime, "yyyy-MM-dd");
	}
	
	@Transient
	public String getSearchEndTimeStr(){
		if(searchEndTime==null){
			return "";
		}
		return DateUtils.formatDate(searchEndTime, "yyyy-MM-dd");
	}
	
	@Transient
	public String getStartTimeStr(){
		if(startTime==null){
			return "";
		}
		return DateUtils.formatDate(startTime, "yyyy-MM-dd");
	}
	
	@Transient
	public String getEndTimeStr(){
		if(endTime==null){
			return "";
		}
		return DateUtils.formatDate(endTime, "yyyy-MM-dd");
	}
	
	@Transient
	public String getWarnTimeStr(){
		if(warnTime==null){
			return "";
		}
		return DateUtils.formatDate(warnTime, "yyyy-MM-dd");
	}
}
