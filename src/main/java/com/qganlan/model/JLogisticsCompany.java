package com.qganlan.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_LOGISTICS_COMPANY")
public class JLogisticsCompany implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	@Column(name = "CODE", nullable = false)
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	private Long id;
	
	@Id
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	private String name;
	
	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	private String regMailNo;
	
	@Column(name = "REG_MAIL_NO")
	public String getRegMailNo() {
		return this.regMailNo;
	}
	
	public void setRegMailNo(String regMailNo) {
		this.regMailNo = regMailNo;
	}
	
	private Integer useFlag;

	@Column(name = "USE_FLAG")
	public Integer getUseFlag() {
		return useFlag;
	}
	
	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}
}
