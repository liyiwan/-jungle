package com.qganlan.dao;

import org.appfuse.dao.GenericDao;

import com.qganlan.model.Provider;

public interface ProviderDao extends GenericDao<Provider, Long> {

	Provider getProvider(Long providerId);

}
