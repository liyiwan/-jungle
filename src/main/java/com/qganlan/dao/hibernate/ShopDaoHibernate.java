package com.qganlan.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.ShopDao;
import com.qganlan.model.Shop;

@Repository("shopDao")
public class ShopDaoHibernate extends GenericDaoHibernate<Shop, Long> implements ShopDao {

	public ShopDaoHibernate() {
		super(Shop.class);
	}

	@SuppressWarnings("unchecked")
	public List<Shop> getTaobaoShopList() {
		return getSession().createQuery("FROM Shop WHERE shopType = '淘宝网'").list();
	}

}
