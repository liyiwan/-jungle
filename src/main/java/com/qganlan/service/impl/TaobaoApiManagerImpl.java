package com.qganlan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.taobao.api.domain.TbkItem;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemsCustomGetRequest;
import com.taobao.api.request.SkusCustomGetRequest;
import com.taobao.api.request.TbkItemsConvertRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemsCustomGetResponse;
import com.taobao.api.response.SkusCustomGetResponse;
import com.taobao.api.response.TbkItemsConvertResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;

@Service("taobaoApiManager")
public class TaobaoApiManagerImpl implements TaobaoApiManager {
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

	public List<Sku> getTaobaoSkusByOuterId(String outerId, String appkey, String appsecret, String sessionkey) {
		TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		SkusCustomGetRequest req = new SkusCustomGetRequest();
		req.setOuterId(outerId);
		req.setFields("properties_name,sku_id,num_iid,properties,quantity,price,outer_id,created,modified,status");
		try {
			SkusCustomGetResponse response = client.execute(req , sessionkey);
			if (!response.isSuccess()) {
				System.out.println("ERROR:" + response.getErrorCode() + " " + response.getMsg());
			} 
			return response.getSkus();
		} catch (ApiException e) {
			e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		}
		return null;
	}

	public List<Item> getTaobaoItemsByOuterId(String outerId, String appkey, String appsecret, String sessionkey) {
		TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		ItemsCustomGetRequest req = new ItemsCustomGetRequest();
		req.setOuterId(outerId);
		req.setFields("detail_url,num_iid,title,nick,type,desc,sku,props_name,created,property_alias,cid,props,pic_url,num,list_time,delist_time,price,modified,approve_status,item_img,prop_img,outer_id,input_pids,input_str,volume");
		try {
			ItemsCustomGetResponse response = client.execute(req , sessionkey);
			if (!response.isSuccess()) {
				System.out.println("ERROR:" + response.getErrorCode() + " " + response.getMsg());
			} 
			return response.getItems();
		} catch (ApiException e) {
			e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		}
		return null;
	}

    public Item getTaobaoItemByNumIid(Long numIid, String appkey, String appsecret, String sessionkey) {
    	TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		ItemGetRequest itemGetRequest = new ItemGetRequest();
    	itemGetRequest.setFields("detail_url,num_iid,title,nick,type,desc,sku,props_name,created,property_alias,cid,props,pic_url,num,list_time,delist_time,price,modified,approve_status,item_img,prop_img,outer_id,input_pids,input_str,volume");
    	itemGetRequest.setNumIid(numIid);
    	Item item = null;
    	int retryCount = 0;
    	while (retryCount < 3) {
    		try {
	    		ItemGetResponse itemGetResponse = client.execute(itemGetRequest, sessionkey);
	    		item = itemGetResponse.getItem();
	    		break;
	    	} catch (ApiException e) {
	    		e.printStackTrace();
	    	}
    		retryCount = retryCount + 1;
    	}
    	return item;
    }

	public Trade getTaobaoTrade(Long tid, String appkey, String appsecret, String sessionkey) {
		TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		TradeGetRequest req = new TradeGetRequest();
    	req.setFields("tid,seller_nick,buyer_nick,title,type,created,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,buyer_message,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,orders");
    	req.setTid(tid);
    	com.taobao.api.domain.Trade taobaoTrade = null;
    	int retryCount = 0;
    	while (retryCount < 3) {
        	try {
    			TradeGetResponse response = client.execute(req , sessionkey);
    			taobaoTrade = response.getTrade();
    			break;
    		} catch (ApiException e) {
    			e.printStackTrace();
    		}
        	retryCount++;
    	}

    	return taobaoTrade;
	}
	
	public Trade getTradeFullInfo(Long tid, String appKey, String appSecret, String sessionKey) {
		TaobaoClient taobaoClient = new DefaultTaobaoClient(TAOBAO_API_URL, appKey, appSecret);
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		req.setFields("seller_nick,buyer_nick,title,type,created,tid,seller_rate,status,payment,adjust_fee,has_post_fee,post_fee,total_fee,pay_time,buyer_message,receiver_address,receiver_name,receiver_mobile,receiver_phone,receiver_state,receiver_city,receiver_district,receiver_zip,orders");
		req.setTid(tid);
		Trade trade = null;
		int tryCount = 0;
		while (tryCount < 10) {
			tryCount = tryCount + 1;
			try {
				TradeFullinfoGetResponse response = taobaoClient.execute(req , sessionKey);
				trade = response.getTrade();
				break;
			} catch (ApiException e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		return trade;
	}

	@Override
	public TbkItem getTbkItem(String numIid) {
		TaobaoClient taobaoClient = new DefaultTaobaoClient(TAOBAO_API_URL, appKey, appSecret);
		TbkItemsConvertRequest req = new TbkItemsConvertRequest();
		req.setFields("click_url,item_url,nick,num_iid,pic_url");
		req.setNick("lovebluecore");
		req.setNumIids(numIid);
		req.setReferType(1L);
		int tryCount = 0;
		while (tryCount < 3) {
			tryCount++;
			try {
				TbkItemsConvertResponse response = taobaoClient.execute(req);
				List<TbkItem> tbkItems = response.getTbkItems();
				if (tbkItems != null && tbkItems.size() == 1) {
					return tbkItems.get(0);
				} else {
					return null;
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public List<TbkItem> getTbkItems(List<String> numIids) {
		List<TbkItem> rtnTbkItems = new ArrayList<TbkItem>();
		String numIidsStr = "";
		int count = 0;
		Iterator<String> iter = numIids.iterator();
		while (iter.hasNext()) {
			String numIid = iter.next();
			System.out.println("numIid:" + numIid);
			numIidsStr = numIidsStr + numIid;
			count++;
			if (count >= 30 || !iter.hasNext()) {
				TaobaoClient taobaoClient = new DefaultTaobaoClient(TAOBAO_API_URL, "21675427", "7b12462c06fe0b5eab021ea79a207b50");
				TbkItemsConvertRequest req = new TbkItemsConvertRequest();
				req.setFields("click_url,item_url,nick,num_iid,pic_url");
				req.setNick("lovebluecore");
				System.out.println("numIidsStr:" + numIidsStr);
				req.setNumIids(numIidsStr);
				req.setReferType(1L);
				int tryCount = 0;
				while (tryCount < 3) {
					tryCount++;
					try {
						TbkItemsConvertResponse response = taobaoClient.execute(req);
						System.out.println("errorCode：" + response.getErrorCode());
						System.out.println("msg：" + response.getMsg());
						System.out.println("返回淘宝客链接数量：" + response.getTotalResults());
						List<TbkItem> tbkItems = response.getTbkItems();
						if (tbkItems != null && tbkItems.size() > 0) {
							rtnTbkItems.addAll(tbkItems);
						} else {
							break;
						}
					} catch (ApiException e) {
						e.printStackTrace();
					}
				}
				count = 0;
				numIidsStr = "";
			} else {
				if (iter.hasNext()) {
					numIidsStr = numIidsStr + ",";
				}
			}
		}
		return rtnTbkItems;
	}

}
