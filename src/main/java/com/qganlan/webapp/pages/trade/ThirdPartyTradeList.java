package com.qganlan.webapp.pages.trade;

import java.math.BigDecimal;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.service.TradeManager;

public class ThirdPartyTradeList {

	@Property
	private List<JRawTrade> rawTrades;
	
	@Property
	private JRawTrade rawTrade;
	
	@Inject
	private TradeManager tradeManager;
	
	@Property
	private JRawOrder rawOrder;
	
	public void setUpRender() {
		rawTrades = tradeManager.getInProgressThirdPartyRawTradeList();
	}
	
	public List<JRawOrder> getRawOrderList() {
		return tradeManager.getRawOrderList(rawTrade.getTid());
	}
	
	public String getItemUrl() {
		return "http://item.taobao.com/item.htm?id=" + rawOrder.getNumIid();
	}
	
	public String getTbkShopUrl() {
		return tradeManager.getTbkShopUrl(rawOrder.getProviderNick());
	}
	
	public String getBuyUrl() {
		return "http://item.taobao.com/item.htm?id=" + rawOrder.getProviderNumIid();
	}
	
	public String getFromNick() {
		return rawOrder.getProviderNick();
	}
	
	public BigDecimal getPrice() {
		return new BigDecimal("0");
	}
}
