package com.qganlan.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.TradeDao;
import com.qganlan.model.JLogisticsCompany;
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

	@Override
	public JRawTrade getRawTrade(Long tid) {
		return (JRawTrade) getSession().get(JRawTrade.class, tid);
	}

	@Override
	public void fillOrder(JRawOrder rawOrder) {
		Query query = getSession().createQuery("UPDATE JRawOrder SET purchaseNick = :purchaseNick, purchaseTid = :purchaseTid WHERE tid = :tid AND oid = :oid");
		query.setString("purchaseNick", rawOrder.getPurchaseNick()==null?"":rawOrder.getPurchaseNick());
		query.setLong("purchaseTid", rawOrder.getPurchaseTid()==null?0:rawOrder.getPurchaseTid());
		query.setLong("tid", rawOrder.getTid());
		query.setLong("oid", rawOrder.getOid());
		query.executeUpdate();
	}

	@Override
	public void completeTrade(Long tid) {
		Query query = getSession().createQuery("UPDATE JRawTrade SET curStatus = :curStatus WHERE tid = :tid");
		query.setInteger("curStatus", 11);
		query.setLong("tid", tid);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getRecentRawTradeList() {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM J_RAW_TRADE WHERE CUR_STATUS <> 11 OR DATEDIFF(DAY, CONVERT(VARCHAR(100), PAY_TIME,20), GETDATE()) <=30 ORDER BY PAY_TIME DESC");
		query.addEntity(JRawTrade.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getInProgressThirdPartyRawTradeList() {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM J_RAW_TRADE WHERE CUR_STATUS <> 11 ORDER BY PAY_TIME DESC");
		query.addEntity(JRawTrade.class);
		return query.list();
	}

	@Override
	public void saveOrUpdate(JLogisticsCompany jcomp) {
		getSession().saveOrUpdate(jcomp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JLogisticsCompany> getLogisticsCompanyList() {
		Query query = getSession().createQuery("FROM JLogisticsCompany");
		return query.list();
	}

	@Override
	public String getLogisticsCompanyCode(String logisticsCompany) {
		Query query = getSession().createQuery("FROM JLogisticsCompany WHERE name = :name");
		query.setString("name", logisticsCompany);
		JLogisticsCompany comp = (JLogisticsCompany) query.uniqueResult();
		return comp.getCode();
	}

	@Override
	public void markSent(JRawTrade rawTrade) {
		SQLQuery query = getSession().createSQLQuery("UPDATE J_RAW_TRADE SET CUR_STATUS = 11 WHERE TID = :tid AND NOT EXISTS (SELECT * FROM J_RAW_ORDER WHERE CUR_STATUS <> 11 AND TID = :tid)");
		query.setLong("tid", rawTrade.getTid());
		query.executeUpdate();
	}

	@Override
	public void markSent(JRawTrade rawTrade, String subTid) {
		Query query = getSession().createQuery("UPDATE JRawOrder SET curStatus = 11 WHERE tid = :tid AND oid = :oid");
		String[] aSubTid = subTid.split(",");
		query.setLong("tid", rawTrade.getTid());
		for (String oid : aSubTid) {
			query.setLong("oid", Long.valueOf(oid));
			query.executeUpdate();
		}
	}

	@Override
	public void updateLogistics(JRawOrder rawOrder) {
		Query query = getSession().createQuery("UPDATE JRawOrder SET invoiceNo = :invoiceNo, companyCode = :companyCode, logisticsCompany = :logisticsCompany WHERE tid = :tid AND oid = :oid");
		query.setString("invoiceNo", rawOrder.getInvoiceNo());
		query.setString("companyCode", rawOrder.getCompanyCode());
		query.setString("logisticsCompany", rawOrder.getLogisticsCompany());
		query.setLong("tid", rawOrder.getTid());
		query.setLong("oid", rawOrder.getOid());
		query.executeUpdate();
	}

}
