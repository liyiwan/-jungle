package com.qganlan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.model.ItemUpdate;
import com.qganlan.service.AccountManager;
import com.qganlan.service.EmailManager;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.JobManager;
import com.qganlan.service.RzcshopManager;
import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.Item;

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
		matchGoods();
	}

	@Scheduled(cron = "0 0 20 ? * *")
	public void dailyJob() {
		sendAccountBalanceReport();
		checkGoods();
	}

	private void matchGoods() {
		try {
			List<ItemUpdate> itemUpdates = goodsManager.getItemUpdateList();
			for (ItemUpdate itemUpdate : itemUpdates) {
				System.out.println("ITEM UPDATE " + itemUpdate.getNumIid());
				Item item = taobaoApiManager.getTaobaoItemByNumIid(itemUpdate.getNumIid(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(itemUpdate.getNick()));
				if (item != null) {
					if (item.getOuterId() != null && !item.getOuterId().toUpperCase().startsWith("ID-")) {
						goodsManager.resolveApiSysMatch(item);
					}
					goodsManager.deleteItemUpdate(itemUpdate);
				} else {
					if (itemUpdate.getTryCount() >= 2) {
						goodsManager.deleteItemUpdate(itemUpdate);
					} else {
						itemUpdate.setTryCount(itemUpdate.getTryCount() + 1);
						goodsManager.saveItemUpdate(itemUpdate);
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
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
