package com.qganlan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.common.PropertyValueAlias;
import com.qganlan.dao.GoodsDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.model.ApiSysMatch;
import com.qganlan.model.MyGoods;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.ShopManager;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.qganlan.model.Shop;

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

	public void checkGoodsSpec(GoodsDTO goods) {
		Map<Long, Item> itemMap = new HashMap<Long, Item>();
		goodsDao.deleteStaleApiSysMatch(goods);
		List<GoodsSpecDTO> goodsSpecs = goodsDao.getGoodsSpecList(goods);
		List<Shop> shopList = shopManager.getTaobaoShopList();
		for (GoodsSpecDTO goodsSpec : goodsSpecs) {
			for (Shop shop : shopList) {
				String sessionKey = taobaoApiManager.getSessionKey(shop.getSellNick());
				if (sessionKey == null || sessionKey.trim().equals("")) {
					continue;
				}
				if (goods.getMultiSpec() == 1) {
					String outerId = goods.getGoodsNo() + goodsSpec.getSpecCode();
					List<Sku> skus = taobaoApiManager.getTaobaoSkusByOuterId(outerId, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), sessionKey);
					if (skus != null) {
						for (Sku sku : skus) {
							Item item = itemMap.get(sku.getNumIid());
							if (item == null) {
								item = taobaoApiManager.getTaobaoItemByNumIid(sku.getNumIid(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), sessionKey);
								itemMap.put(sku.getNumIid(), item);
							}
							PropertyValueAlias pva = new PropertyValueAlias(item == null ? "" : item.getPropertyAlias());
							ApiSysMatch apiSysMatch = getApiSysMatch(sku.getNumIid().toString(), sku.getSkuId().toString());
							if (apiSysMatch == null) {
								apiSysMatch = new ApiSysMatch();
								apiSysMatch.setNumIid(sku.getNumIid().toString());
								apiSysMatch.setSkuId(sku.getSkuId().toString());
								apiSysMatch.setStopFlag(0L);
							}
							apiSysMatch.setGoodsId(goodsSpec.getGoodsId());
							apiSysMatch.setSpecId(goodsSpec.getSpecId());
							apiSysMatch.setSkuOuterId(sku.getOuterId());
							apiSysMatch.setTbName(item == null ? "" : item.getTitle());
							apiSysMatch.setTbSku(pva.translate(sku.getPropertiesName() == null ? "" : sku.getPropertiesName()));
							apiSysMatch.setTbOuterId(item == null ? "" : item.getOuterId());
							apiSysMatch.setShopId(shop.getShopId());
							apiSysMatch.setTbGoods(1L);
							apiSysMatch.setIsSys(1L);
							apiSysMatch.setFixNumFlag(0L);
							apiSysMatch.setFixNum(0L);
							if (goods.getStock() > 50 || (goods.getFlagId() != null && goods.getFlagId() == 13) || (goodsSpec.getFlagId() != null && goodsSpec.getFlagId() == 13)) {
								System.out.println(shop.getShopName() + " " + goods.getGoodsNo() + " " + goods.getGoodsName() + " " + goodsSpec.getSpecName() + " 设置为同步实际库存。");
								apiSysMatch.setVirNumFlag(0L);
								apiSysMatch.setVirNumBase(0L);
								apiSysMatch.setVirNumTop(0L);
								apiSysMatch.setVirNumInc(0L);
							} else {
								System.out.println(shop.getShopName() + " " + goods.getGoodsNo() + " " + goods.getGoodsName() + " " + goodsSpec.getSpecName() + " 设置为同步虚拟库存。");
								apiSysMatch.setVirNumFlag(1L);
								apiSysMatch.setVirNumBase(1L);
								apiSysMatch.setVirNumTop(10L);
								apiSysMatch.setVirNumInc(10L);
							}
							goodsDao.saveOrUpdate(apiSysMatch);
						}
					}
				} else {
					String outerId = goods.getGoodsNo();
					List<Item> items = taobaoApiManager.getTaobaoItemsByOuterId(outerId, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), sessionKey);
					if (items != null) {
						for (Item item : items) {
							ApiSysMatch apiSysMatch = getApiSysMatch(item.getNumIid().toString(), "0");
							if (apiSysMatch == null) {
								apiSysMatch = new ApiSysMatch();
								apiSysMatch.setNumIid(item.getNumIid().toString());
								apiSysMatch.setSkuId("0");
								apiSysMatch.setStopFlag(0L);
							}
							apiSysMatch.setGoodsId(goodsSpec.getGoodsId());
							apiSysMatch.setSpecId(goodsSpec.getSpecId());
							apiSysMatch.setSkuOuterId("");
							apiSysMatch.setTbName(item.getTitle());
							apiSysMatch.setTbSku("");
							apiSysMatch.setTbOuterId(item.getOuterId());
							apiSysMatch.setShopId(shop.getShopId());
							apiSysMatch.setTbGoods(1L);
							apiSysMatch.setIsSys(1L);
							apiSysMatch.setFixNumFlag(0L);
							apiSysMatch.setFixNum(0L);
							if (goods.getStock() > 50 || (goods.getFlagId() != null && goods.getFlagId() == 13) || (goodsSpec.getFlagId() != null && goodsSpec.getFlagId() == 13)) {
								System.out.println(shop.getShopName() + " " + goods.getGoodsNo() + " " + goods.getGoodsName() + " 设置为同步实际库存。");
								apiSysMatch.setVirNumFlag(0L);
								apiSysMatch.setVirNumBase(0L);
								apiSysMatch.setVirNumTop(0L);
								apiSysMatch.setVirNumInc(0L);
							} else {
								System.out.println(shop.getShopName() + " " + goods.getGoodsNo() + " " + goods.getGoodsName() + " 设置为同步虚拟库存。");
								apiSysMatch.setVirNumFlag(1L);
								apiSysMatch.setVirNumBase(1L);
								apiSysMatch.setVirNumTop(10L);
								apiSysMatch.setVirNumInc(10L);
							}
							goodsDao.saveOrUpdate(apiSysMatch);
						}
					}
				}
			}
		}
	}

	public ApiSysMatch getApiSysMatch(String numIid, String skuIid) {
		return goodsDao.getApiSysMatch(numIid, skuIid);
	}

	public List<GoodsSpecDTO> getOutOfStockGoods() {
		return goodsDao.getOutOfStockGoods();
	}
}
