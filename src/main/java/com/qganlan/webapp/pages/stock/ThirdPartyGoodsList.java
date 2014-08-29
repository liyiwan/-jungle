package com.qganlan.webapp.pages.stock;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.dto.ThirdPartyGoods;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.TbkItem;

public class ThirdPartyGoodsList {
	
	@Property
	private List<ThirdPartyGoods> thirdPartyGoodsList;
	
	@Property
	private ThirdPartyGoods thirdPartyGoods;
	
	@Inject
	private GoodsManager goodsManager;
	
	@Inject
	private TaobaoApiManager taobaoApiManager;
	
	public void setUpRender() {
		thirdPartyGoodsList = goodsManager.getTirdPartyGoodsList();
	}

	public String getTaobaoKeLink() {
		String tradeGoodsNo = thirdPartyGoods.getTradeGoodsNO();
		String[] id = tradeGoodsNo.split("-");
		if (id.length == 2) { 
			String numIid = id[1];
			List<TbkItem> tbkItems = taobaoApiManager.getTbkItems(numIid);
			if (tbkItems != null && tbkItems.size() > 0) {
				return tbkItems.get(0).getClickUrl();
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
}
