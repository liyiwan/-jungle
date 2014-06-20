package com.qganlan.service;

import java.util.List;

import com.qganlan.dto.GoodsDTO;

public interface GoodsManager {
	public List<GoodsDTO> getGoodsToCheck();
	public void checkGoods(GoodsDTO goods);
	public void disableStockWarning(Long goodsId);
	public void hideOneDay(Long goodsId);
	public List<GoodsDTO> getSoldOutGoodsList();
	public void deleteGoods(Long goodsId);
}
