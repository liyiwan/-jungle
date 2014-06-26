package com.qganlan.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MoneyInOutDTO {

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
	
	private String itemName;
	private String accountName;

	public Long getInOutId() {
		return this.inOutId;
	}

	public void setInOutId(Long inOutId) {
		this.inOutId = inOutId;
	}

	public Integer getInOutType() {
		return this.inOutType;
	}

	public void setInOutType(Integer inOutType) {
		this.inOutType = inOutType;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getChktime() {
		return this.chktime;
	}

	public void setChktime(Date chktime) {
		this.chktime = chktime;
	}

	public String getRegOperator() {
		return this.regOperator;
	}

	public void setRegOperator(String regOperator) {
		this.regOperator = regOperator;
	}

	public String getChkoperator() {
		return this.chkoperator;
	}

	public void setChkoperator(String chkoperator) {
		this.chkoperator = chkoperator;
	}

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getOperationId() {
		return this.operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmountIn() {
		return this.amountIn;
	}

	public void setAmountIn(BigDecimal amountIn) {
		this.amountIn = amountIn;
	}

	public BigDecimal getAmountOut() {
		return this.amountOut;
	}

	public void setAmountOut(BigDecimal amountOut) {
		this.amountOut = amountOut;
	}

	public Integer getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(Integer curStatus) {
		this.curStatus = curStatus;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getDueId() {
		return this.dueId;
	}

	public void setDueId(Integer dueId) {
		this.dueId = dueId;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Integer getWriteOffType() {
		return this.writeOffType;
	}

	public void setWriteOffType(Integer writeOffType) {
		this.writeOffType = writeOffType;
	}

	public Integer getWriteOffId() {
		return this.writeOffId;
	}

	public void setWriteOffId(Integer writeOffId) {
		this.writeOffId = writeOffId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}
