package com.qganlan.dao.hibernate;

import java.util.Calendar;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.GoodsDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.model.ApiSysMatch;
import com.qganlan.model.Goods;
import com.qganlan.model.MyGoods;

@Repository("goodsDao")
public class GoodsDaoHibernate extends GenericDaoHibernate<Goods, Long> implements GoodsDao {
    
	public GoodsDaoHibernate() {
        super(Goods.class);
    }

	@SuppressWarnings("unchecked")
	public List<GoodsDTO> getStockWarningGoodsList(Long classId) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("StockWarningGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.addScalar("SellCountMonth", StandardBasicTypes.LONG);
		query.addScalar("ProviderId", StandardBasicTypes.LONG);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.addScalar("Remark", StandardBasicTypes.STRING);
		query.addScalar("CostPrice", StandardBasicTypes.BIG_DECIMAL);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		query.setLong("classId", classId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<StockSpecDTO> getStockSpecByGoods(Long goodsId) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("StockSpecByGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
		query.addScalar("FlagId", StandardBasicTypes.LONG);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.setLong("goodsId", goodsId);
		query.setResultTransformer(Transformers.aliasToBean(StockSpecDTO.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<GoodsDTO> getGoodsToCheck() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("GoodsToCheck");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("FlagId", StandardBasicTypes.LONG);
		query.addScalar("MultiSpec", StandardBasicTypes.LONG);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return query.list();
	}

	public MyGoods getMyGoods(Long goodsId) {
		return (MyGoods) getSession().get(MyGoods.class, goodsId);
	}

	public void saveMyGoods(MyGoods myGoods) {
		Session sess = getSession();
		sess.saveOrUpdate(myGoods);
		sess.flush();
	}

	public void disableStockWarning(Long goodsId) {
		Query q = getSession().createQuery("UPDATE Goods SET flagId = 13 WHERE goodsId = :goodsId");
		q.setLong("goodsId", goodsId);
		q.executeUpdate();
	}

	public void hideOneDay(Long goodsId) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Query q = getSession().createQuery("UPDATE MyGoods SET StockWarningDate = :stockWarningDate WHERE goodsId = :goodsId");
		q.setLong("goodsId", goodsId);
		q.setDate("stockWarningDate", cal.getTime());
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<GoodsDTO> getSoldOutGoodsList() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("SoldOutGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<GoodsDTO> getNotOnSaleGoodsList() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("NotOnSaleGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return query.list();
	}
	
	public void deleteGoods(Long goodsId) {
		SQLQuery query = getSession().createSQLQuery("DELETE FROM G_API_SysMatch WHERE GoodsID = :goodsId");
		query.setLong("goodsId", goodsId);
		query.executeUpdate();
		query = getSession().createSQLQuery("DELETE FROM G_Stock_Spec WHERE GoodsID = :goodsId");
		query.setLong("goodsId", goodsId);
		query.executeUpdate();
		query = getSession().createSQLQuery("DELETE FROM G_Goods_GoodsSpec WHERE GoodsID = :goodsId");
		query.setLong("goodsId", goodsId);
		query.executeUpdate();
		query = getSession().createSQLQuery("DELETE FROM J_MyGoods WHERE GoodsID = :goodsId");
		query.setLong("goodsId", goodsId);
		query.executeUpdate();
		query = getSession().createSQLQuery("DELETE FROM G_Goods_GoodsList WHERE GoodsID = :goodsId");
		query.setLong("goodsId", goodsId);
		query.executeUpdate();
	}

	public void deleteStaleApiSysMatch(GoodsDTO goods) {
		SQLQuery query = getSession().createSQLQuery("DELETE FROM G_API_SysMatch WHERE GoodsID = :goodsId AND SpecID NOT IN (SELECT SpecID FROM G_Goods_GoodsSpec WHERE GoodsID = :goodsId)");
		query.setLong("goodsId", goods.getGoodsId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<GoodsSpecDTO> getGoodsSpecList(GoodsDTO goods) {
		SQLQuery query = (SQLQuery) getSession().createSQLQuery("SELECT GoodsID AS GoodsId, SpecID AS SpecId, SpecCode AS SpecCode, SpecName AS SpecName, FlagID AS FlagId FROM G_Goods_GoodsSpec WHERE GoodsID = :goodsId");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecCode", StandardBasicTypes.STRING);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
		query.addScalar("FlagId", StandardBasicTypes.LONG);
		query.setLong("goodsId", goods.getGoodsId());
		query.setResultTransformer(Transformers.aliasToBean(GoodsSpecDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public ApiSysMatch getApiSysMatch(String numIid, String skuIid) {
		Query query = getSession().createQuery("FROM ApiSysMatch WHERE numIid = :numIid AND skuId = :skuIid");
		query.setString("numIid", numIid);
		query.setString("skuIid", skuIid);
		List<ApiSysMatch> l = query.list();
		if (l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public void saveOrUpdate(ApiSysMatch apiSysMatch) {
		getSession().saveOrUpdate(apiSysMatch);
	}
}
