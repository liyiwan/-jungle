package com.qganlan.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.model.ApiSysMatch;
import com.qganlan.model.Goods;
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
	public List<GoodsSpecDTO> getGoodsSpecList(GoodsDTO goods);
	public ApiSysMatch getApiSysMatch(String numIid, String skuIid);
	public void saveOrUpdate(ApiSysMatch apiSysMatch);
	public List<GoodsSpecDTO> getOutOfStockGoods();
}
