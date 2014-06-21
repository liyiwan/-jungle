package com.qganlan.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.ProviderDao;
import com.qganlan.model.Provider;
import com.qganlan.model.ProviderClass;

@Repository("providerDao")
public class ProviderDaoHibernate extends GenericDaoHibernate<Provider, Long> implements ProviderDao {

	public ProviderDaoHibernate() {
		super(Provider.class);
	}

	public Provider getProvider(Long providerId) {
		return (Provider) getSession().get(Provider.class, providerId);
       
	}

	@SuppressWarnings("unchecked")
	public List<ProviderClass> getProviderClassList() {
		return getSession().createQuery("FROM ProviderClass").list();
	}

}
