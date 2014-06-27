package com.qganlan.webapp.pages.goods;

import java.util.List;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.service.GoodsManager;

public class GoodsList {
	@Property
	private List<GoodsDTO> goodsList;
	@Property
	private GoodsDTO goods;
	@Inject
	private GoodsManager goodsManager;
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String searchTerm;
	
	public void setUpRender() {
		goodsList = goodsManager.getGoodsList(searchTerm);
	}
	
	public String getPicPath() {
		if (goods.getPicPath() == null || goods.getPicPath().trim().equals("")) {
			return "#";
		} else {
			return goods.getPicPath() + "_310x310.jpg";
		}
	}
}
