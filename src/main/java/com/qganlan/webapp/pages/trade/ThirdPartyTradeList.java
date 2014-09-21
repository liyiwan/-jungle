package com.qganlan.webapp.pages.trade;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TradeManager;

public class ThirdPartyTradeList {
	
	@PageActivationContext
	private int type = 1;

	@Property
	private List<JRawTrade> rawTrades;
	
	@Property
	private JRawTrade rawTrade;
	
	@Inject
	private TradeManager tradeManager;
	
	@Property
	private JRawOrder rawOrder;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String tradeId;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String nick;
	
	public void setUpRender() {
		if (type == 1) {
			rawTrades = tradeManager.getInProgressThirdPartyRawTradeList();
		} else {
			rawTrades = tradeManager.getRecentRawTradeList();
		}
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
		if (rawOrder.getProviderNumIid() != null) {
			return "http://item.taobao.com/item.htm?id=" + rawOrder.getProviderNumIid();
		} else {
			return "";
		}
	}
	
	public String getFromNick() {
		return rawOrder.getProviderNick();
	}
	
	public BigDecimal getPrice() {
		return new BigDecimal("0");
	}
	
	public void onCompleteTrade(Long tid) {
		tradeManager.completeTrade(tid);
	}
	
	public String getRowStyle() {
		if (rawTrade.getCurStatus().equals(11)) {
			return "background-color:#dddddd";
		} else {
			return "background-color:#ffffff";
		}
	}
	
	public String getRowStyle2() {
		if (rawOrder.getPurchaseNick() != null && !rawOrder.getPurchaseNick().equals("") && rawOrder.getPurchaseTid() != null && !rawOrder.getPurchaseTid().equals("")) {
			return "background-color:#FFEC8B";
		} else if (rawOrder.getProviderNumIid() == null) {
			return "background-color:#dddddd";
		} else {
			return "background-color:#ffffff";
		}
	}
	
	public void onAutoSendTrade(Long tid) {
		tradeManager.autoSendTrade(tid);
	}
	
	public void onAutoSendTradeAll() {
		List<JRawTrade> inprogressRawTrades = tradeManager.getInProgressThirdPartyRawTradeList();
		for (JRawTrade aRawTrade : inprogressRawTrades) {
			tradeManager.autoSendTrade(aRawTrade.getTid());
		}
	}
	
	public void onSubmitFromGetTradeForm() {
		if (tradeId != null && !tradeId.equals("")) {
			if (StringUtils.isNumeric(tradeId.trim())) {
				Long tid = Long.valueOf(tradeId.trim());
				tradeManager.saveTradeFromTaobao(nick, tid);
			}
		}
	}
}
