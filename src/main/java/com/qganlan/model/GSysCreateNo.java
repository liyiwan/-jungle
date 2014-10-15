package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "G_Sys_CreateNO")
public class GSysCreateNo {

	private Long recId;
	private Long curNo;
	private String tableName;
	private String fieldName;

	public GSysCreateNo() {
	}

	public GSysCreateNo(Long recId) {
		this.recId = recId;
	}

	@Id
	@Column(name = "RecID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecId() {
		return this.recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	@Column(name = "curNO")
	public Long getCurNo() {
		return this.curNo;
	}

	public void setCurNo(Long curNo) {
		this.curNo = curNo;
	}

	@Column(name = "_TableName", length = 50)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "_FieldName", length = 50)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
