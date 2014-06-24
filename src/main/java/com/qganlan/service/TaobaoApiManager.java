package com.qganlan.service;

import java.util.List;

import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;

public interface TaobaoApiManager {
	public String getAppKey();
	public String getAppSecret();
	public String getSessionKey(String nick);
	public List<String> getAuthorizedSellers();
	public List<Item> getItemByOuterId(String goodsNo);
	public List<Sku> getTaobaoSkusByOuterId(String outerId, String appkey, String appsecret, String sessionkey);
	public List<Item> getTaobaoItemsByOuterId(String outerId, String appkey, String appsecret, String sessionkey);
	public Item getTaobaoItemByNumIid(Long numIid, String appkey, String appsecret, String sessionkey);
}
