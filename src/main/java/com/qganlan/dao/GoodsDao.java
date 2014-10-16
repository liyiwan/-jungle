package com.qganlan.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.dto.ThirdPartyGoods;
import com.qganlan.model.ApiSysMatch;
import com.qganlan.model.Goods;
import com.qganlan.model.ItemUpdate;
import com.qganlan.model.MyGoods;

public interface GoodsDao extends GenericDao<Goods, Long> {

	public List<GoodsDTO> getStockWarningGoodsList(Long classId);
	public List<StockSpecDTO> getStockSpecByGoods(Long goodsId);
	public List<GoodsDTO> getGoodsToCheck();
	public MyGoods getMyGoods(Long goodsId);
	public void saveMyGoods(MyGoods myGoods);
	public void disableStockWarning(Long goodsId);
	public void hideOneDay(Long goodsId);
	public List<GoodsDTO> getSoldOutGoodsList();
	public List<GoodsDTO> getNotOnSaleGoodsList();
	public void deleteGoods(Long goodsId);
	public void deleteStaleApiSysMatch(GoodsDTO goods);
	public List<GoodsSpecDTO> getGoodsSpecList(Long goodsId);
	public List<ApiSysMatch> getApiSysMatch(Long goodsId, Long specId);
	public void saveOrUpdate(ApiSysMatch apiSysMatch);
	public List<GoodsSpecDTO> getOutOfStockGoods();
	public GoodsSpecDTO getGoodsSpec(Long goodsId, Long specId);
	public GoodsDTO getGoods(Long goodsId);
	public void deleteGoodsSpec(Long goodsId, Long specId);
	public List<GoodsDTO> getGoodsList(String searchTerm);
	public GoodsSpecDTO getGoodsSpecBySkuOuterId(String outerId);
	public ApiSysMatch getApiSysMatch(String numIid, String skuId);
	public GoodsDTO getGoodsByGoodsNo(String outerId);
	public void deleteStaleApiSysMatch(Long numIid, Long skuId);
	public void recordItemUpdate(Long numIid, String nick);
	public List<ItemUpdate> getItemUpdateList();
	public void deleteItemUpdate(ItemUpdate itemUpdate);
	public void saveItemUpdate(ItemUpdate itemUpdate);
	public void deleteApiSysMatch(String string);
	public List<ThirdPartyGoods> getThirdPartyGoodsList();
	public List<GoodsSpecDTO> getGoodsSpecListForUpdateOnSaleStock(Long updateAge);
	public Long getOrderedCount(Long goodsId, Long specId);
	public Long getPendingSendCount(Long goodsId, Long specId);
}
