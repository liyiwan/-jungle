package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "J_TaobaoItemSku")
public class TaobaoItemSku {
	
	private Long recId;
	private Long skuId;
	private TaobaoItem taobaoItem;
	private String created;
	private String modified;
	private String outerId;
	private String price;
	private String properties;
	private String propertiesName;
	private Long quantity;
	private String status;
	
	@Column(name = "RecId")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}
	
	@Column(name = "SkuId")
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	@ManyToOne
	@JoinColumn(name="NumIid", referencedColumnName="numIid")
	public TaobaoItem getTaobaoItem() {
		return taobaoItem;
	}

	public void setTaobaoItem(TaobaoItem taobaoItem) {
		this.taobaoItem = taobaoItem;
	}
	
	@Column(name = "Created")
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	@Column(name = "Modified")
	public String getModified() {
		return modified;
	}
	
	public void setModified(String modified) {
		this.modified = modified;
	}
	
	@Column(name = "OuterId")
	public String getOuterId() {
		return outerId;
	}
	
	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	
	@Column(name = "Price")
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Column(name = "Properties")
	public String getProperties() {
		return properties;
	}
	
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	@Column(name = "PropertiesName")
	public String getPropertiesName() {
		return propertiesName;
	}
	
	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}
	
	@Column(name = "Quantity")
	public Long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "Status")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	private boolean isStale;
	
	@Transient
	public boolean isStale() {
		return isStale;
	}
	
	public void setStale(boolean isStale) {
		this.isStale = isStale;
	}
}
