package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_Goods_GoodsList")
public class Goods {
	
	private Long goodsId;
	private String goodsNo;
	private String goodsName;
	private Long flagId;
	private String remark;

    @Id
    @Column(name = "GoodsID", nullable = false)
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "GoodsNO")
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Column(name = "GoodsName")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "FlagID")
	public Long getFlagId() {
		return flagId;
	}

	public void setFlagId(Long flagId) {
		this.flagId = flagId;
	}

	@Column(name = "Remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
