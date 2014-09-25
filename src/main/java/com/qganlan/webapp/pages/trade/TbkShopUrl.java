package com.qganlan.webapp.pages.trade;

import java.util.List;

import org.apache.tapestry5.annotations.Property;

import com.qganlan.model.JTbkShopUrl;

public class TbkShopUrl {

	@Property
	private List<JTbkShopUrl> tbkShopUrlList;
	
	@Property
	private JTbkShopUrl tbkShopUrl;
}
