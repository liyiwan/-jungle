package com.qganlan.webapp.pages.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.model.Provider;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.ProviderManager;

public class GoodsOutOfStock {
	
	@Property
	private List<Provider> providerList;
	
	@Property
	private Provider provider;
	
	@Property
	private GoodsSpecDTO goodsSpec;
	
	@Inject
	private GoodsManager goodsManager;
	
	@Inject
	private ProviderManager providerManager;

	@Property
	private GoodsDTO goods;
	
	private Map<Long, List<GoodsSpecDTO>> goodsSpecMap;
	private Map<Long, List<GoodsDTO>> providerGoodsMap;
	
	public void setUpRender() {
		providerList = new ArrayList<Provider>();
		providerGoodsMap = new HashMap<Long, List<GoodsDTO>>();
		goodsSpecMap = new HashMap<Long, List<GoodsSpecDTO>>();
		
		List<GoodsSpecDTO> goodsSpecList = goodsManager.getOutOfStockGoods();
		for (GoodsSpecDTO aGoodsSpec : goodsSpecList) {
			List<GoodsSpecDTO> aGoodsSpecList = goodsSpecMap.get(aGoodsSpec.getGoodsId());
			if (aGoodsSpecList == null) {
				aGoodsSpecList = new ArrayList<GoodsSpecDTO>();
				goodsSpecMap.put(aGoodsSpec.getGoodsId(), aGoodsSpecList);
				GoodsDTO aGoods = new GoodsDTO();
				aGoods.setGoodsId(aGoodsSpec.getGoodsId());
				aGoods.setGoodsNo(aGoodsSpec.getGoodsNo());
				aGoods.setGoodsName(aGoodsSpec.getGoodsName());
				aGoods.setPicPath(aGoodsSpec.getPicPath());
				List<Provider> aProviderList = providerManager.getProviderListByGoodsId(aGoods.getGoodsId());
				if (aProviderList == null || aProviderList.size() == 0) {
					Provider aProvider = new Provider();
					aProvider.setProviderId(0L);
					aProvider.setProviderName("DUMMY");
					aProviderList.add(aProvider);
				}
				for (Provider aProvider : aProviderList) {
					List<GoodsDTO> aGoodsList = providerGoodsMap.get(aProvider.getProviderId());
					if (aGoodsList == null) {
						aGoodsList = new ArrayList<GoodsDTO>();
						providerGoodsMap.put(aProvider.getProviderId(), aGoodsList);
						providerList.add(aProvider);
					}
					aGoodsList.add(aGoods);
				}
			}
			aGoodsSpecList.add(aGoodsSpec);
		}
	}
	
	public String getPicPath() {
		if (goods.getPicPath() == null || goods.getPicPath().trim().equals("")) {
			return "#";
		} else {
			return goods.getPicPath() + "_310x310.jpg";
		}
	}
	
	public Long getQuantity() {
		return goodsSpec.getSoldCount() - goodsSpec.getStock() - goodsSpec.getPurchaseCount();
	}
	
	public List<GoodsSpecDTO> getGoodsSpecList() {
		return goodsSpecMap.get(goods.getGoodsId());
	}
	
	public List<GoodsDTO> getGoodsList() {
		return providerGoodsMap.get(provider.getProviderId());
	}
}
