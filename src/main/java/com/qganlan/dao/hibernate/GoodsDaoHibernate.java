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
import com.qganlan.dto.ThirdPartyGoods;
import com.qganlan.model.ApiSysMatch;
import com.qganlan.model.Goods;
import com.qganlan.model.ItemUpdate;
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
		query.addScalar("SoldCount", StandardBasicTypes.LONG);
		query.addScalar("PurchaseCount", StandardBasicTypes.LONG);
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
		query.addScalar("PicPath", StandardBasicTypes.STRING);
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
	public List<GoodsSpecDTO> getGoodsSpecList(Long goodsId) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("GoodsSpecList");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecCode", StandardBasicTypes.STRING);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
		query.addScalar("FlagId", StandardBasicTypes.LONG);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.setLong("goodsId", goodsId);
		query.setResultTransformer(Transformers.aliasToBean(GoodsSpecDTO.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<ApiSysMatch> getApiSysMatch(Long goodsId, Long specId) {
		Query query = getSession().createQuery("FROM ApiSysMatch WHERE goodsId = :goodsId AND specId = :specId");
		query.setLong("goodsId", goodsId);
		query.setLong("specId", specId);
		return query.list();
	}

	public void saveOrUpdate(ApiSysMatch apiSysMatch) {
		getSession().saveOrUpdate(apiSysMatch);
	}

	@SuppressWarnings("unchecked")
	public List<GoodsSpecDTO> getOutOfStockGoods() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("OutOfStockGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecCode", StandardBasicTypes.STRING);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.addScalar("SoldCount", StandardBasicTypes.LONG);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.addScalar("PurchaseCount", StandardBasicTypes.LONG);
		query.setResultTransformer(Transformers.aliasToBean(GoodsSpecDTO.class));
		return query.list();
	}

	public GoodsSpecDTO getGoodsSpec(Long goodsId, Long specId) {
		SQLQuery query = (SQLQuery) getSession().createSQLQuery("SELECT G_Goods_GoodsSpec.GoodsID AS GoodsId, G_Goods_GoodsSpec.SpecID AS SpecId, G_Goods_GoodsSpec.SpecCode AS SpecCode, G_Goods_GoodsSpec.SpecName AS SpecName, G_Goods_GoodsList.GoodsNo AS GoodsNo, G_Goods_GoodsList.GoodsName AS GoodsName FROM G_Goods_GoodsSpec, G_Goods_GoodsList WHERE G_Goods_GoodsList.GoodsID=G_Goods_GoodsSpec.GoodsID AND G_Goods_GoodsSpec.GoodsID = :goodsId AND G_Goods_GoodsSpec.SpecID = :specId");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecCode", StandardBasicTypes.STRING);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
		query.setLong("goodsId", goodsId);
		query.setLong("specId", specId);
		query.setResultTransformer(Transformers.aliasToBean(GoodsSpecDTO.class));
		return (GoodsSpecDTO) query.uniqueResult();
	}

	public GoodsDTO getGoods(Long goodsId) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("SingleGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.addScalar("MultiSpec", StandardBasicTypes.LONG);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.addScalar("SellCountMonth", StandardBasicTypes.LONG);
		query.addScalar("CostPrice", StandardBasicTypes.BIG_DECIMAL);
		query.addScalar("FlagId", StandardBasicTypes.LONG);
		query.setLong("goodsId", goodsId);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return (GoodsDTO) query.uniqueResult();
	}

	public void deleteGoodsSpec(Long goodsId, Long specId) {
		SQLQuery query = getSession().createSQLQuery("DELETE FROM G_API_SysMatch WHERE GoodsID = :goodsId AND SpecID = :specId");
		query.setLong("goodsId", goodsId);
		query.setLong("specId", specId);
		query.executeUpdate();
		query = getSession().createSQLQuery("DELETE FROM G_Stock_Spec WHERE GoodsID = :goodsId AND SpecID = :specId");
		query.setLong("goodsId", goodsId);
		query.setLong("specId", specId);
		query.executeUpdate();
		query = getSession().createSQLQuery("DELETE FROM G_Goods_GoodsSpec WHERE GoodsID = :goodsId AND SpecID = :specId");
		query.setLong("goodsId", goodsId);
		query.setLong("specId", specId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<GoodsDTO> getGoodsList(String searchTerm) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("AllGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.addScalar("SellCountMonth", StandardBasicTypes.LONG);
		query.addScalar("CostPrice", StandardBasicTypes.BIG_DECIMAL);
		query.setString("searchTerm", searchTerm == null ? "%" : searchTerm + "%");
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return query.list();
	}

	@Override
	public GoodsSpecDTO getGoodsSpecBySkuOuterId(String outerId) {
		SQLQuery query = (SQLQuery) getSession().createSQLQuery("SELECT G_Goods_GoodsSpec.GoodsID AS GoodsId, G_Goods_GoodsSpec.SpecID AS SpecId, G_Goods_GoodsSpec.SpecCode AS SpecCode, G_Goods_GoodsSpec.FlagId AS FlagId, G_Goods_GoodsSpec.SpecName AS SpecName, G_Goods_GoodsList.GoodsNo AS GoodsNo, G_Goods_GoodsList.GoodsName AS GoodsName FROM G_Goods_GoodsSpec, G_Goods_GoodsList WHERE G_Goods_GoodsSpec.GoodsID = G_Goods_GoodsList.GoodsID AND (G_Goods_GoodsList.GoodsNO + G_Goods_GoodsSpec.SpecCode) LIKE :outerId");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecCode", StandardBasicTypes.STRING);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
		query.addScalar("FlagId", StandardBasicTypes.LONG);
		query.setString("outerId", outerId);
		query.setResultTransformer(Transformers.aliasToBean(GoodsSpecDTO.class));
		return (GoodsSpecDTO) query.uniqueResult();
	}

	@Override
	public ApiSysMatch getApiSysMatch(String numIid, String skuId) {
		Query query = getSession().createQuery("FROM ApiSysMatch WHERE numIid = :numIid AND skuId = :skuId");
		query.setString("numIid", numIid);
		query.setString("skuId", skuId);
		return (ApiSysMatch) query.uniqueResult();
	}

	@Override
	public GoodsDTO getGoodsByGoodsNo(String outerId) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("SingleGoodsByGoodsNo");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.addScalar("MultiSpec", StandardBasicTypes.LONG);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.addScalar("SellCountMonth", StandardBasicTypes.LONG);
		query.addScalar("CostPrice", StandardBasicTypes.BIG_DECIMAL);
		query.setString("goodsNo", outerId);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return (GoodsDTO) query.uniqueResult();
	}

	@Override
	public void deleteStaleApiSysMatch(Long numIid, Long skuId) {
		SQLQuery query = getSession().createSQLQuery("DELETE FROM G_API_SysMatch WHERE Numiid = :numIid AND Skuid = :skuId");
		query.setLong("numIid", numIid);
		query.setLong("skuId", skuId);
		query.executeUpdate();
	}

	@Override
	public void recordItemUpdate(final Long numIid, final String nick) {
		synchronized(this) {
		Session session = getSession();
			ItemUpdate itemUpdate = (ItemUpdate) session.get(ItemUpdate.class, numIid);
			if (itemUpdate == null) {
				itemUpdate = new ItemUpdate();
				itemUpdate.setNumIid(numIid);
				itemUpdate.setNick(nick);
	    		itemUpdate.setTryCount(0);
	    		session.save(itemUpdate);
	    		session.flush();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemUpdate> getItemUpdateList() {
		return getSession().createQuery("FROM ItemUpdate").list();
	}

	@Override
	public void deleteItemUpdate(ItemUpdate itemUpdate) {
		Query q = getSession().createQuery("DELETE ItemUpdate WHERE numIid = :numIid");
		q.setLong("numIid", itemUpdate.getNumIid());
		q.executeUpdate();
	}

	@Override
	public void saveItemUpdate(ItemUpdate itemUpdate) {
		getSession().save(itemUpdate);
	}

	@Override
	public void deleteApiSysMatch(String numIid) {
		Query q = getSession().createQuery("DELETE ApiSysMatch WHERE numIid LIKE :numIid");
		q.setString("numIid", numIid);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ThirdPartyGoods> getThirdPartyGoodsList() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("ThirdPartyGoodsList");
		query.addScalar("TradeGoodsNO", StandardBasicTypes.STRING);
		query.addScalar("TradeGoodsName", StandardBasicTypes.STRING);
		query.addScalar("TradeGoodsSpec", StandardBasicTypes.STRING);
		query.addScalar("GoodsCount", StandardBasicTypes.LONG);
		query.setResultTransformer(Transformers.aliasToBean(ThirdPartyGoods.class));
		return query.list();
	}
}
