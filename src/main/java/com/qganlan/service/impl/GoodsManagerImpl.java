package com.qganlan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.GoodsDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.model.ApiSysMatch;
import com.qganlan.model.MyGoods;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.ShopManager;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.Item;

@Service("goodsManager")
public class GoodsManagerImpl implements GoodsManager {

	private GoodsDao goodsDao;
	private TaobaoApiManager taobaoApiManager;

	@Autowired
	private ShopManager shopManager;

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

	public void setShopManager(ShopManager shopManager) {
		this.shopManager = shopManager;
	}

	public List<GoodsDTO> getGoodsToCheck() {
		return goodsDao.getGoodsToCheck();
	}

	public boolean checkGoods(GoodsDTO goods) {
		try {
			if (goods.getPicPath() == null || goods.getPicPath().trim().equals("")) {
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
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

	public void disableStockWarning(Long goodsId) {
		goodsDao.disableStockWarning(goodsId);
		List<GoodsSpecDTO> goodsSpecs = goodsDao.getGoodsSpecList(goodsId);
		for (GoodsSpecDTO goodsSpec : goodsSpecs) {
			List<ApiSysMatch> apiSysMatchList = getApiSysMatch(goodsSpec.getGoodsId(), goodsSpec.getSpecId());
			for (ApiSysMatch apiSysMatch : apiSysMatchList) {
				System.out.println(goodsId + " 设置为同步实际库存。");
				apiSysMatch.setVirNumFlag(0L);
				apiSysMatch.setVirNumBase(0L);
				apiSysMatch.setVirNumTop(0L);
				apiSysMatch.setVirNumInc(0L);
				apiSysMatch.setIsSys(1L);
				goodsDao.saveOrUpdate(apiSysMatch);
			}
		}
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

	public void checkGoodsSpec(GoodsDTO goods) {
		goodsDao.deleteStaleApiSysMatch(goods);
		List<GoodsSpecDTO> goodsSpecs = goodsDao.getGoodsSpecList(goods.getGoodsId());
		for (GoodsSpecDTO goodsSpec : goodsSpecs) {
			List<ApiSysMatch> apiSysMatchList = getApiSysMatch(goodsSpec.getGoodsId(), goodsSpec.getSpecId());
			for (ApiSysMatch apiSysMatch : apiSysMatchList) {
				if (goods.getStock() > 50 || (goods.getFlagId() != null && goods.getFlagId() == 13) || (goodsSpec.getFlagId() != null && goodsSpec.getFlagId() == 13)) {
					System.out.println(goods.getGoodsNo() + " " + goods.getGoodsName() + " " + goodsSpec.getSpecName() + " 设置为同步实际库存。");
					apiSysMatch.setVirNumFlag(0L);
					apiSysMatch.setVirNumBase(0L);
					apiSysMatch.setVirNumTop(0L);
					apiSysMatch.setVirNumInc(0L);
				} else {
					System.out.println(goods.getGoodsNo() + " " + goods.getGoodsName() + " " + goodsSpec.getSpecName() + " 设置为同步虚拟库存。");
					apiSysMatch.setVirNumFlag(1L);
					apiSysMatch.setVirNumBase(1L);
					apiSysMatch.setVirNumTop(10L);
					apiSysMatch.setVirNumInc(10L);
				}
				apiSysMatch.setIsSys(1L);
				goodsDao.saveOrUpdate(apiSysMatch);
			}
		}
	}

	public List<ApiSysMatch> getApiSysMatch(Long goodsId, Long specId) {
		return goodsDao.getApiSysMatch(goodsId, specId);
	}

	public List<GoodsSpecDTO> getOutOfStockGoods() {
		return goodsDao.getOutOfStockGoods();
	}

	public GoodsSpecDTO getGoodsSpec(Long goodsId, Long specId) {
		return goodsDao.getGoodsSpecList(goodsId, specId);
	}

	public GoodsDTO getGoods(Long goodsId) {
		return goodsDao.getGoods(goodsId);
	}

	public List<GoodsSpecDTO> getGoodsSpecList(Long goodsId) {
		return goodsDao.getGoodsSpecList(goodsId);
	}

	public void deleteGoodsSpec(Long goodsId, Long specId) {
		goodsDao.deleteGoodsSpec(goodsId, specId);
	}

	public List<GoodsDTO> getGoodsList(String searchTerm) {
		return goodsDao.getGoodsList(searchTerm);
	}
}
