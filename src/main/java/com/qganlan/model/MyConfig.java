package com.qganlan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "J_MyConfig")
public class MyConfig {
	private String configKey;
	private String configValue;
	
	@Id
	@Column(name = "ConfigKey")
	public String getConfigKey() {
		return configKey;
	}
	
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	
	@Column(name = "ConfigValue")
	public String getConfigValue() {
		return configValue;
	}
	
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}
