package com.qganlan.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.GoodsDao;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.model.Goods;
import com.qganlan.model.MyGoods;

@Repository("goodsDao")
public class GoodsDaoHibernate extends GenericDaoHibernate<Goods, Long> implements GoodsDao {
    
	public GoodsDaoHibernate() {
        super(Goods.class);
    }

	@SuppressWarnings("unchecked")
	public List<GoodsDTO> getStockWarningGoodsList() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("StockWarningGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("GoodsNo", StandardBasicTypes.STRING);
		query.addScalar("GoodsName", StandardBasicTypes.STRING);
		query.addScalar("Stock", StandardBasicTypes.LONG);
		query.addScalar("SellCountMonth", StandardBasicTypes.LONG);
		query.addScalar("ProviderId", StandardBasicTypes.LONG);
		query.addScalar("PicPath", StandardBasicTypes.STRING);
		query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<StockSpecDTO> getStockSpecByGoods(Long goodsId) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("StockSpecByGoods");
		query.addScalar("GoodsId", StandardBasicTypes.LONG);
		query.addScalar("SpecId", StandardBasicTypes.LONG);
		query.addScalar("SpecName", StandardBasicTypes.STRING);
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

}
