package com.qganlan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.GoodsDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.model.MyGoods;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.Item;

@Service("goodsManager")
public class GoodsManagerImpl implements GoodsManager {

	private GoodsDao goodsDao;
	private TaobaoApiManager taobaoApiManager;
	
	@Inject
    private Logger logger;

	@Autowired
	public void setGoodsDao(final GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }
	
	@Autowired
	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

	public List<GoodsDTO> getGoodsToCheck() {
		return goodsDao.getGoodsToCheck();
	}

	public boolean checkGoods(GoodsDTO goods) {
		try {
			List<Item> items = taobaoApiManager.getItemByOuterId(goods.getGoodsNo());
			if (items != null && items.size() > 0) {
				Item item = items.get(0);
				MyGoods myGoods = goodsDao.getMyGoods(goods.getGoodsId());
				if (myGoods == null) {
					myGoods = new MyGoods();
					myGoods.setGoodsId(goods.getGoodsId());
				}
				myGoods.setPicPath(item.getPicUrl());
				myGoods.setCheckDate(new Date());
				goodsDao.saveMyGoods(myGoods);
				System.out.println(goods.getGoodsNo() + " " + myGoods.getPicPath());
				return true;
			} else {
				System.out.println(goods.getGoodsNo() + " 没有在售");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

	public void disableStockWarning(Long goodsId) {
		goodsDao.disableStockWarning(goodsId);
	}

	public void hideOneDay(Long goodsId) {
		goodsDao.hideOneDay(goodsId);
	}

	public List<GoodsDTO> getSoldOutGoodsList() {
		return goodsDao.getSoldOutGoodsList();
	}

	public List<GoodsDTO> getNotOnSaleGoodsList() {
		return goodsDao.getNotOnSaleGoodsList();
	}
	
	public void deleteGoods(Long goodsId) {
		goodsDao.deleteGoods(goodsId);
	}

}
