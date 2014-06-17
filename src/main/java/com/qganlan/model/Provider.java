package com.qganlan.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "G_Cfg_ProviderList")
@Indexed
@XmlRootElement
public class Provider extends BaseObject implements Serializable {

	private static final long serialVersionUID = 6207419563232496014L;

	private Long providerId;
	private String providerName;
	private String linkMan;
	private String tel;
	private String qq;
	private String adr;
	private String email;
	private String webSite;
	private String remark;
	
	@Id
	@Column(name = "ProviderID", unique = true, nullable = false)
	@DocumentId
	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	@Column(name = "ProviderName", length = 50)
	@Field
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	@Column(name = "LinkMan", length = 20)
	@Field
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "Tel", length = 50)
	@Field
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "QQ", length = 50)
	@Field
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "Adr", length = 120)
	@Field
	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	@Column(name = "Email", length = 50)
	@Field
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "WebSite", length = 50)
	@Field
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Column(name = "Remark", length = 500)
	@Field
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
		.append("providerId", this.providerId)
		.append("providerName", this.providerName);
	return sb.toString();
	}

	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof Provider)) {
            return false;
        }

        final Provider provider = (Provider) o;
        return providerId.equals(provider.getProviderId());
	}

	public int hashCode() {
		return providerId.hashCode();
	}

}
