package com.qganlan.dto;

public class GoodsSpecDTO {
	private Long goodsId;
	private String goodsNo;
	private String goodsName;
	private Long specId;
	private String specCode;
	private String specName;
	private Long flagId;
	private String picPath;
	private Long soldCount;
	private Long stock;
	private Long purchaseCount;
	
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	
	public Long getSpecId() {
		return specId;
	}
	
	public void setSpecId(Long specId) {
		this.specId = specId;
	}
	
	public String getSpecCode() {
		return specCode;
	}
	
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	
	public String getSpecName() {
		return specName;
	}
	
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	
	public Long getFlagId() {
		return flagId;
	}
	
	public void setFlagId(Long flagId) {
		this.flagId = flagId;
	}
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public String getGoodsNo() {
		return goodsNo;
	}
	
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public Long getSoldCount() {
		return soldCount;
	}
	
	public void setSoldCount(Long soldCount) {
		this.soldCount = soldCount;
	}
	
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	
	public Long getPurchaseCount() {
		return purchaseCount;
	}
	
	public void setPurchaseCount(Long purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
}
