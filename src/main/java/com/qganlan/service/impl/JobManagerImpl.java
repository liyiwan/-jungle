package com.qganlan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.service.AccountManager;
import com.qganlan.service.EmailManager;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.JobManager;
import com.qganlan.service.RzcshopManager;
import com.qganlan.service.TaobaoApiManager;

@Service("jobManager")
public class JobManagerImpl implements JobManager {

	@Autowired
	private TaobaoApiManager taobaoApiManager;
	@Autowired
	private GoodsManager goodsManager;
	@Autowired
	private EmailManager emailManager;
	@Autowired
	private RzcshopManager rzcshopManager;
	@Autowired
	private AccountManager accountManager;

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
	
	public void setRzcshopManager(RzcshopManager rzcshopManager) {
		this.rzcshopManager = rzcshopManager;
	}
	
	@Scheduled(initialDelay = 600000, fixedDelay = 3600000)
	public void hourlyJob() {
		checkGoods();
	}
	
	@Scheduled(cron = "0 0 20 ? * *")
	public void dailyJob() {
		rzcshopManager.execute();
		sendAccountBalanceReport();
	}

	private void checkGoods() {
		try {
			List<GoodsDTO> goodsList = goodsManager.getGoodsToCheck();
			for (GoodsDTO goods : goodsList) {
				System.out.println("CHECK GOODS " + goods.getGoodsNo() + " " + goods.getGoodsName());
				goodsManager.checkGoods(goods);
				goodsManager.checkGoodsSpec(goods);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void sendAccountBalanceReport() {
		try {
			String content = accountManager.getHtmlAccountBalanceReport();
			String[] receivers = new String[] {"1106628276@qq.com", "9394908@qq.com", "dwf306@sina.com"};
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			emailManager.sentHtml("今日账户报表" + sdf.format(new Date()), content, receivers);
			System.out.println("今日账户报表发送成功");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
