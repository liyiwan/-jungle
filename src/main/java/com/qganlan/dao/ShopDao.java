package com.qganlan.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.model.Shop;

public interface ShopDao extends GenericDao<Shop, Long> {

	List<Shop> getTaobaoShopList();

	Shop getShopByNick(String nick);

}
