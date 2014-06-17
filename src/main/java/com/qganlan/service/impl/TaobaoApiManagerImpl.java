package com.qganlan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsCustomGetRequest;
import com.taobao.api.response.ItemsCustomGetResponse;

@Service("taobaoApiManager")
public class TaobaoApiManagerImpl implements TaobaoApiManager {
	
	private static String TAOBAO_API_URL = "http://gw.api.taobao.com/router/rest";
	private String defaultSeller = "小脚丫商城";
	private String appKey = "12421252";
	private String appSecret = "81e9eb1005ce741ee23cc3081b1c9fd0";
	private Map<String,String> sessionKeyMap = new HashMap<String,String>();
	private List<String> authorizedSellers = new ArrayList<String>();
	
	String[][] keys = {
			{ "dwf306", "6101c09193bd6f93e1ffc62f9441e055e086d37f19405c7136835512","2014-02-22" }, 
			{ "janny0293", "610170051ff5c4bdb615844ff158c90c1e8651fb5125fd169955725", "2013-12-07" }, 
			{ "lingxige", "6102826210c4c88696a314d2a4e8cf210b11ca52ff4ef35167962435", "2013-12-07" }, 
			{ "狐狐屋", "6102528c37ee8f46a43f99cf2ee5612654cd18696abf0aa143185909", "2014-05-10" },
			{ "小脚丫商城", "6101f151eb6f16f174a1fd11e6c2adcf250cf34a197d567268631276", "2013-07-18" } };
	
	@Inject
    private Logger logger;
	
	@PostConstruct
	public void loadTaobaoSessionKeys() {
		for (String[] key : keys) {
			sessionKeyMap.put(key[0], key[1]);
			authorizedSellers.add(key[0]);
		}
	}

	public String getAppKey() {
		return appKey;
	}
	
	public String getAppSecret() {
		return appSecret;
	}
	
	public String getSessionKey(String nick) {
		return sessionKeyMap.get(nick);
	}
	
	public List<String> getAuthorizedSellers() {
		return authorizedSellers;
	}

	public List<Item> getItemByOuterId(String goodsNo) {
		TaobaoClient taobaoClient = new DefaultTaobaoClient(TAOBAO_API_URL, getAppKey(), getAppSecret(), "xml");
		ItemsCustomGetRequest req = new ItemsCustomGetRequest();
		req.setOuterId(goodsNo);
		req.setFields("detail_url,num_iid,title,nick,type,desc,sku,props_name,created,property_alias,cid,props,pic_url,num,list_time,delist_time,price,modified,approve_status,item_img,prop_img,outer_id,input_pids,input_str,volume");
		int retry = 0;
		while (retry < 3) {
			try {
				ItemsCustomGetResponse response = taobaoClient.execute(req , getSessionKey(defaultSeller));
				if (response.isSuccess()) {
					return response.getItems();
				} else {
					break;
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
			retry = retry + 1;
		}
		return CollectionFactory.newList();
	}

}
