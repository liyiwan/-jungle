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
@Table(name = "G_Trade_TradeList")
public class Trade {
	
	private Long tradeId;
	private Date regTime;
	private Date tradeTime;
	private Integer tradeStatus;
	private String tradeNo;
	private Long shopId;
	private String seller;
	private String tradeType;
	private Integer customerId;
	private String sndTo;
	private String country;
	private String province;
	private String city;
	private String town;
	private String adr;
	private String zip;
	private String tel;
	private String regOperator;
	private Date confirmTime;
	private String confirmOperator;
	private Integer printSndBill;
	private Integer printExpress;
	private String picker;
	private String chkOperator;
	private Date chkTime;
	private String packageOperator;
	private Date sndTime;
	private String sndOperator;
	private BigDecimal goodsWeight;
	private BigDecimal packagedWeight;
	private String customerRemark;
	private String remark;
	private Long logisticId;
	private String packageId;
	private BigDecimal postage;
	private String postId;
	private String chargeType;
	private String chargeId;
	private Integer accountId;
	private BigDecimal otherCost;
	private BigDecimal goodsTotal;
	private BigDecimal postageTotal;
	private BigDecimal taxValue;
	private BigDecimal couponValue;
	private BigDecimal favourableTotal;
	private BigDecimal allTotal;
	private BigDecimal rcvTotal;
	private String currencyType;
	private BigDecimal currencyRate;
	private BigDecimal goodsCost;
	private BigDecimal packageCost;
	private BigDecimal totalProfit;
	private String priceSpec;
	private BigDecimal priceDis;
	private String goodsList;
	private Integer tradeFlagId;
	private BigDecimal drawbackValue;
	private String otherGoods;
	private BigDecimal commissionValue;
	private Integer wareHouseId;
	private Integer printCollect;
	private Integer printPos;
	private Integer printChannel;
	private Date preDate;
	private String preType;
	private Long stockOutFlag;
	private Long manualChkFlag;
	private String appendRemark;
	private Long invoiceFlag;
	private Integer printInvoice;
	private BigDecimal estimateWeight;
	private String invoiceTitle;
	private String rmbValue;
	private Integer providerId;
	private String chargeOperator;
	private String freezeReason;
	private String cancelReason;
	private Long sellBackFlag;
	private String tradeFrom;
	private String tradeNo2;
	private Long sndSysFlag;
	private BigDecimal postageFee2;
	private String reserved1;
	private String reserved2;
	private String reserved3;
	private String reserved4;
	private String printChk;
	private String fxtid;
	private String tradeNick;
	private String printer;
	private Integer chkchannel;
	private String troubleClass;

	public Trade() {
	}

	public Trade(Long tradeId) {
		this.tradeId = tradeId;
	}

