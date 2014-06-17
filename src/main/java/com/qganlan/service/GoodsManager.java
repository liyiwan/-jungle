package com.qganlan.service;

import java.util.List;

import com.qganlan.dto.GoodsDTO;

public interface GoodsManager {
	public List<GoodsDTO> getGoodsToCheck();
	public void checkGoods(GoodsDTO goods);
}
