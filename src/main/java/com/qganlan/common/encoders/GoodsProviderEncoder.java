package com.qganlan.common.encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.json.JSONObject;

import com.qganlan.dto.GoodsProviderDTO;

public class GoodsProviderEncoder implements ValueEncoder<GoodsProviderDTO> {

	
	public String toClient(GoodsProviderDTO goodsProvider) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("goodsId", goodsProvider.getGoodsId());
		jsonObject.put("providerId", goodsProvider.getProviderId());
		return jsonObject.toString();
	}

	
	public GoodsProviderDTO toValue(String goodsProvider) {
		JSONObject jsonObject = new JSONObject(goodsProvider);
		GoodsProviderDTO aGoodsProvider = new GoodsProviderDTO();
		aGoodsProvider.setGoodsId(jsonObject.getLong("goodsId"));
		aGoodsProvider.setProviderId(jsonObject.getLong("providerId"));
		return aGoodsProvider;
	}

}
