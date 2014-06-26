package com.qganlan.dao;

import java.util.Date;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;

public interface TradeDao extends GenericDao<Trade, Long> {

	List<Trade> getSndTradeList(Date startDate, Date endDate);

	List<TradeGoods> getTradeGoodsList(Long tradeId);

}
