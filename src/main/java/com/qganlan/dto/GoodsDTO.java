package com.qganlan.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GoodsDTO implements Serializable {

	private static final long serialVersionUID = 4789512455855965526L;

	private Long goodsId;
	private String goodsNo;
	private String goodsName;
	private Long stock;
	private Long sellCountMonth;
	private Long providerId;
	private String picPath;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
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

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getSellCountMonth() {
		return sellCountMonth;
	}

	public void setSellCountMonth(Long sellCountMonth) {
		this.sellCountMonth = sellCountMonth;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			.append("goodsId", this.goodsId)
			.append("goodsNo", this.goodsNo)
			.append("goodsName", this.goodsName);
		return sb.toString();
	}

	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof GoodsDTO)) {
            return false;
        }

        final GoodsDTO goods = (GoodsDTO) o;
        return goodsId.equals(goods.getGoodsId());
	}

	public int hashCode() {
		return goodsId.hashCode();
	}

}