	@Id
	@Column(name = "TradeID", unique = true, nullable = false)
	public Long getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RegTime", length = 23)
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TradeTime", length = 23)
	public Date getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	@Column(name = "TradeStatus")
	public Integer getTradeStatus() {
		return this.tradeStatus;
	}

	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	@Column(name = "TradeNO", length = 20)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name = "Seller", length = 20)
	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Column(name = "TradeType", length = 10)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "CustomerID")
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "SndTo", length = 50)
	public String getSndTo() {
		return this.sndTo;
	}

	public void setSndTo(String sndTo) {
		this.sndTo = sndTo;
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

	@Column(name = "Adr", length = 200)
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

	@Column(name = "Tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "RegOperator", length = 20)
	public String getRegOperator() {
		return this.regOperator;
	}

	public void setRegOperator(String regOperator) {
		this.regOperator = regOperator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ConfirmTime", length = 23)
	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Column(name = "ConfirmOperator", length = 20)
	public String getConfirmOperator() {
		return this.confirmOperator;
	}

	public void setConfirmOperator(String confirmOperator) {
		this.confirmOperator = confirmOperator;
	}

	@Column(name = "PrintSndBill")
	public Integer getPrintSndBill() {
		return this.printSndBill;
	}

	public void setPrintSndBill(Integer printSndBill) {
		this.printSndBill = printSndBill;
	}

	@Column(name = "PrintExpress")
	public Integer getPrintExpress() {
		return this.printExpress;
	}

	public void setPrintExpress(Integer printExpress) {
		this.printExpress = printExpress;
	}

	@Column(name = "Picker", length = 20)
	public String getPicker() {
		return this.picker;
	}

	public void setPicker(String picker) {
		this.picker = picker;
	}

	@Column(name = "ChkOperator", length = 20)
	public String getChkOperator() {
		return this.chkOperator;
	}

	public void setChkOperator(String chkOperator) {
		this.chkOperator = chkOperator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ChkTime", length = 23)
	public Date getChkTime() {
		return this.chkTime;
	}

	public void setChkTime(Date chkTime) {
		this.chkTime = chkTime;
	}

	@Column(name = "PackageOperator", length = 20)
	public String getPackageOperator() {
		return this.packageOperator;
	}

	public void setPackageOperator(String packageOperator) {
		this.packageOperator = packageOperator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SndTime", length = 23)
	public Date getSndTime() {
		return this.sndTime;
	}

	public void setSndTime(Date sndTime) {
		this.sndTime = sndTime;
	}

	@Column(name = "SndOperator", length = 20)
	public String getSndOperator() {
		return this.sndOperator;
	}

	public void setSndOperator(String sndOperator) {
		this.sndOperator = sndOperator;
	}

	@Column(name = "GoodsWeight", scale = 4)
	public BigDecimal getGoodsWeight() {
		return this.goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	@Column(name = "PackagedWeight", scale = 4)
	public BigDecimal getPackagedWeight() {
		return this.packagedWeight;
	}

	public void setPackagedWeight(BigDecimal packagedWeight) {
		this.packagedWeight = packagedWeight;
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

	@Column(name = "LogisticID")
	public Long getLogisticId() {
		return this.logisticId;
	}

	public void setLogisticId(Long logisticId) {
		this.logisticId = logisticId;
	}

	@Column(name = "PackageID", length = 20)
	public String getPackageId() {
		return this.packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	@Column(name = "Postage", scale = 4)
	public BigDecimal getPostage() {
		return this.postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	@Column(name = "PostID", length = 30)
	public String getPostId() {
		return this.postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	@Column(name = "ChargeType", length = 20)
	public String getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name = "ChargeID", length = 50)
	public String getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	@Column(name = "AccountID")
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(name = "OtherCost", scale = 4)
	public BigDecimal getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(BigDecimal otherCost) {
		this.otherCost = otherCost;
	}

	@Column(name = "GoodsTotal", scale = 4)
	public BigDecimal getGoodsTotal() {
		return this.goodsTotal;
	}

	public void setGoodsTotal(BigDecimal goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

	@Column(name = "PostageTotal", scale = 4)
	public BigDecimal getPostageTotal() {
		return this.postageTotal;
	}

	public void setPostageTotal(BigDecimal postageTotal) {
		this.postageTotal = postageTotal;
	}

	@Column(name = "TaxValue", scale = 4)
	public BigDecimal getTaxValue() {
		return this.taxValue;
	}

	public void setTaxValue(BigDecimal taxValue) {
		this.taxValue = taxValue;
	}

	@Column(name = "CouponValue", scale = 4)
	public BigDecimal getCouponValue() {
		return this.couponValue;
	}

	public void setCouponValue(BigDecimal couponValue) {
		this.couponValue = couponValue;
	}

	@Column(name = "FavourableTotal", scale = 4)
	public BigDecimal getFavourableTotal() {
		return this.favourableTotal;
	}

	public void setFavourableTotal(BigDecimal favourableTotal) {
		this.favourableTotal = favourableTotal;
	}

	@Column(name = "AllTotal", scale = 4)
	public BigDecimal getAllTotal() {
		return this.allTotal;
	}

	public void setAllTotal(BigDecimal allTotal) {
		this.allTotal = allTotal;
	}

	@Column(name = "RcvTotal", scale = 4)
	public BigDecimal getRcvTotal() {
		return this.rcvTotal;
	}

	public void setRcvTotal(BigDecimal rcvTotal) {
		this.rcvTotal = rcvTotal;
	}

	@Column(name = "CurrencyType", length = 20)
	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Column(name = "CurrencyRate", scale = 4)
	public BigDecimal getCurrencyRate() {
		return this.currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	@Column(name = "GoodsCost", scale = 4)
	public BigDecimal getGoodsCost() {
		return this.goodsCost;
	}

	public void setGoodsCost(BigDecimal goodsCost) {
		this.goodsCost = goodsCost;
	}

	@Column(name = "PackageCost", scale = 4)
	public BigDecimal getPackageCost() {
		return this.packageCost;
	}

	public void setPackageCost(BigDecimal packageCost) {
		this.packageCost = packageCost;
	}

	@Column(name = "TotalProfit", scale = 4)
	public BigDecimal getTotalProfit() {
		return this.totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	@Column(name = "PriceSpec", length = 20)
	public String getPriceSpec() {
		return this.priceSpec;
	}

	public void setPriceSpec(String priceSpec) {
		this.priceSpec = priceSpec;
	}

	@Column(name = "PriceDis", scale = 4)
	public BigDecimal getPriceDis() {
		return this.priceDis;
	}

	public void setPriceDis(BigDecimal priceDis) {
		this.priceDis = priceDis;
	}

	@Column(name = "GoodsList", length = 200)
	public String getGoodsList() {
		return this.goodsList;
	}

	public void setGoodsList(String goodsList) {
		this.goodsList = goodsList;
	}

	@Column(name = "TradeFlagID")
	public Integer getTradeFlagId() {
		return this.tradeFlagId;
	}

	public void setTradeFlagId(Integer tradeFlagId) {
		this.tradeFlagId = tradeFlagId;
	}

	@Column(name = "DrawbackValue", scale = 4)
	public BigDecimal getDrawbackValue() {
		return this.drawbackValue;
	}

	public void setDrawbackValue(BigDecimal drawbackValue) {
		this.drawbackValue = drawbackValue;
	}

	@Column(name = "OtherGoods", length = 50)
	public String getOtherGoods() {
		return this.otherGoods;
	}

	public void setOtherGoods(String otherGoods) {
		this.otherGoods = otherGoods;
	}

	@Column(name = "CommissionValue", scale = 4)
	public BigDecimal getCommissionValue() {
		return this.commissionValue;
	}

	public void setCommissionValue(BigDecimal commissionValue) {
		this.commissionValue = commissionValue;
	}

	@Column(name = "WareHouseID")
	public Integer getWareHouseId() {
		return this.wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	@Column(name = "PrintCollect")
	public Integer getPrintCollect() {
		return this.printCollect;
	}

	public void setPrintCollect(Integer printCollect) {
		this.printCollect = printCollect;
	}

	@Column(name = "PrintPos")
	public Integer getPrintPos() {
		return this.printPos;
	}

	public void setPrintPos(Integer printPos) {
		this.printPos = printPos;
	}

	@Column(name = "PrintChannel")
	public Integer getPrintChannel() {
		return this.printChannel;
	}

	public void setPrintChannel(Integer printChannel) {
		this.printChannel = printChannel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PreDate", length = 23)
	public Date getPreDate() {
		return this.preDate;
	}

	public void setPreDate(Date preDate) {
		this.preDate = preDate;
	}

	@Column(name = "PreType", length = 20)
	public String getPreType() {
		return this.preType;
	}

	public void setPreType(String preType) {
		this.preType = preType;
	}

	@Column(name = "bStockOut")
	public Long getStockOutFlag() {
		return this.stockOutFlag;
	}

	public void setStockOutFlag(Long stockOutFlag) {
		this.stockOutFlag = stockOutFlag;
	}

	@Column(name = "bManualCHK")
	public Long getManualChkFlag() {
		return this.manualChkFlag;
	}

	public void setManualChkFlag(Long manualChkFlag) {
		this.manualChkFlag = manualChkFlag;
	}

	@Column(name = "AppendRemark", length = 1000)
	public String getAppendRemark() {
		return this.appendRemark;
	}

	public void setAppendRemark(String appendRemark) {
		this.appendRemark = appendRemark;
	}

	@Column(name = "bInvoice")
	public Long getInvoiceFlag() {
		return this.invoiceFlag;
	}

	public void setInvoiceFlag(Long invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}

	@Column(name = "PrintInvoice")
	public Integer getPrintInvoice() {
		return this.printInvoice;
	}

	public void setPrintInvoice(Integer printInvoice) {
		this.printInvoice = printInvoice;
	}

	@Column(name = "estimateWeight", scale = 4)
	public BigDecimal getEstimateWeight() {
		return this.estimateWeight;
	}

	public void setEstimateWeight(BigDecimal estimateWeight) {
		this.estimateWeight = estimateWeight;
	}

	@Column(name = "InvoiceTitle", length = 50)
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Column(name = "RMB_Value", length = 50)
	public String getRmbValue() {
		return this.rmbValue;
	}

	public void setRmbValue(String rmbValue) {
		this.rmbValue = rmbValue;
	}

	@Column(name = "ProviderID")
	public Integer getProviderId() {
		return this.providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	@Column(name = "ChargeOperator", length = 20)
	public String getChargeOperator() {
		return this.chargeOperator;
	}

	public void setChargeOperator(String chargeOperator) {
		this.chargeOperator = chargeOperator;
	}

	@Column(name = "FreezeReason", length = 20)
	public String getFreezeReason() {
		return this.freezeReason;
	}

	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}

	@Column(name = "CancelReason", length = 20)
	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name = "bSellBack")
	public Long getSellBackFlag() {
		return this.sellBackFlag;
	}

	public void setSellBackFlag(Long sellBackFlag) {
		this.sellBackFlag = sellBackFlag;
	}

	@Column(name = "TradeFrom", length = 20)
	public String getTradeFrom() {
		return this.tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

	@Column(name = "TradeNO2", length = 500)
	public String getTradeNo2() {
		return this.tradeNo2;
	}

	public void setTradeNo2(String tradeNo2) {
		this.tradeNo2 = tradeNo2;
	}

	@Column(name = "bSndSys")
	public Long getSndSysFlag() {
		return this.sndSysFlag;
	}

	public void setSndSysFlag(Long sndSysFlag) {
		this.sndSysFlag = sndSysFlag;
	}

	@Column(name = "PostageFee2", scale = 4)
	public BigDecimal getPostageFee2() {
		return this.postageFee2;
	}

	public void setPostageFee2(BigDecimal postageFee2) {
		this.postageFee2 = postageFee2;
	}

	@Column(name = "Reserved1", length = 50)
	public String getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "Reserved2", length = 50)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	@Column(name = "Reserved3", length = 50)
	public String getReserved3() {
		return this.reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	@Column(name = "Reserved4", length = 50)
	public String getReserved4() {
		return this.reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	@Column(name = "PrintCHK", length = 20)
	public String getPrintChk() {
		return this.printChk;
	}

	public void setPrintChk(String printChk) {
		this.printChk = printChk;
	}

	@Column(name = "FXTid", length = 50)
	public String getFxtid() {
		return this.fxtid;
	}

	public void setFxtid(String fxtid) {
		this.fxtid = fxtid;
	}

	@Column(name = "TradeNick", length = 50)
	public String getTradeNick() {
		return this.tradeNick;
	}

	public void setTradeNick(String tradeNick) {
		this.tradeNick = tradeNick;
	}

	@Column(name = "Printer", length = 20)
	public String getPrinter() {
		return this.printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	@Column(name = "CHKChannel")
	public Integer getChkchannel() {
		return this.chkchannel;
	}

	public void setChkchannel(Integer chkchannel) {
		this.chkchannel = chkchannel;
	}

	@Column(name = "TroubleClass", length = 20)
	public String getTroubleClass() {
		return this.troubleClass;
	}

	public void setTroubleClass(String troubleClass) {
		this.troubleClass = troubleClass;
	}

	@Column(name = "ShopID")
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
