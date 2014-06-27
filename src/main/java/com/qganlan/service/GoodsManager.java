package com.qganlan.service;

import java.util.List;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;

public interface GoodsManager {
	public List<GoodsDTO> getGoodsToCheck();
	public boolean checkGoods(GoodsDTO goods);
	public void disableStockWarning(Long goodsId);
	public void hideOneDay(Long goodsId);
	public List<GoodsDTO> getSoldOutGoodsList();
	public List<GoodsDTO> getNotOnSaleGoodsList();
	public void deleteGoods(Long goodsId);
	public void checkGoodsSpec(GoodsDTO goods);
	public List<GoodsSpecDTO> getOutOfStockGoods();
	public GoodsSpecDTO getGoodsSpec(Long goodsId, Long specId);
	public GoodsDTO getGoods(Long goodsId);
	public List<GoodsSpecDTO> getGoodsSpecList(Long goodsId);
	public void deleteGoodsSpec(Long goodsId, Long specId);
	public List<GoodsDTO> getGoodsList(String searchTerm);
}
