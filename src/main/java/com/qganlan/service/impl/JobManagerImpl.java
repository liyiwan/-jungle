package com.qganlan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.service.EmailManager;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.JobManager;
import com.qganlan.service.TaobaoApiManager;

@Service("jobManager")
public class JobManagerImpl implements JobManager {

	@Autowired
	private TaobaoApiManager taobaoApiManager;
	@Autowired
	private GoodsManager goodsManager;
	@Autowired
	private EmailManager emailManager;

	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
	
	@Scheduled(initialDelay = 10000, fixedDelay = 3600000)
	public void hourJob() {
		checkGoods();
	}
	
	private void checkModifiedTaobaoItems() {
	}

	private void checkGoods() {
		try {
			List<GoodsDTO> goodsList = goodsManager.getGoodsToCheck();
			for (GoodsDTO goods : goodsList) {
				System.out.println("CHECK GOODS " + goods.getGoodsNo() + " " + goods.getGoodsName());
				goodsManager.checkGoods(goods);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
