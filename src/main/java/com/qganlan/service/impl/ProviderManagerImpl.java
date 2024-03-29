package com.qganlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.ProviderDao;
import com.qganlan.model.Provider;
import com.qganlan.model.ProviderClass;
import com.qganlan.service.ProviderManager;

@Service("providerManager")
public class ProviderManagerImpl implements ProviderManager {

	private ProviderDao providerDao;
	
	@Autowired
	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
	}
	
	public Provider getProvider(Long providerId) {
		return providerDao.getProvider(providerId);
	}

	public List<ProviderClass> getProviderClassList() {
		return providerDao.getProviderClassList();
	}

	public List<Provider> getProviderListByGoodsId(Long goodsId) {
		return providerDao.getProviderListByGoodsId(goodsId);
	}

}
