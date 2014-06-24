package com.qganlan.dto;

public class GoodsSpecDTO {
	private Long goodsId;
	private Long specId;
	private String specCode;
	private String specName;
	private Long flagId;
	
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
	
}
