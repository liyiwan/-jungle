package com.qganlan.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.qganlan.dao.MyConfigDao;
import com.qganlan.model.MyConfig;

@Repository("myConfigDao")
public class MyConfigDaoHibernate extends GenericDaoHibernate<MyConfig, String> implements MyConfigDao {

	public MyConfigDaoHibernate() {
		super(MyConfig.class);
	}

}
