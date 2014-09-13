package com.qganlan.dao;

import java.util.Date;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;

public interface TradeDao extends GenericDao<Trade, Long> {

	List<Trade> getSndTradeList(Date startDate, Date endDate);

	List<TradeGoods> getTradeGoodsList(Long tradeId);

	List<Trade> getTradeByTradeNO2(String string);

	void updateRemark(Long tradeId, String remark);

	void freezeTrade(Long tradeId, String string);

	void saveRawTrade(JRawTrade rawTrade);

	void saveRawOrder(JRawOrder rawOrder);

}
