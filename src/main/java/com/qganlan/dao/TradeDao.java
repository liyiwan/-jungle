package com.qganlan.dao;

import java.util.Date;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;

public interface TradeDao extends GenericDao<Trade, Long> {

	public List<Trade> getSndTradeList(Date startDate, Date endDate);

	public List<TradeGoods> getTradeGoodsList(Long tradeId);

	public List<Trade> getTradeByTradeNO2(String string);

	public void updateRemark(Long tradeId, String remark);

	public void freezeTrade(Long tradeId, String string);

	public void saveRawTrade(JRawTrade rawTrade);

	public void saveRawOrder(JRawOrder rawOrder);

	public List<JRawTrade> getRawTradeList(Integer curStatus);

	public List<JRawOrder> getRawOrderList(Long tid);

	public String getTbkShopUrl(String providerNick);

	public JRawTrade getRawTrade(Long tid);

	public void fillOrder(JRawOrder rawOrder);

	public void completeTrade(Long tid);

	public List<JRawTrade> getRecentRawTradeList();

	public List<JRawTrade> getInProgressThirdPartyRawTradeList();

}
