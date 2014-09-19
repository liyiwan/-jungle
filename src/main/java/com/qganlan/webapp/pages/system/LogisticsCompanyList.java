package com.qganlan.webapp.pages.system;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.model.JLogisticsCompany;
import com.qganlan.service.TradeManager;

public class LogisticsCompanyList {

	@Inject
	private TradeManager tradeManager;
	
	@Property
	private List<JLogisticsCompany> logisticsCompanyList;
	
	@Property
	private JLogisticsCompany logisticsCompany;
	
	public void setUpRender() {
		logisticsCompanyList = tradeManager.getLogisticsCompanyList();
	}
	
	public void onDownloadLogisticsCompanyList() {
		tradeManager.downloadLogisticsCompanyList();
	}
}
