package com.qganlan.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.TradeDao;
import com.qganlan.model.GApiTrade;
import com.qganlan.model.GApiTradeGoods;
import com.qganlan.model.GCfgLogistics;
import com.qganlan.model.JLogisticsCompany;
import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.JTbkShopUrl;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;
import com.taobao.api.domain.Order;

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
		SQLQuery query = getSession().createSQLQuery("UPDATE J_RAW_TRADE SET CUR_STATUS = 11 WHERE tid = :tid");
		query.setLong("tid", tid);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getRecentRawTradeList() {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM J_RAW_TRADE WHERE DATEDIFF(DAY, CONVERT(VARCHAR(100), PAY_TIME,20), GETDATE()) <=30 ORDER BY PAY_TIME DESC");
		query.addEntity(JRawTrade.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getInProgressThirdPartyRawTradeList() {
		Query query = getSession().createQuery("FROM JRawTrade WHERE curStatus <> 11 ORDER BY payTime DESC");
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
	public void updateLogistics(JRawOrder rawOrder) {
		Query query = getSession().createQuery("UPDATE JRawOrder SET invoiceNo = :invoiceNo, companyCode = :companyCode, logisticsCompany = :logisticsCompany, curStatus = 11 WHERE tid = :tid AND oid = :oid");
		query.setString("invoiceNo", rawOrder.getInvoiceNo());
		query.setString("companyCode", rawOrder.getCompanyCode());
		query.setString("logisticsCompany", rawOrder.getLogisticsCompany());
		query.setLong("tid", rawOrder.getTid());
		query.setLong("oid", rawOrder.getOid());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTbkShopUrl> getTbkShopUrlList() {
		Query query = getSession().createQuery("FROM JTbkShopUrl");
		return query.list();
	}

	@Override
	public void saveTbkShopUrl(String nick, String tbkUrl) {
		if (nick != null) {
			JTbkShopUrl tbkShopUrl = (JTbkShopUrl) getSession().get(JTbkShopUrl.class, nick);
			if (tbkShopUrl == null) {
				tbkShopUrl = new JTbkShopUrl();
				tbkShopUrl.setNick(nick);
				tbkShopUrl.setTbkShopUrl(tbkUrl);
			}
			getSession().saveOrUpdate(tbkShopUrl);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getReFundRawTradeList() {
		Query query = getSession().createQuery("FROM JRawTrade WHERE curStatus = 3 ORDER BY payTime DESC");
		return query.list();
	}

	@Override
	public void refundTrade(com.taobao.api.domain.Trade trade) {
		Query updateRawOrder = getSession().createQuery("UPDATE JRawOrder SET refundId = :refundId, refundStatus = :refundStatus WHERE tid = :tid AND oid = :oid");
		for (Order order : trade.getOrders()) {
			updateRawOrder.setLong("refundId", order.getRefundId());
			updateRawOrder.setString("refundStatus", order.getRefundStatus());
			updateRawOrder.setLong("tid", trade.getTid());
			updateRawOrder.setLong("oid", order.getOid());
			updateRawOrder.executeUpdate();
		}
		Query query = getSession().createQuery("UPDATE JRawTrade SET curStatus = 3 WHERE tid = :tid");
		query.setLong("tid", trade.getTid());
		query.executeUpdate();
	}

	@Override
	public void refundBuyerReturnGoods(Long tid, Long oid, String companyName, String sid) {
		Query updateRawOrder = getSession().createQuery("UPDATE JRawOrder SET refundCompanyName = :refundCompanyName, refundSid = :refundSid WHERE tid = :tid AND oid = :oid");
		updateRawOrder.setString("refundCompanyName", companyName);
		updateRawOrder.setString("refundSid", sid);
		updateRawOrder.setLong("tid", tid);
		updateRawOrder.setLong("oid", oid);
		updateRawOrder.executeUpdate();
	}

	@Override
	public void refundCreated(Long tid, Long oid, Long refund_id) {
		Query updateRawOrder = getSession().createQuery("UPDATE JRawOrder SET refundId = :refundId WHERE tid = :tid AND oid = :oid");
		updateRawOrder.setLong("refundId", refund_id);
		updateRawOrder.setLong("tid", tid);
		updateRawOrder.setLong("oid", oid);
		updateRawOrder.executeUpdate();
		Query updateRawTrade = getSession().createQuery("UPDATE JRawTrade SET curStatus = 3 WHERE tid = :tid AND curStatus <> 3");
		updateRawTrade.setLong("tid", tid);
		updateRawTrade.executeUpdate();
	}

	@Override
	public void setRawOrderStatus(JRawOrder rawOrder, long l) {
		Query updateRawTrade = getSession().createQuery("UPDATE JRawOrder SET curStatus = :curStatus WHERE tid = :tid AND oid = :oid");
		updateRawTrade.setLong("tid", rawOrder.getTid());
		updateRawTrade.setLong("oid", rawOrder.getOid());
		updateRawTrade.setLong("curStatus", l);
		updateRawTrade.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JRawTrade> getPendingThirdPartyRawTradeList() {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM J_RAW_TRADE WHERE CUR_STATUS <> 11 AND EXISTS (SELECT * FROM J_RAW_ORDER WHERE TID = J_RAW_TRADE.TID AND ((PURCHASE_NICK IS NULL OR PURCHASE_NICK = '') OR (PURCHASE_TID IS NULL OR PURCHASE_TID = 0))) ORDER BY PAY_TIME DESC");
		query.addEntity(JRawTrade.class);
		return query.list();
	}

	private Object billIdLock = new Object();
	@Override
	public Long getNextTradeBillId() {
		synchronized(billIdLock) {
			Query query = getSession().createQuery("SELECT curNo + 1 FROM GSysCreateNo WHERE tableName = 'G_API_TradeList' AND fieldName = 'BillID'");
			Long no1 = (Long) query.uniqueResult();
			if (no1 == null) {
				no1 = 1L;
			}
			query = getSession().createQuery("SELECT MAX(billId) + 1 FROM GApiTrade");
			Long no2 = (Long) query.uniqueResult();
			if (no2 == null) {
				no2 = 1L;
			}
			Long no = no1 > no2 ? no1 : no2;
			query = getSession().createQuery("UPDATE GSysCreateNo SET curNo = :curNo WHERE tableName = 'G_API_TradeList' AND fieldName = 'BillID'");
			query.setLong("curNo", no);
			query.executeUpdate();
			return no;
		}
	}

	@Override
	public void saveApiTrade(GApiTrade apiTrade) {
		getSession().save(apiTrade);		
	}

	@Override
	public void saveApiTradeGoods(GApiTradeGoods apiTradeGoods) {
		getSession().save(apiTradeGoods);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GApiTrade getApiTrade(Long tid) {
		Query query = getSession().createQuery("FROM GApiTrade WHERE tradeNo = :tradeNo");
		query.setString("tradeNo", tid+"");
		List l = query.list();
		if (l.size() > 0) {
			return (GApiTrade) l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GApiTrade> getApiTradeForSyncLogistics() {
		Query query = getSession().createQuery("FROM GApiTrade WHERE synStatus = 0 OR synStatus = 1");
		return query.list();
	}

	@Override
	public Trade getTradeByTradeID(Long tradeId) {
		return (Trade) getSession().get(Trade.class, tradeId);
	}

	@Override
	public GCfgLogistics getGCfgLogistics(Long logisticsId) {
		return (GCfgLogistics) getSession().get(GCfgLogistics.class, logisticsId);
	}

	@Override
	public void updateLogisticsSuccess(GApiTrade apiTrade) {
		Query query = getSession().createQuery("UPDATE GApiTrade SET synStatus = 4 WHERE billId = :billId");
		query.setLong("billId", apiTrade.getBillId());
		query.executeUpdate();
	}

	@Override
	public void updateLogisticsFail(GApiTrade apiTrade) {
		Query query = getSession().createQuery("UPDATE GApiTrade SET synStatus = 2, synCause = :synCause WHERE billId = :billId");
		query.setLong("billId", apiTrade.getBillId());
		query.setString("synCause", "发货同步失败，请手工发货。");
		query.executeUpdate();
	}
}
