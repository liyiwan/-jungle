package com.qganlan.webapp.pages.goods;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.service.GoodsManager;

public class GoodsNotOnSale {

	@Property
	private List<GoodsDTO> goodsList;
	
	@Property
	private GoodsDTO goods;
	
	@Inject
	private GoodsManager goodsManager;
	
	public void setUpRender() {
		goodsList = goodsManager.getNotOnSaleGoodsList();
	}

}
