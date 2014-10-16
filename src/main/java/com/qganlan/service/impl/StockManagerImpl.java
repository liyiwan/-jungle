package com.qganlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.GoodsDao;
import com.qganlan.dao.MyConfigDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.model.MyConfig;
import com.qganlan.service.StockManager;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;

@Service("stockManager")
public class StockManagerImpl implements StockManager {

	private GoodsDao goodsDao;
	
	private MyConfigDao myConfigDao;
	
	@Autowired
	private TaobaoApiManager taobaoApiManager;
	
	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

	@Autowired
	public void setGoodsDao(final GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }
	
	@Autowired
	public void setMyConfigDao(MyConfigDao myConfigDao) {
		this.myConfigDao = myConfigDao;
	}

	public List<GoodsDTO> getStockWarningGoodsList(Long classId) {
		return goodsDao.getStockWarningGoodsList(classId);
	}
	
	public List<StockSpecDTO> getStockSpecByGoods(Long goodsId) {
		return goodsDao.getStockSpecByGoods(goodsId);
	}

	@Override
	public void synchronizeStock() {
		MyConfig config = myConfigDao.get("UpdateAge"); 
		Long updateAge = Long.valueOf(config.getConfigValue());
		List<GoodsSpecDTO> goodsSpecList = goodsDao.getGoodsSpecListForUpdateOnSaleStock(updateAge);
		for (GoodsSpecDTO goodsSpec : goodsSpecList) {
			GoodsDTO goods = goodsDao.getGoods(goodsSpec.getGoodsId());
			GoodsSpecDTO aGoodsSpecDTO = goodsDao.getGoodsSpec(goodsSpec.getGoodsId(), goodsSpec.getSpecId());
			Long orderedCount = goodsDao.getOrderedCount(goodsSpec.getGoodsId(), goodsSpec.getSpecId());
			Long pendingSendCount = goodsDao.getPendingSendCount(goodsSpec.getGoodsId(), goodsSpec.getSpecId());
			Long stock = goodsSpec.getStock() - orderedCount - pendingSendCount;
			if (stock < 0) {
				stock = 0L;
			}
			if (goods != null && aGoodsSpecDTO != null) {
				if (goodsSpec.getSpecId().equals(0L)) {
					List<Item> items = taobaoApiManager.getTaobaoItemsByOuterId(goods.getGoodsNo(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("小脚丫商城"));
					for (Item item : items) {
						taobaoApiManager.updateItemQuantity(item.getNumIid(), null, stock, taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("小脚丫商城"));
					}
					
					items = taobaoApiManager.getTaobaoItemsByOuterId(goods.getGoodsNo(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("lingxige"));
					for (Item item : items) {
						taobaoApiManager.updateItemQuantity(item.getNumIid(), null, stock, taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("lingxige"));
					}
				} else {
					List<Sku> skus = taobaoApiManager.getTaobaoSkusByOuterId(goods.getGoodsNo() + aGoodsSpecDTO.getSpecCode(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("小脚丫商城"));
					for (Sku sku : skus) {
						taobaoApiManager.updateItemQuantity(sku.getNumIid(), sku.getSkuId(), stock, taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("小脚丫商城"));
					}
					
					skus = taobaoApiManager.getTaobaoSkusByOuterId(goods.getGoodsNo() + aGoodsSpecDTO.getSpecCode(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("lingxige"));
					for (Sku sku : skus) {
						taobaoApiManager.updateItemQuantity(sku.getNumIid(), sku.getSkuId(), stock, taobaoApiManager.getAppKey(), taobaoApiManager.getAppKey(), taobaoApiManager.getSessionKey("lingxige"));
					}
				}
			}
		}
		config.setConfigValue((System.currentTimeMillis() / 1000) + "");
		myConfigDao.save(config);
	}

}
