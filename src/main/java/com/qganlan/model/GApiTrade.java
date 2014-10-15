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
@Table(name = "G_API_TradeList")
public class GApiTrade {
	
	private Long billId;
	private String tradeNo;
	private Long shopId;
	private Integer curStatus;
	private String tradeStatus;
	private Date tradeTime;
	private String customerId;
	private String customerName;
	private String payId;
	private String payAccount;
	private Integer synStatus;
	private String country;
	private String province;
	private String city;
	private String town;
	private String adr;
	private String zip;
	private String phone;
	private String mobile;
	private String email;
	private Long tradeId;
	private String customerRemark;
	private String remark;
	private BigDecimal postFee;
	private BigDecimal goodsFee;
	private BigDecimal totalMoney;
	private BigDecimal favourableMoney;
	private String sndStyle;
	private String chargeType;
	private String seller;
	private String qq;
	private Integer drawBack;
	private Date payTime;
	private Date getTime;
	private Double dtimeStamp;
	private Boolean bfx;
	private String invoiceTitle;
	private Integer fromId;
	private Date sndTime;
	private BigDecimal codserviceFee;
	private String synCause;
	private Boolean bdelayForRemark;
	private Boolean bdelayForOrder;
	private String summary;
	private String tradeType;
	private Integer apitype;
	private String fxtid;
	private Boolean remind;
	private Boolean bwlb;
	private Boolean bbrandSale;
	private Boolean bsoldOver;
	private Boolean bsplit;

	public GApiTrade() {
	}

	@Id
	@Column(name = "BillID", unique = true, nullable = false)
	public Long getBillId() {
		return this.billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	@Column(name = "TradeNO", length = 50)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name = "ShopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "curStatus")
	public Integer getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(Integer curStatus) {
		this.curStatus = curStatus;
	}

	@Column(name = "TradeStatus", length = 20)
	public String getTradeStatus() {
		return this.tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TradeTime", length = 23)
	public Date getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	@Column(name = "CustomerID", length = 50)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CustomerName", length = 50)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "PayID", length = 50)
	public String getPayId() {
		return this.payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	@Column(name = "PayAccount", length = 50)
	public String getPayAccount() {
		return this.payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	@Column(name = "SynStatus")
	public Integer getSynStatus() {
		return this.synStatus;
	}

	public void setSynStatus(Integer synStatus) {
		this.synStatus = synStatus;
	}

	@Column(name = "Country", length = 50)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "Province", length = 50)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "City", length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "Town", length = 50)
	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Column(name = "Adr", length = 120)
	public String getAdr() {
		return this.adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	@Column(name = "Zip", length = 20)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "Phone", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "Mobile", length = 50)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "Email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TradeID")
	public Long getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	@Column(name = "CustomerRemark", length = 500)
	public String getCustomerRemark() {
		return this.customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	@Column(name = "Remark", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "PostFee", scale = 4)
	public BigDecimal getPostFee() {
		return this.postFee;
	}

	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}

	@Column(name = "GoodsFee", scale = 4)
	public BigDecimal getGoodsFee() {
		return this.goodsFee;
	}

	public void setGoodsFee(BigDecimal goodsFee) {
		this.goodsFee = goodsFee;
	}

	@Column(name = "TotalMoney", scale = 4)
	public BigDecimal getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "favourableMoney", scale = 4)
	public BigDecimal getFavourableMoney() {
		return this.favourableMoney;
	}

	public void setFavourableMoney(BigDecimal favourableMoney) {
		this.favourableMoney = favourableMoney;
	}

	@Column(name = "SndStyle", length = 20)
	public String getSndStyle() {
		return this.sndStyle;
	}

	public void setSndStyle(String sndStyle) {
		this.sndStyle = sndStyle;
	}

	@Column(name = "ChargeType", length = 20)
	public String getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name = "Seller", length = 20)
	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Column(name = "QQ", length = 50)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "DrawBack")
	public Integer getDrawBack() {
		return this.drawBack;
	}

	public void setDrawBack(Integer drawBack) {
		this.drawBack = drawBack;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PayTime", length = 23)
	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GetTime", length = 23)
	public Date getGetTime() {
		return this.getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	@Column(name = "dTimeStamp", precision = 53, scale = 0)
	public Double getDtimeStamp() {
		return this.dtimeStamp;
	}

	public void setDtimeStamp(Double dtimeStamp) {
		this.dtimeStamp = dtimeStamp;
	}

	@Column(name = "bFX")
	public Boolean getBfx() {
		return this.bfx;
	}

	public void setBfx(Boolean bfx) {
		this.bfx = bfx;
	}
	
	@Column(name = "bSplit")
	public Boolean getBsplit() {
		return this.bsplit;
	}

	public void setBsplit(Boolean bsplit) {
		this.bsplit = bsplit;
	}

	@Column(name = "InvoiceTitle", length = 200)
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Column(name = "FromID")
	public Integer getFromId() {
		return this.fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SndTime", length = 23)
	public Date getSndTime() {
		return this.sndTime;
	}

	public void setSndTime(Date sndTime) {
		this.sndTime = sndTime;
	}

	@Column(name = "CODServiceFee", scale = 4)
	public BigDecimal getCodserviceFee() {
		return this.codserviceFee;
	}

	public void setCodserviceFee(BigDecimal codserviceFee) {
		this.codserviceFee = codserviceFee;
	}

	@Column(name = "SynCause", length = 300)
	public String getSynCause() {
		return this.synCause;
	}

	public void setSynCause(String synCause) {
		this.synCause = synCause;
	}

	@Column(name = "bDelayForRemark")
	public Boolean getBdelayForRemark() {
		return this.bdelayForRemark;
	}

	public void setBdelayForRemark(Boolean bdelayForRemark) {
		this.bdelayForRemark = bdelayForRemark;
	}

	@Column(name = "bDelayForOrder")
	public Boolean getBdelayForOrder() {
		return this.bdelayForOrder;
	}

	public void setBdelayForOrder(Boolean bdelayForOrder) {
		this.bdelayForOrder = bdelayForOrder;
	}

	@Column(name = "Summary", length = 120)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "TradeType", length = 20)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "APIType")
	public Integer getApitype() {
		return this.apitype;
	}

	public void setApitype(Integer apitype) {
		this.apitype = apitype;
	}

	@Column(name = "FXTid", length = 50)
	public String getFxtid() {
		return this.fxtid;
	}

	public void setFxtid(String fxtid) {
		this.fxtid = fxtid;
	}

	@Column(name = "remind")
	public Boolean getRemind() {
		return this.remind;
	}

	public void setRemind(Boolean remind) {
		this.remind = remind;
	}

	@Column(name = "bwlb")
	public Boolean getBwlb() {
		return this.bwlb;
	}

	public void setBwlb(Boolean bwlb) {
		this.bwlb = bwlb;
	}

	@Column(name = "bbrand_sale")
	public Boolean getBbrandSale() {
		return this.bbrandSale;
	}

	public void setBbrandSale(Boolean bbrandSale) {
		this.bbrandSale = bbrandSale;
	}

	@Column(name = "bSoldOver")
	public Boolean getBsoldOver() {
		return this.bsoldOver;
	}

	public void setBsoldOver(Boolean bsoldOver) {
		this.bsoldOver = bsoldOver;
	}

}
