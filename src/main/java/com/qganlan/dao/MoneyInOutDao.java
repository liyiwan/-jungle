package com.qganlan.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.qganlan.dto.MoneyInOutDTO;
import com.qganlan.model.MoneyInOut;

public interface MoneyInOutDao extends GenericDao<MoneyInOut, Long> {

	List<MoneyInOutDTO> getTop10MoneyInOutList(String string);

}
