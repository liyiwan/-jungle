package com.qganlan.service.impl;

import java.util.Date;
import java.util.List;

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
import com.qganlan.model.ItemUpdate;
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
		return goodsDao.getGoodsSpec(goodsId, specId);
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

	@Override
	public void resolveApiSysMatch(Item item) {
		Shop shop = shopManager.getShopByNick(item.getNick());
		List<Sku> skus = item.getSkus();
		if (skus == null || skus.size() == 0) {
			if (item.getOuterId() != null && !item.getOuterId().trim().equals("")) {
				GoodsSpecDTO goodsSpec = goodsDao.getGoodsSpecBySkuOuterId(item.getOuterId());
				if (goodsSpec != null) {
					GoodsDTO goods = goodsDao.getGoods(goodsSpec.getGoodsId());
					ApiSysMatch apiSysMatch = goodsDao.getApiSysMatch(item.getNumIid().toString(), "0");
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
					apiSysMatch.setIsSys(1L);
					goodsDao.saveOrUpdate(apiSysMatch);
				} else {
					goodsDao.deleteStaleApiSysMatch(item.getNumIid(), 0L);
				}
			}
		} else {
			for (Sku sku : skus) {
				GoodsSpecDTO goodsSpec = goodsDao.getGoodsSpecBySkuOuterId(sku.getOuterId());
				if (goodsSpec != null) {
					System.out.println("goodsId="+goodsSpec.getGoodsId());
					GoodsDTO goods = goodsDao.getGoods(goodsSpec.getGoodsId());
					PropertyValueAlias pva = new PropertyValueAlias(item == null ? "" : item.getPropertyAlias());
					ApiSysMatch apiSysMatch = goodsDao.getApiSysMatch(item.getNumIid().toString(), sku.getSkuId().toString());
					if (apiSysMatch == null) {
						apiSysMatch = new ApiSysMatch();
						apiSysMatch.setNumIid(item.getNumIid().toString());
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
				} else {
					goodsDao.deleteStaleApiSysMatch(item.getNumIid(), sku.getSkuId());
				}
			}
		}
		
	}

	@Override
	public void deleteApiSysMatch(Long numIid) {
		goodsDao.deleteApiSysMatch(numIid.toString());
	}

	@Override
	public void recordItemUpdate(Long numIid, String nick) {
		goodsDao.recordItemUpdate(numIid, nick);
	}

	@Override
	public List<ItemUpdate> getItemUpdateList() {
		return goodsDao.getItemUpdateList();
	}

	@Override
	public void deleteItemUpdate(ItemUpdate itemUpdate) {
		goodsDao.deleteItemUpdate(itemUpdate);		
	}

	@Override
	public void saveItemUpdate(ItemUpdate itemUpdate) {
		goodsDao.saveItemUpdate(itemUpdate);
	}
}
