package com.qganlan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_API_SysMatch")
public class ApiSysMatch {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Numiid")
	private String numIid;
	
	@Column(name = "Skuid")
	private String skuId;
	
	@Column(name = "GoodsID")
	private Long goodsId;
	
	@Column(name = "SpecID")
	private Long specId;
	
	@Column(name = "TBName")
	private String tbName;
	
	@Column(name = "TBSku")
	private String tbSku;
	
	@Column(name = "TBOuterID")
	private String tbOuterId;
	
	@Column(name = "SKUOuterID")
	private String skuOuterId;
	
	@Column(name = "IsSys")
	private Long isSys;
	
	@Column(name = "SysLog")
	private String sysLog;
	
	@Column(name = "ShopID")
	private Long shopId;
	
	@Column(name = "bTBGoods")
	private Long tbGoods;
	
	@Column(name = "updatetime")
	private Date updateTime;
	
	@Column(name = "SysCount")
	private Integer sysCount;
	
	@Column(name = "SysGoodsType")
	private String sysGoodsType;
	
	@Column(name = "bFixNum")
	private Long fixNumFlag;
	
	@Column(name = "FixNum")
	private Long fixNum;
	
	@Column(name = "bVirNum")
	private Long virNumFlag;
	
	@Column(name = "VirNumBase")
	private Long virNumBase;
	
	@Column(name = "VirNumTop")
	private Long virNumTop;
	
	@Column(name = "VirNumInc")
	private Long virNumInc;
	
	@Column(name = "bstop")
	private Long stopFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

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

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getTbSku() {
		return tbSku;
	}

	public void setTbSku(String tbSku) {
		this.tbSku = tbSku;
	}

	public String getTbOuterId() {
		return tbOuterId;
	}

	public void setTbOuterId(String tbOuterId) {
		this.tbOuterId = tbOuterId;
	}

	public String getSkuOuterId() {
		return skuOuterId;
	}

	public void setSkuOuterId(String skuOuterId) {
		this.skuOuterId = skuOuterId;
	}

	public Long getIsSys() {
		return isSys;
	}

	public void setIsSys(Long isSys) {
		this.isSys = isSys;
	}

	public String getSysLog() {
		return sysLog;
	}

	public void setSysLog(String sysLog) {
		this.sysLog = sysLog;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getTbGoods() {
		return tbGoods;
	}

	public void setTbGoods(Long tbGoods) {
		this.tbGoods = tbGoods;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSysCount() {
		return sysCount;
	}

	public void setSysCount(Integer sysCount) {
		this.sysCount = sysCount;
	}

	public String getSysGoodsType() {
		return sysGoodsType;
	}

	public void setSysGoodsType(String sysGoodsType) {
		this.sysGoodsType = sysGoodsType;
	}

	public Long getFixNumFlag() {
		return fixNumFlag;
	}

	public void setFixNumFlag(Long fixNumFlag) {
		this.fixNumFlag = fixNumFlag;
	}

	public Long getFixNum() {
		return fixNum;
	}

	public void setFixNum(Long fixNum) {
		this.fixNum = fixNum;
	}

	public Long getVirNumFlag() {
		return virNumFlag;
	}

	public void setVirNumFlag(Long virNumFlag) {
		this.virNumFlag = virNumFlag;
	}

	public Long getVirNumBase() {
		return virNumBase;
	}

	public void setVirNumBase(Long virNumBase) {
		this.virNumBase = virNumBase;
	}
	
	public Long getVirNumTop() {
		return virNumTop;
	}

	public void setVirNumTop(Long virNumTop) {
		this.virNumTop = virNumTop;
	}

	public Long getVirNumInc() {
		return virNumInc;
	}

	public void setVirNumInc(Long virNumInc) {
		this.virNumInc = virNumInc;
	}

	public Long getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(Long stopFlag) {
		this.stopFlag = stopFlag;
	}

	
}
