package com.qganlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.GoodsDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.service.StockManager;

@Service("stockManager")
public class StockManagerImpl implements StockManager {

	private GoodsDao goodsDao;

	@Autowired
	public void setGoodsDao(final GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

	public List<GoodsDTO> getStockWarningGoodsList(Long classId) {
		return goodsDao.getStockWarningGoodsList(classId);
	}
	
	public List<StockSpecDTO> getStockSpecByGoods(Long goodsId) {
		return goodsDao.getStockSpecByGoods(goodsId);
	}

}
