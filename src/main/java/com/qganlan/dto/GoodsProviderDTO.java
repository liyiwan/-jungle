package com.qganlan.dto;

import java.math.BigDecimal;

public class GoodsProviderDTO {
    
    private Long recId;
    
    public Long getRecId() {
        return recId;
    }
    
    public void setRecId(Long recId) {
        this.recId = recId;
    }
    
    private Long goodsId;
    
    public Long getGoodsId() {
        return goodsId;
    }
    
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    
    private Long providerId;
    
    public Long getProviderId() {
        return providerId;
    }
    
    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
    
    private String providerName;
    
    public String getProviderName() {
        return providerName;
    }
    
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    
    private String adr;
    
    public String getAdr() {
        return adr;
    }
    
    public void setAdr(String adr) {
        this.adr = adr;
    }
    
    private String website;
    
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    private BigDecimal price;
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
