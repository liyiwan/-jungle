package com.qganlan.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "G_Goods_GoodsList")
@Indexed
@XmlRootElement
public class Goods extends BaseObject implements Serializable {
	private static final long serialVersionUID = 6076627298809691646L;
	
	private Long goodsId;
	private String goodsNo;
	private String goodsName;
	private Long stock;
	private Long sellCountMonth;
	private Long providerId;

    @Id
    @DocumentId
    @Column(name = "GoodsID", nullable = false)
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "GoodsNO")
	@Field
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Column(name = "GoodsName")
	@Field
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Transient
	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	@Transient
	public Long getSellCountMonth() {
		return sellCountMonth;
	}

	public void setSellCountMonth(Long sellCountMonth) {
		this.sellCountMonth = sellCountMonth;
	}

	@Transient
	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
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
        if (!(o instanceof Goods)) {
            return false;
        }

        final Goods goods = (Goods) o;
        return goodsId.equals(goods.getGoodsId());
	}

	public int hashCode() {
		return goodsId.hashCode();
	}

}
