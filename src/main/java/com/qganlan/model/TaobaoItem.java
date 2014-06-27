package com.qganlan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_TaobaoItem")
public class TaobaoItem {

	private Long numIid;

	@Id
	@Column(name="NumIid")
	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	private String title;

	@Column(name="Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String picUrl;

	@Column(name="PicUrl")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	private String nick;

	@Column(name="Nick")
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	private String type;

	@Column(name="Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Long cid;

	@Column(name="Cid")
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	private String sellerCids;

	@Column(name="SellerCids")
	public String getSellerCids() {
		return sellerCids;
	}

	public void setSellerCids(String sellerCids) {
		this.sellerCids = sellerCids;
	}

	private Long localCid;

	@Column(name="LocalCid")
	public Long getLocalCid() {
		return localCid;
	}

	public void setLocalCid(Long localCid) {
		this.localCid = localCid;
	}

	private String props;

	@Column(name="Props")
	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	private String inputPids;

	@Column(name="InputPids")
	public String getInputPids() {
		return inputPids;
	}

	public void setInputPids(String inputPids) {
		this.inputPids = inputPids;
	}

	private String inputStr;

	@Column(name="InputStr")
	public String getInputStr() {
		return inputStr;
	}

	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}

	private String desc;

	@Column(name="Description")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private String detailUrl;

	@Column(name="DetailUrl")
	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	private Date listTime;
	
	@Column(name="ListTime")
	public Date getListTime() {
		return listTime;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}

	private Date deListTime;

	@Column(name="DeListTime")
	public Date getDeListTime() {
		return deListTime;
	}

	public void setDeListTime(Date deListTime) {
		this.deListTime = deListTime;
	}

	private String stuffStatus;

	@Column(name="StuffStatus")
	public String getStuffStatus() {
		return stuffStatus;
	}

	public void setStuffStatus(String stuffStatus) {
		this.stuffStatus = stuffStatus;
	}

	private String locationCity;

	@Column(name="LocationCity")
	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	private String locationState;

	@Column(name="LocationState")
	public String getLocationState() {
		return locationState;
	}

	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}

	private String price;

	@Column(name="Price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	private String postFee;

	@Column(name="PostFee")
	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	private String expressFee;

	@Column(name="ExpressFee")
	public String getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(String expressFee) {
		this.expressFee = expressFee;
	}

	private String emsFee;

	@Column(name="EmsFee")
	public String getEmsFee() {
		return emsFee;
	}

	public void setEmsFee(String emsFee) {
		this.emsFee = emsFee;
	}

	private Date created;

	@Column(name="Created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	private Date modified;

	@Column(name="Modified")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	private String approveStatus;

	@Column(name="ApproveStatus")
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	private Long postageId;

	@Column(name="PostageId")
	public Long getPostageId() {
		return postageId;
	}

	public void setPostageId(Long postageId) {
		this.postageId = postageId;
	}

	private String propertyAlias;

	@Column(name="PropertyAlias")
	public String getPropertyAlias() {
		return propertyAlias;
	}

	public void setPropertyAlias(String propertyAlias) {
		this.propertyAlias = propertyAlias;
	}

	private String outerId;

	@Column(name="OuterId")
	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	private Date localUpdateTime;

	@Column(name="LocalUpdateTime")
	public Date getLocalUpdateTime() {
		return localUpdateTime;
	}

	public void setLocalUpdateTime(Date localUpdateTime) {
		this.localUpdateTime = localUpdateTime;
	}

}
