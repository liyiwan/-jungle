package com.qganlan.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.TradeDao;
import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.JTbkShopUrl;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;

@Repository("tradeDao")
public class TradeDaoHibernate extends GenericDaoHibernate<Trade, Long> implements TradeDao {

	public TradeDaoHibernate() {
		super(Trade.class);
	}

	@SuppressWarnings("unchecked")
	public List<Trade> getSndTradeList(Date startDate, Date endDate) {
	    String hql = "FROM Trade WHERE sndTime >= :startDate AND sndTime < :endDate AND tradeStatus = 11 ORDER BY shopId ASC, sndTime ASC";
	    Query query = getSession().createQuery(hql);
	    Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        query.setDate("startDate", cal.getTime());
        cal.setTime(endDate);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        query.setDate("endDate", cal.getTime());
        return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<TradeGoods> getTradeGoodsList(Long tradeId) {
		Query query = getSession().createQuery("FROM TradeGoods WHERE tradeId = :tradeId");
		query.setLong("tradeId", tradeId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Trade> getTradeByTradeNO2(String tradeNo2) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM G_Trade_TradeList WHERE tradeNO2 LIKE :tradeNo2");
		query.setString("tradeNo2", tradeNo2);
		query.addEntity(Trade.class);
		return query.list();
	}

	public void updateRemark(Long tradeId, String remark) {
		SQLQuery query = getSession().createSQLQuery("UPDATE G_Trade_TradeList SET AppendRemark = :remark WHERE TradeID = :tradeId");
		query.setString("remark", remark);
		query.setLong("tradeId", tradeId);
		query.executeUpdate();
	}
	
	public void freezeTrade(Long tradeId, String freezeReason) {
		SQLQuery query = getSession().createSQLQuery("UPDATE G_Trade_TradeList SET FreezeReason = :freezeReason WHERE TradeID = :tradeId");
		query.setString("freezeReason", freezeReason);
		query.setLong("tradeId", tradeId);
		query.executeUpdate();
		
	}

	@Override
	public void saveRawTrade(JRawTrade rawTrade) {
		getSession().save(rawTrade);
		
	}

	@Override
	public void saveRawOrder(JRawOrder rawOrder) {
		getSession().save(rawOrder);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getRawTradeList(Integer curStatus) {
		Query query = getSession().createQuery("FROM JRawTrade WHERE curStatus = :curStatus ORDER BY payTime DESC");
		query.setInteger("curStatus", curStatus);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawOrder> getRawOrderList(Long tid) {
		Query query = getSession().createQuery("FROM JRawOrder WHERE tid = :tid");
		query.setLong("tid", tid);
		return query.list();
	}

	@Override
	public String getTbkShopUrl(String providerNick) {
		if (providerNick != null) {
			JTbkShopUrl tbkShopUrl = (JTbkShopUrl) getSession().get(JTbkShopUrl.class, providerNick);
			if (tbkShopUrl != null) {
				return tbkShopUrl.getTbkShopUrl();
			}
		}
		return "";
	}

}
