package com.qganlan.common.encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.json.JSONObject;

import com.qganlan.dto.GoodsSpecDTO;

public class GoodsSpecEncoder implements ValueEncoder<GoodsSpecDTO> {

	public String toClient(GoodsSpecDTO goodsSpec) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("goodsId", goodsSpec.getGoodsId());
		jsonObject.put("specId", goodsSpec.getSpecId());
		return jsonObject.toString();
	}

	public GoodsSpecDTO toValue(String goodsSpecString) {
		JSONObject jsonObject = new JSONObject(goodsSpecString);
		GoodsSpecDTO aGoodsSpec = new GoodsSpecDTO();
		aGoodsSpec.setGoodsId(jsonObject.getLong("goodsId"));
		aGoodsSpec.setSpecId(jsonObject.getLong("specId"));
		return aGoodsSpec;
	}
}
