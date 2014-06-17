package com.qganlan.service;

import java.util.List;

import com.taobao.api.domain.Item;

public interface TaobaoApiManager {
	public String getAppKey();
	public String getAppSecret();
	public String getSessionKey(String nick);
	public List<String> getAuthorizedSellers();
	public List<Item> getItemByOuterId(String goodsNo);
}
