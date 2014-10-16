package com.qganlan.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_Cfg_LogisticList")
public class GCfgLogistics {

	private Long logisticId;
	private String name;
	private String linkMan;
	private String tel;
	private Boolean bmonthCharge;
	private String billStyle;
	private String remark;
	private Integer orderPos;
	private String webSite;
	private Boolean bblockUp;
	private BigDecimal balance;
	private Boolean bsendStyle;
	private String taobaoCompanyCode;
	private String paipaiLogisticsName;

	public GCfgLogistics() {
	}

	@Id
	@Column(name = "LogisticID", unique = true, nullable = false)
	public Long getLogisticId() {
		return this.logisticId;
	}

	public void setLogisticId(Long logisticId) {
		this.logisticId = logisticId;
	}

	@Column(name = "Name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LinkMan", length = 50)
	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "Tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "bMonthCharge")
	public Boolean getBmonthCharge() {
		return this.bmonthCharge;
	}

	public void setBmonthCharge(Boolean bmonthCharge) {
		this.bmonthCharge = bmonthCharge;
	}

	@Column(name = "BillStyle", length = 20)
	public String getBillStyle() {
		return this.billStyle;
	}

	public void setBillStyle(String billStyle) {
		this.billStyle = billStyle;
	}

	@Column(name = "Remark", length = 120)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OrderPos")
	public Integer getOrderPos() {
		return this.orderPos;
	}

	public void setOrderPos(Integer orderPos) {
		this.orderPos = orderPos;
	}

	@Column(name = "WebSite", length = 120)
	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Column(name = "bBlockUp")
	public Boolean getBblockUp() {
		return this.bblockUp;
	}

	public void setBblockUp(Boolean bblockUp) {
		this.bblockUp = bblockUp;
	}

	@Column(name = "Balance", scale = 4)
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "bSendStyle")
	public Boolean getBsendStyle() {
		return this.bsendStyle;
	}

	public void setBsendStyle(Boolean bsendStyle) {
		this.bsendStyle = bsendStyle;
	}

	@Column(name = "taobaoCompanyCode", length = 50)
	public String getTaobaoCompanyCode() {
		return this.taobaoCompanyCode;
	}

	public void setTaobaoCompanyCode(String taobaoCompanyCode) {
		this.taobaoCompanyCode = taobaoCompanyCode;
	}

	@Column(name = "paipaiLogisticsName", length = 50)
	public String getPaipaiLogisticsName() {
		return this.paipaiLogisticsName;
	}

	public void setPaipaiLogisticsName(String paipaiLogisticsName) {
		this.paipaiLogisticsName = paipaiLogisticsName;
	}

}
