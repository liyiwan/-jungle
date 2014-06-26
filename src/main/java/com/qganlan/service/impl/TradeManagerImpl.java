package com.qganlan.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.TradeDao;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;
import com.qganlan.service.TradeManager;

@Service("tradeManager")
public class TradeManagerImpl implements TradeManager {

	@Autowired
	private TradeDao tradeDao;
	
	public TradeDao getTradeDao() {
		return tradeDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public List<Trade> getSndTradeList(Date startDate, Date endDate) {
		return tradeDao.getSndTradeList(startDate, endDate);
	}

	public List<TradeGoods> getTradeGoodsList(Long tradeId) {
		return tradeDao.getTradeGoodsList(tradeId);
	}

	public Trade getTrade(Long tradeId) {
		return tradeDao.get(tradeId);
	}

}
