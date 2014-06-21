package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_Cfg_ProviderClass")
public class ProviderClass {

	private Long recId;
	private Long classId;
	private String className;
	private Long orderPos;
	
	@Id
	@Column(name = "RecId", unique = true, nullable = false)
	public Long getRecId() {
		return recId;
	}
	public void setRecId(Long recId) {
		this.recId = recId;
	}
	
	@Column(name = "ClassID")
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	
	@Column(name = "ClassName")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Column(name = "OrderPos")
	public Long getOrderPos() {
		return orderPos;
	}
	public void setOrderPos(Long orderPos) {
		this.orderPos = orderPos;
	}
}
