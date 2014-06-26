package com.qganlan.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "G_Trade_GoodsList")
public class TradeGoods {
	
	private Long recId;
	private Long recNo;
	private Long TradeId;
	private Long goodsId;
	private Long specId;
	private Long sellCount;
	private BigDecimal sellPrice;

	private Long giftFlag;
	private BigDecimal score;
	private String remark;
	private String originalNo;
	private String unit;
	private String shopName;
	private BigDecimal goodsCost;
	private BigDecimal backCount;
	private Integer giftType;
	private String giftActivStep;
	private Long fitFlag;
	private String tradeName;
	private String tradeSpec;
	private String tradeGoodsNo;
	private Date sayHelloDate;
	private Long sayHelloFlag;
	private Integer pickCount;


	@Id
	@Column(name = "RecID", unique = true, nullable = false)
	public Long getRecId() {
		return this.recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	@Column(name = "RecNO")
	public Long getRecNo() {
		return this.recNo;
	}

	public void setRecNo(Long recNo) {
		this.recNo = recNo;
	}

	@Column(name = "TradeID")
	public Long getTradeId() {
		return TradeId;
	}

	public void setTradeId(Long tradeId) {
		TradeId = tradeId;
	}

	@Column(name = "GoodsID", nullable = false)
	public Long getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "SpecID", nullable = false)
	public Long getSpecId() {
		return this.specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	@Column(name = "SellCount", scale = 4)
	public Long getSellCount() {
		return this.sellCount;
	}

	public void setSellCount(Long sellCount) {
		this.sellCount = sellCount;
	}

	@Column(name = "SellPrice", scale = 4)
	public BigDecimal getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	@Column(name = "bGift")
	public Long getGiftFlag() {
		return this.giftFlag;
	}

	public void setGiftFlag(Long giftFlag) {
		this.giftFlag = giftFlag;
	}

	@Column(name = "Score", scale = 4)
	public BigDecimal getScore() {
		return this.score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Column(name = "Remark", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OriginalNO", length = 50)
	public String getOriginalNo() {
		return this.originalNo;
	}

	public void setOriginalNo(String originalNo) {
		this.originalNo = originalNo;
	}

	@Column(name = "Unit", length = 50)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "ShopName", length = 20)
	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "GoodsCost", scale = 4)
	public BigDecimal getGoodsCost() {
		return this.goodsCost;
	}

	public void setGoodsCost(BigDecimal goodsCost) {
		this.goodsCost = goodsCost;
	}

	@Column(name = "BackCount", scale = 4)
	public BigDecimal getBackCount() {
		return this.backCount;
	}

	public void setBackCount(BigDecimal backCount) {
		this.backCount = backCount;
	}

	@Column(name = "GiftType")
	public Integer getGiftType() {
		return this.giftType;
	}

	public void setGiftType(Integer giftType) {
		this.giftType = giftType;
	}

	@Column(name = "GiftActivStep", length = 20)
	public String getGiftActivStep() {
		return this.giftActivStep;
	}

	public void setGiftActivStep(String giftActivStep) {
		this.giftActivStep = giftActivStep;
	}

	@Column(name = "bFit")
	public Long getFitFlag() {
		return this.fitFlag;
	}

	public void setFitFlag(Long fitFlag) {
		this.fitFlag = fitFlag;
	}

	@Column(name = "TradeName", length = 120)
	public String getTradeName() {
		return this.tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	@Column(name = "TradeSpec", length = 50)
	public String getTradeSpec() {
		return this.tradeSpec;
	}

	public void setTradeSpec(String tradeSpec) {
		this.tradeSpec = tradeSpec;
	}

	@Column(name = "TradeGoodsNO", length = 50)
	public String getTradeGoodsNo() {
		return this.tradeGoodsNo;
	}

	public void setTradeGoodsNo(String tradeGoodsNo) {
		this.tradeGoodsNo = tradeGoodsNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SayHelloDate", length = 23)
	public Date getSayHelloDate() {
		return this.sayHelloDate;
	}

	public void setSayHelloDate(Date sayHelloDate) {
		this.sayHelloDate = sayHelloDate;
	}

	@Column(name = "bSayHello")
	public Long getSayHelloFlag() {
		return this.sayHelloFlag;
	}

	public void setSayHelloFlag(Long sayHelloFlag) {
		this.sayHelloFlag = sayHelloFlag;
	}

	@Column(name = "PickCount")
	public Integer getPickCount() {
		return this.pickCount;
	}

	public void setPickCount(Integer pickCount) {
		this.pickCount = pickCount;
	}

	private BigDecimal sellTotal;

	@Column(name = "SellTotal")
	public BigDecimal getSellTotal() {
		return sellTotal;
	}

	public void setSellTotal(BigDecimal sellTotal) {
		this.sellTotal = sellTotal;
	}
	
	private Long produceId;

	@Column(name = "ProduceID")
	public Long getProduceId() {
		return produceId;
	}

	public void setProduceId(Long produceId) {
		this.produceId = produceId;
	}
	
	private Long positionsId;

	@Column(name = "PositionsID")
	public Long getPositionsId() {
		return positionsId;
	}

	public void setPositionsId(Long positionsId) {
		this.positionsId = positionsId;
	}

}
