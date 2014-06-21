package com.qganlan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.MyConfigDao;
import com.qganlan.dao.TaobaoSessionKeyDao;
import com.qganlan.model.MyConfig;
import com.qganlan.model.TaobaoSessionKey;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.request.ItemsCustomGetRequest;
import com.taobao.api.request.SkusCustomGetRequest;
import com.taobao.api.response.ItemQuantityUpdateResponse;
import com.taobao.api.response.ItemsCustomGetResponse;
import com.taobao.api.response.SkusCustomGetResponse;

@Service("taobaoApiManager")
public class TaobaoApiManagerImpl implements TaobaoApiManager {
	
	private static String TAOBAO_API_URL = "http://gw.api.taobao.com/router/rest";
	private String defaultSeller = null;
	private String appKey = null;
	private String appSecret = null;
	private Map<String,String> sessionKeyMap = new HashMap<String,String>();
	private List<String> authorizedSellers = null;
	private TaobaoSessionKeyDao taobaoSessionKeyDao;
	private MyConfigDao myConfigDao;
	
	@Autowired
	public void setTaobaoSessionKeyDao(TaobaoSessionKeyDao taobaoSessionKeyDao) {
		this.taobaoSessionKeyDao = taobaoSessionKeyDao;
	}

	@Autowired
	public void setMyConfigDao(MyConfigDao myConfigDao) {
		this.myConfigDao = myConfigDao;
	}
	
	public String getAppKey() {
		if (appKey == null) {
			MyConfig config = myConfigDao.get("AppKey"); 
			if (config != null) {
				appKey = config.getConfigValue();
			}
		}
		return appKey;
	}
	
	public String getAppSecret() {
		if (appSecret == null) {
			MyConfig config = myConfigDao.get("AppSecret"); 
			if (config != null) {
				appSecret = config.getConfigValue();
			}
		}
		return appSecret;
	}
	
	public String getDefaultSeller() {
		if (defaultSeller == null) {
			MyConfig config = myConfigDao.get("DefaultSeller"); 
			if (config != null) {
				defaultSeller = config.getConfigValue();
			}
		}
		return defaultSeller;
	}
	
	public String getSessionKey(String nick) {
		String key = sessionKeyMap.get(nick);
		if (key == null) {
			TaobaoSessionKey taobaoSessionKey = taobaoSessionKeyDao.get(nick);
			if (taobaoSessionKey != null) {
				key = taobaoSessionKey.getSessionKey();
				sessionKeyMap.put(nick, key);
			}
		}
		return key;
	}
	
	public List<String> getAuthorizedSellers() {
		if (authorizedSellers == null) {
			List<TaobaoSessionKey> keys = taobaoSessionKeyDao.getAll();
			authorizedSellers = new ArrayList<String>();
			for (TaobaoSessionKey key : keys) {
				authorizedSellers.add(key.getNick());
			}
		}
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
				ItemsCustomGetResponse response = taobaoClient.execute(req , getSessionKey(getDefaultSeller()));
				return response.getItems();
			} catch (ApiException e) {
				e.printStackTrace();
			}
			retry = retry + 1;
		}
		return null;
	}

}
