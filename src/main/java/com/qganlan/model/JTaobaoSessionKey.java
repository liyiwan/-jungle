package com.qganlan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_TAOBAO_SESSION_KEY")
public class JTaobaoSessionKey {
	private String nick;
	private String sessionKey;
	private Date createDate;
	private String refreshToken;
	private Integer isTmc;
	
	@Id
	@Column(name = "NICK", unique = true, nullable = false)
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Column(name = "SESSION_KEY")
	public String getSessionKey() {
		return sessionKey;
	}
	
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	@Column(name = "REFRESH_TOKEN")
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "IS_TMC")
	public Integer getIsTmc() {
		return isTmc;
	}

	public void setIsTmc(Integer isTmc) {
		this.isTmc = isTmc;
	}
}
