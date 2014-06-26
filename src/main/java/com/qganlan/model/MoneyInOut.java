package com.qganlan.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "G_Money_InOut")
public class MoneyInOut {

	private Long inOutId;
	private Integer inOutType;
	private Date regDate;
	private Date chktime;
	private String regOperator;
	private String chkoperator;
	private Integer shopId;

	private Integer operationId;
	private String summary;
	private Integer customerId;
	private BigDecimal amountIn;
	private BigDecimal amountOut;
	private Integer curStatus;
	private BigDecimal balance;
	private Integer customerType;
	private Integer dueId;
	private String operationType;
	private Integer writeOffType;
	private Integer writeOffId;

	@Id
	@Column(name = "InOutID", unique = true, nullable = false)
	public Long getInOutId() {
		return this.inOutId;
	}

	public void setInOutId(Long inOutId) {
		this.inOutId = inOutId;
	}

	@Column(name = "InOutType")
	public Integer getInOutType() {
		return this.inOutType;
	}

	public void setInOutType(Integer inOutType) {
		this.inOutType = inOutType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "regDate", length = 23)
	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHKTime", length = 23)
	public Date getChktime() {
		return this.chktime;
	}

	public void setChktime(Date chktime) {
		this.chktime = chktime;
	}

	@Column(name = "RegOperator", length = 20)
	public String getRegOperator() {
		return this.regOperator;
	}

	public void setRegOperator(String regOperator) {
		this.regOperator = regOperator;
	}

	@Column(name = "CHKOperator", length = 20)
	public String getChkoperator() {
		return this.chkoperator;
	}

	public void setChkoperator(String chkoperator) {
		this.chkoperator = chkoperator;
	}

	@Column(name = "ShopID")
	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	@Column(name = "OperationID")
	public Integer getOperationId() {
		return this.operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	@Column(name = "Summary", length = 60)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "CustomerID")
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "Amount_IN")
	public BigDecimal getAmountIn() {
		return this.amountIn;
	}

	public void setAmountIn(BigDecimal amountIn) {
		this.amountIn = amountIn;
	}

	@Column(name = "Amount_Out")
	public BigDecimal getAmountOut() {
		return this.amountOut;
	}

	public void setAmountOut(BigDecimal amountOut) {
		this.amountOut = amountOut;
	}

	@Column(name = "curStatus")
	public Integer getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(Integer curStatus) {
		this.curStatus = curStatus;
	}

	@Column(name = "Balance")
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "CustomerType")
	public Integer getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	@Column(name = "DueID")
	public Integer getDueId() {
		return this.dueId;
	}

	public void setDueId(Integer dueId) {
		this.dueId = dueId;
	}

	@Column(name = "OperationType", length = 20)
	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Column(name = "WriteOffType")
	public Integer getWriteOffType() {
		return this.writeOffType;
	}

	public void setWriteOffType(Integer writeOffType) {
		this.writeOffType = writeOffType;
	}

	@Column(name = "WriteOffID")
	public Integer getWriteOffId() {
		return this.writeOffId;
	}

	public void setWriteOffId(Integer writeOffId) {
		this.writeOffId = writeOffId;
	}

}
