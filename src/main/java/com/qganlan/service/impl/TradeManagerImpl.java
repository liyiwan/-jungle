package com.qganlan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.dao.TradeDao;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;
import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TradeManager;

@Service("tradeManager")
public class TradeManagerImpl implements TradeManager {

	@Autowired
	private TradeDao tradeDao;
	
	@Autowired
	private TaobaoApiManager taobaoApiManager;
	
	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

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

	public void onTradeMemoModified(String nick, Long tid) {
		com.taobao.api.domain.Trade taobaoTrade = taobaoApiManager.getTaobaoTrade(tid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(),taobaoApiManager.getSessionKey(nick));
		if (taobaoTrade != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Trade> listTrade = tradeDao.getTradeByTradeNO2("%" + tid + "%");
			for (Trade trade : listTrade) {
				String remark = taobaoTrade.getSellerMemo() + "(SYS " + sdf.format(new Date()) + ")" + "\n" + trade.getAppendRemark();
				if (remark.length() > 1000) {
					remark = remark.substring(0,1000);
				}
				tradeDao.updateRemark(trade.getTradeId(), remark);
				System.out.println("TradeStatus=" + trade.getTradeStatus());
				if (trade.getTradeStatus() == 5) {
					System.out.println("发货中，冻结订单。");
					tradeDao.freezeTrade(trade.getTradeId(), "修改备注");
				}
			}
		}
	}

}
