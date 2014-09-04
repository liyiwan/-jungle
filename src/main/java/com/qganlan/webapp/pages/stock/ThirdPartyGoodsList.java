package com.qganlan.webapp.pages.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.common.TbkUtil;
import com.qganlan.dto.ThirdPartyGoods;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.TaobaoApiManager;

public class ThirdPartyGoodsList {
	
	@Property
	private List<ThirdPartyGoods> thirdPartyGoodsList;
	
	@Property
	private ThirdPartyGoods thirdPartyGoods;
	
	@Inject
	private GoodsManager goodsManager;
	
	@Inject
	private TaobaoApiManager taobaoApiManager;
	
	@Property
	private HashMap<String, String> clickUrls;
	
	@Property
	private Map.Entry<String, String> clickUrlEntry;
	
	public void setUpRender() {
		thirdPartyGoodsList = goodsManager.getTirdPartyGoodsList();
		clickUrls = new HashMap<String, String>();
		for (int x = 0; x < TbkUtil.clickUrls.length; x++) {
			clickUrls.put(TbkUtil.clickUrls[x][0], TbkUtil.clickUrls[x][1]);
		}
	}

	public String getItemUrl() {
		String tradeGoodsNo = thirdPartyGoods.getTradeGoodsNO();
		String[] id = tradeGoodsNo.split("-");
		if (id.length == 2) { 
			return "http://item.taobao.com/item.htm?id=" + id[1];
		} else {
			return "";
		}
	}

}
