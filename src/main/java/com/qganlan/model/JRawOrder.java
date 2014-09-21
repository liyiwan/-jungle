package com.qganlan.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_RAW_ORDER")
public class JRawOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long tid;

	@Id
    @Column(name = "TID", nullable = false)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	private String buyerNick;

	@Column(name = "BUYER_NICK")
	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	
	private String invoiceNo;

	@Column(name = "INVOICE_NO")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	private String companyCode;
	
	@Column(name = "COMPANY_CODE")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	private String logisticsCompany;

	@Column(name = "LOGISTICS_COMPANY")
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	private Long num;

	@Column(name = "NUM")
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	private Long numIid;

	@Column(name = "NUM_IID")
	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	
	private Long oid;

	@Id
    @Column(name = "OID", nullable = false)
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}
	
	private String outerIid;

	@Column(name = "OUTER_IID")
	public String getOuterIid() {
		return outerIid;
	}

	public void setOuterIid(String outerIid) {
		this.outerIid = outerIid;
	}
	
	private String outerSkuId;

	@Column(name = "OUTER_SKU_ID")
	public String getOuterSkuId() {
		return outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}
	
	private String payment;

	@Column(name = "PAYMENT")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	private String picPath;

	@Column(name = "PIC_PATH")
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	private String price;

	@Column(name = "PRICE")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	private String sellerNick;

	@Column(name = "SELLER_NICK")
	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	private String skuId;

	@Column(name = "SKU_ID")
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	private String skuPropertiesName;

	@Column(name = "SKU_PROPERTIES_NAME")
	public String getSkuPropertiesName() {
		return skuPropertiesName;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}
	
	private String title;

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	private String totalFee;

	@Column(name = "TOTAL_FEE")
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	private String adjustFee;

	@Column(name = "ADJUST_FEE")
	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}
	
	private Integer curStatus;

	@Column(name = "CUR_STATUS")
	public Integer getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(Integer curStatus) {
		this.curStatus = curStatus;
	}
	
	private String statusMessage;

	@Column(name = "STATUS_MESSAGE")
	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	private String providerNick;

	@Column(name = "PROVIDER_NICK")
	public String getProviderNick() {
		return providerNick;
	}

	public void setProviderNick(String providerNick) {
		this.providerNick = providerNick;
	}
	
	private Long providerNumIid;

	@Column(name = "PROVIDER_NUM_IID")
	public Long getProviderNumIid() {
		return providerNumIid;
	}

	public void setProviderNumIid(Long providerNumIid) {
		this.providerNumIid = providerNumIid;
	}
	
	private String purchaseNick;
	
	@Column(name = "PURCHASE_NICK")
	public String getPurchaseNick() {
		return purchaseNick;
	}

	public void setPurchaseNick(String purchaseNick) {
		this.purchaseNick = purchaseNick;
	}

	private Long purchaseTid;

	@Column(name = "PURCHASE_TID")
	public Long getPurchaseTid() {
		return purchaseTid;
	}

	public void setPurchaseTid(Long purchaseTid) {
		this.purchaseTid = purchaseTid;
	}
}
