package com.qganlan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "J_RAW_TRADE")
public class JRawTrade implements Serializable {

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

	private String sellerNick;

	@Column(name = "SELLER_NICK")
	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	private String buyerNick;

	@Column(name = "BUYER_NICK")
	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
		
	private String payment;

	@Column(name = "PAYMENT")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	private Date payTime;

	@Column(name = "PAY_TIME")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	private String buyerMessage;

	@Column(name = "BUYER_MESSAGE")
	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	
	private String receiverName;

	@Column(name = "RECEIVER_NAME")
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	private String receiverPhone;

	@Column(name = "RECEIVER_PHONE")
	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	
	private String receiverState;

	@Column(name = "RECEIVER_STATE")
	public String getReceiverState() {
		return receiverState;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	
	private String receiverZip;

	@Column(name = "RECEIVER_ZIP")
	public String getReceiverZip() {
		return receiverZip;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}
	
	private String receiverMobile;

	@Column(name = "RECEIVER_MOBILE")
	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	
	private String receiverAddress;

	@Column(name = "RECEIVER_ADDRESS")
	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	
	private String receiverCity;

	@Column(name = "RECEIVER_CITY")
	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	
	private String receiverDistrict;

	@Column(name = "RECEIVER_DISTRICT")
	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	
	private String buyerAlipayNo;

	@Column(name = "BUYER_ALIPAY_NO")
	public String getBuyerAlipayNo() {
		return buyerAlipayNo;
	}

	public void setBuyerAlipayNo(String buyerAlipayNo) {
		this.buyerAlipayNo = buyerAlipayNo;
	}
	
	private String buyerEmail;

	@Column(name = "BUYER_EMAIL")
	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	
	private String postFee;

	@Column(name = "POST_FEE")
	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	
	private String sellerMemo;

	@Column(name = "SELLER_MEMO")
	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}
	
	private Integer curStatus;

	@Column(name = "CUR_STATUS")
	public Integer getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(Integer curStatus) {
		this.curStatus = curStatus;
	}
	
	private List<JRawOrder> orders;

	@Transient
	public List<JRawOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<JRawOrder> orders) {
		this.orders = orders;
	}

}
