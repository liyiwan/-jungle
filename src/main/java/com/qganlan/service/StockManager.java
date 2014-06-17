package com.qganlan.service;

import java.util.List;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.StockSpecDTO;

public interface StockManager {
	public List<GoodsDTO> getStockWarningGoodsList();
	public List<StockSpecDTO> getStockSpecByGoods(Long goodsId);
	
}
