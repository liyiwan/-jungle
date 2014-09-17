package com.qganlan.webapp.pages.trade;

import java.math.BigDecimal;
import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.service.TradeManager;

public class FillTrade {
	
	@Property
	private JRawTrade rawTrade;
	
	@Property
	private List<JRawOrder> rawOrders;
	
	@Property
	private JRawOrder rawOrder;
	
	@PageActivationContext
	private Long tid;
	
	@Inject
	private TradeManager tradeManager;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	public void setUpRender() {
		rawTrade = tradeManager.getRawTrade(tid);
		rawOrders = tradeManager.getRawOrderList(tid);
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
	
	public void onPrepareForSubmit() {
		rawTrade = tradeManager.getRawTrade(tid);
		rawOrders = tradeManager.getRawOrderList(tid);
	}
	
	public Object onSubmitFromFillTradeForm() {
		tradeManager.fillTrade(rawTrade, rawOrders);
		Link link = pageRenderLinkSource.createPageRenderLink(ThirdPartyTradeList.class);
		return link;
	}
}
