package com.qganlan.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.TaobaoSessionKeyDao;
import com.qganlan.model.TaobaoSessionKey;

@Repository("taobaoSessionKeyDao")
public class TaobaoSessionKeyDaoHibernate extends GenericDaoHibernate<TaobaoSessionKey, String> implements TaobaoSessionKeyDao {

	public TaobaoSessionKeyDaoHibernate() {
		super(TaobaoSessionKey.class);
	}

}
