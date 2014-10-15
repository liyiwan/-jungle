package com.qganlan.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_API_TradeGoods")
public class GApiTradeGoods {

	private int recId;
	private Long billId;
	private String tradeGoodsNo;
	private String tradeGoodsName;
	private String tradeGoodsSpec;
	private Long goodsCount;
	private BigDecimal price;
	private BigDecimal discountMoney;
	private String otherGoods;
	private String remark;
	private Integer recNo;
	private Integer curStatus;
	private Long goodsId;
	private Long specId;
	private String goodsSpec;
	private Boolean bfit;
	private Boolean isOversold;
	private String cstatus;
	private String oid;

	public GApiTradeGoods() {
	}

	@Id
	@Column(name = "RecID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getRecId() {
		return this.recId;
	}

	public void setRecId(int recId) {
		this.recId = recId;
	}

	@Column(name = "BillID")
	public Long getBillId() {
		return this.billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	@Column(name = "TradeGoodsNO", length = 50)
	public String getTradeGoodsNo() {
		return this.tradeGoodsNo;
	}

	public void setTradeGoodsNo(String tradeGoodsNo) {
		this.tradeGoodsNo = tradeGoodsNo;
	}

	@Column(name = "TradeGoodsName", length = 120)
	public String getTradeGoodsName() {
		return this.tradeGoodsName;
	}

	public void setTradeGoodsName(String tradeGoodsName) {
		this.tradeGoodsName = tradeGoodsName;
	}

	@Column(name = "TradeGoodsSpec", length = 50)
	public String getTradeGoodsSpec() {
		return this.tradeGoodsSpec;
	}

	public void setTradeGoodsSpec(String tradeGoodsSpec) {
		this.tradeGoodsSpec = tradeGoodsSpec;
	}

	@Column(name = "GoodsCount", scale = 4)
	public Long getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	@Column(name = "Price", scale = 4)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "DiscountMoney", scale = 4)
	public BigDecimal getDiscountMoney() {
		return this.discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	@Column(name = "OtherGoods", length = 50)
	public String getOtherGoods() {
		return this.otherGoods;
	}

	public void setOtherGoods(String otherGoods) {
		this.otherGoods = otherGoods;
	}

	@Column(name = "Remark", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "RecNO")
	public Integer getRecNo() {
		return this.recNo;
	}

	public void setRecNo(Integer recNo) {
		this.recNo = recNo;
	}

	@Column(name = "curStatus")
	public Integer getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(Integer curStatus) {
		this.curStatus = curStatus;
	}

	@Column(name = "GoodsID")
	public Long getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "SpecID")
	public Long getSpecId() {
		return this.specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	@Column(name = "GoodsSpec", length = 50)
	public String getGoodsSpec() {
		return this.goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	@Column(name = "bFit")
	public Boolean getBfit() {
		return this.bfit;
	}

	public void setBfit(Boolean bfit) {
		this.bfit = bfit;
	}

	@Column(name = "is_oversold")
	public Boolean getIsOversold() {
		return this.isOversold;
	}

	public void setIsOversold(Boolean isOversold) {
		this.isOversold = isOversold;
	}

	@Column(name = "cStatus", length = 50)
	public String getCstatus() {
		return this.cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	@Column(name = "oid")
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	

}
