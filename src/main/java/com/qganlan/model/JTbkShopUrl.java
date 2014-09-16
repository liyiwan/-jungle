package com.qganlan.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_TBK_SHOP_URL")
public class JTbkShopUrl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nick;

	@Id
	@Column(name = "NICK", nullable = false)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	private String tbkShopUrl;

	@Column(name = "TBK_SHOP_URL")
	public String getTbkShopUrl() {
		return tbkShopUrl;
	}

	public void setTbkShopUrl(String tbkShopUrl) {
		this.tbkShopUrl = tbkShopUrl;
	}

}
