package com.qganlan.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.StockSpecDTO;
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
	public void deleteGoods(Long goodsId);
}
