package com.qganlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.MoneyInOutDao;
import com.qganlan.dto.MoneyInOutDTO;
import com.qganlan.service.AccountManager;

@Service("accountManager")
public class AccountManagerImpl implements AccountManager {
	
	@Autowired
	private MoneyInOutDao moneyInOutDao;
	
	public void setMoneyInOutDao(MoneyInOutDao moneyInOutDao) {
		this.moneyInOutDao = moneyInOutDao;
	}
	public String getHtmlAccountBalanceReport() {
		StringBuffer sb = new StringBuffer();
		List<MoneyInOutDTO> dwfList = moneyInOutDao.getTop10MoneyInOutList("丁伟锋");
		sb.append(getHtmlTable(dwfList));
		sb.append("<br />");
		sb.append("<br />");
		List<MoneyInOutDTO> cxsList = moneyInOutDao.getTop10MoneyInOutList("陈晓松");
		sb.append(getHtmlTable(cxsList));
		sb.append("<br />");
		sb.append("<br />");
		List<MoneyInOutDTO> zyzList = moneyInOutDao.getTop10MoneyInOutList("张岳致");
		sb.append(getHtmlTable(zyzList));
		return sb.toString();
	}
	private String getHtmlTable(List<MoneyInOutDTO> moneyInOutList) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"3\" style=\"border:1px solid gray;\" >");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("账户");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("日期");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("收入");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("支出");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("余额");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("项目");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("描述");
		sb.append("</td>");
		sb.append("</tr>");
		for (MoneyInOutDTO moneyInOut : moneyInOutList) {
			sb.append("<tr>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getAccountName());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getRegDate());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getAmountIn());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getAmountOut());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getBalance());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getItemName());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(moneyInOut.getSummary());
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
}
