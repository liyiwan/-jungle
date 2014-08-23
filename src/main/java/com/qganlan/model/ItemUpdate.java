package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_ItemUpdate")
public class ItemUpdate {
	private Long numIid;
	private String nick;
	private int tryCount;

	@Id
    @Column(name = "NumIid", nullable = false)
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	
	@Column(name = "nick")
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Column(name = "tryCount")
	public int getTryCount() {
		return tryCount;
	}
	public void setTryCount(int tryCount) {
		this.tryCount = tryCount;
	}
}
