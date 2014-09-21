package com.qganlan.service;

import java.util.Date;
import java.util.List;

import com.qganlan.model.JLogisticsCompany;
import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;

public interface TradeManager {

	public List<Trade> getSndTradeList(Date startDate, Date endDate);

	public List<TradeGoods> getTradeGoodsList(Long tradeId);

	public Trade getTrade(Long tradeId);

	public void onTradeMemoModified(String nick, Long tid);

	public void notifyByEmail(JRawTrade rawTrade);

	public JRawTrade recordThirdPartyTrade(com.taobao.api.domain.Trade trade);

	public List<JRawTrade> getInProgressThirdPartyRawTradeList();

	public List<JRawOrder> getRawOrderList(Long tid);

	public String getTbkShopUrl(String providerNick);

	public JRawTrade getRawTrade(Long tid);

	public void fillTrade(JRawTrade rawTrade, List<JRawOrder> rawOrders);

	public void completeTrade(Long tid);

	public List<JRawTrade> getRecentRawTradeList();

	public void downloadLogisticsCompanyList();

	public List<JLogisticsCompany> getLogisticsCompanyList();

	public void autoSendTrade(Long tid);
	
	public String getLogisticsCompanyCode(String logisticsCompany);

	public void saveTradeFromTaobao(String nick, Long tid);

}
