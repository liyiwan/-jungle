package com.qganlan.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SQLQuery;
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

	@SuppressWarnings("unchecked")
	public List<Provider> getProviderListByGoodsId(Long goodsId) {
		SQLQuery q = getSession().createSQLQuery("SELECT DISTINCT G_Cfg_ProviderList.* FROM G_Cfg_ProviderList, G_Goods_Provider WHERE G_Cfg_ProviderList.ProviderID = G_Goods_Provider.ProviderID AND G_Goods_Provider.GoodsID = :goodsId");
		q.setLong("goodsId", goodsId);
		q.addEntity(Provider.class);
		return q.list();
	}
}
