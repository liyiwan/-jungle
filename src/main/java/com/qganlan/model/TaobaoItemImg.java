package com.qganlan.model;

import java.util.Date;

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
@Table(name = "J_TaobaoItemImg")
public class TaobaoItemImg {

	private Long recId;
	private Long id;
	private TaobaoItem taobaoItem;
	private Date created;
	private Long position;
	private String url;
	
	@Column(name = "RecId")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	@ManyToOne
	@JoinColumn(name="NumIid", referencedColumnName="numIid")
	public TaobaoItem getTaobaoItem() {
		return taobaoItem;
	}

	public void setTaobaoItem(TaobaoItem taobaoItem) {
		this.taobaoItem = taobaoItem;
	}

	@Column(name="Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="Created")
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	@Column(name="Pos")
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	
	@Column(name="Url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
