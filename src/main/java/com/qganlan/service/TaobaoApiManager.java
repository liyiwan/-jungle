package com.qganlan.service;

import java.util.List;

import com.taobao.api.domain.Item;
import com.taobao.api.domain.LogisticsCompany;
import com.taobao.api.domain.Sku;
import com.taobao.api.domain.TbkItem;
import com.taobao.api.domain.Trade;

public interface TaobaoApiManager {
	public static String TAOBAO_API_URL = "http://gw.api.taobao.com/router/rest";
	public String getAppKey();
	public String getAppSecret();
	public String getSessionKey(String nick);
	public List<String> getAuthorizedSellers();
	public List<Item> getItemByOuterId(String goodsNo);
	public List<Sku> getTaobaoSkusByOuterId(String outerId, String appkey, String appsecret, String sessionkey);
	public List<Item> getTaobaoItemsByOuterId(String outerId, String appkey, String appsecret, String sessionkey);
	public Item getTaobaoItemByNumIid(Long numIid, String appkey, String appsecret, String sessionkey);
	public com.taobao.api.domain.Trade getTaobaoTrade(Long tid, String appKey, String appSecret, String sessionKey);
	public Trade getTradeFullInfo(Long tid, String appKey, String appSecret,String sessionKey);
	public TbkItem getTbkItem(String numIid);
	public List<TbkItem> getTbkItems(List<String> numIids);
	public List<LogisticsCompany> getLogisticsCompanyList();
	public boolean sendTrade(Long tid, String subTid, String invoiceNo, String companyCode, String sessionKey);
	public List<String> getTmcAuthorizedSellers();
}
