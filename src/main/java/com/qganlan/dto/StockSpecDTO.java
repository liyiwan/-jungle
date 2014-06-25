package com.qganlan.dto;

public class StockSpecDTO {
	private Long goodsId;
	private Long specId;
	private String specName;
	private Long flagId;
	private Long stock;
	private Long soldCount;
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
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public Long getFlagId() {
		return flagId;
	}
	public void setFlagId(Long flagId) {
		this.flagId = flagId;
	}
	
	public Long getSoldCount() {
		return soldCount;
	}
	
	public void setSoldCount(Long soldCount) {
		this.soldCount = soldCount;
	}
	
	public Long getPurchaseCount() {
		return purchaseCount;
	}
	
	public void setPurchaseCount(Long purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
}
