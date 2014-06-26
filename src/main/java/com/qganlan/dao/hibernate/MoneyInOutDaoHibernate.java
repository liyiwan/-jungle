package com.qganlan.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.MoneyInOutDao;
import com.qganlan.dto.MoneyInOutDTO;
import com.qganlan.model.MoneyInOut;

@Repository("moneyInOutDao")
public class MoneyInOutDaoHibernate extends GenericDaoHibernate<MoneyInOut, Long> implements MoneyInOutDao {

	public MoneyInOutDaoHibernate() {
		super(MoneyInOut.class);
	}

	@SuppressWarnings("unchecked")
	public List<MoneyInOutDTO> getTop10MoneyInOutList(String accountName) {
		SQLQuery query = getSession().createSQLQuery("SELECT G_Money_InOut.RegDate AS RegDate, G_Money_InOut.Amount_IN AS AmountIn, G_Money_InOut.Amount_OUT AS AmountOut, G_Money_InOut.Balance AS Balance, G_Money_InOut.Summary AS Summary, G_Cfg_InOutAccountList.AccountName,G_Cfg_InOutItemList.ItemName FROM G_Money_InOut LEFT JOIN G_Cfg_InOutAccountList ON G_Money_InOut.AccountID=G_Cfg_InOutAccountList.AccountID LEFT JOIN G_Cfg_InOutItemList ON G_Money_InOut.ItemID=G_Cfg_InOutItemList.ItemID WHERE G_Cfg_InOutAccountList.AccountName like :accountName ORDER BY G_Money_InOut.InOutID DESC");
		query.addScalar("AccountName", StandardBasicTypes.STRING);
		query.addScalar("RegDate", StandardBasicTypes.DATE);
		query.addScalar("AmountIn", StandardBasicTypes.BIG_DECIMAL);
		query.addScalar("AmountOut", StandardBasicTypes.BIG_DECIMAL);
		query.addScalar("Balance", StandardBasicTypes.BIG_DECIMAL);
		query.addScalar("ItemName", StandardBasicTypes.STRING);
		query.addScalar("Summary", StandardBasicTypes.STRING);
		query.setResultTransformer(Transformers.aliasToBean(MoneyInOutDTO.class));
		query.setString("accountName", accountName);
		query.setMaxResults(10);
		return query.list();
	}

}

