package com.qganlan.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.TradeDao;
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

}
