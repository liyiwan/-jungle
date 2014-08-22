package com.qganlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.ShopDao;
import com.qganlan.model.Shop;
import com.qganlan.service.ShopManager;

@Service("shopManager")
public class ShopManagerImpl implements ShopManager {
	
	private List<Shop> taobaoShopList;

	@Autowired
	private ShopDao shopDao;
	
	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}
	
	public List<Shop> getTaobaoShopList() {
		if (taobaoShopList == null) {
			taobaoShopList = shopDao.getTaobaoShopList();
		}
		return taobaoShopList;
	}

	public Shop getShop(Long shopId) {
		return shopDao.get(shopId);
	}

	public Shop getShopByNick(String nick) {
		return shopDao.getShopByNick(nick);
	}

}
