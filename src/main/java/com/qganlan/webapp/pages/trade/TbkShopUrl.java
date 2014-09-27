package com.qganlan.webapp.pages.trade;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.model.JTbkShopUrl;
import com.qganlan.service.TradeManager;

public class TbkShopUrl {

	@Property
	private List<JTbkShopUrl> tbkShopUrlList;
	
	@Property
	private JTbkShopUrl tbkShopUrl;
	
	@Property
	private String nick;
	
	@Property
	private String tbkUrl;
	
	@Inject
	private TradeManager tradeManager;
	
	public void setUpRender() {
		tbkShopUrlList = tradeManager.getTbkShopUrlList();
	}
	
	public void onSubmitFromCreateTbkShopUrlForm() {
		tradeManager.saveTbkShopUrl(nick, tbkUrl);
	}
}
