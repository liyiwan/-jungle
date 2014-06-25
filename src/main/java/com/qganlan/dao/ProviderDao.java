package com.qganlan.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.model.Provider;
import com.qganlan.model.ProviderClass;

public interface ProviderDao extends GenericDao<Provider, Long> {

	Provider getProvider(Long providerId);
	List<ProviderClass> getProviderClassList();
	List<Provider> getProviderListByGoodsId(Long goodsId);

}
