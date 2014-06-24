package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_Cfg_ShopList")
public class Shop {
	private Long shopId;
	private String shopName;
	private String shopType;
	private String sellNick;
	
	@Id
	@Column(name = "ShopID", nullable = false)
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "ShopName")
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Column(name = "ShopType")
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	
	@Column(name = "SellNick")
	public String getSellNick() {
		return sellNick;
	}
	public void setSellNick(String sellNick) {
		this.sellNick = sellNick;
	}
}
