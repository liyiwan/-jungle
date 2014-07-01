package com.qganlan.service;

import java.util.Date;
import java.util.List;

import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;

public interface TradeManager {

	List<Trade> getSndTradeList(Date startDate, Date endDate);

	List<TradeGoods> getTradeGoodsList(Long tradeId);

	Trade getTrade(Long tradeId);

	void onTradeMemoModified(String nick, Long tid);

	void notifyByEmail(com.taobao.api.domain.Trade trade);

}
