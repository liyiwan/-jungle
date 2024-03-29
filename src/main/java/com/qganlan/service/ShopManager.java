package com.qganlan.service;

import java.util.List;

import com.qganlan.model.Shop;

public interface ShopManager {

	List<Shop> getTaobaoShopList();

	Shop getShop(Long shopId);

	Shop getShopByNick(String nick);

}
