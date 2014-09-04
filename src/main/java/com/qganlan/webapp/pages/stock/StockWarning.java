package com.qganlan.webapp.pages.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.appfuse.model.User;
import org.slf4j.Logger;

import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.StockSpecDTO;
import com.qganlan.model.Provider;
import com.qganlan.model.ProviderClass;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.ProviderManager;
import com.qganlan.service.StockManager;
import com.qganlan.webapp.services.SelectIdModelFactory;

public class StockWarning {
	@Inject
    private Logger logger;
	
	@Inject
    private Messages messages;
	
	@Inject
	private StockManager stockManager;
	
	@Inject
	private ProviderManager providerManager;
	
	@Inject
	private GoodsManager goodsManager;
	
	@Inject
    private BeanModelSource beanModelSource;
	
	@Inject
    private JavaScriptSupport jsSupport;
	
	@Property
    private User currentUser;
	
	private String infoMessage;
	
	@Property
    private String errorMessage;
	
	@Property
	private Map<Long, List<GoodsDTO>> goodsMap = null;
	
	@Property
	private List<Provider> providerList = null;
	
	@Property
	private Provider provider;

	@Property
	private GoodsDTO goods;
	
	@Property
	private StockSpecDTO stockSpec;
	
	@Property
	@Persist(PersistenceConstants.SESSION)
	private Long classId;
	
	@Property
	private SelectModel providerClassSelectModel;
	
	@Inject
	protected SelectIdModelFactory selectIdModelFactory;

	public void setUpRender() {
		if (classId == null) { classId = 1000L; }
		List<ProviderClass> providerClassList = providerManager.getProviderClassList();
		providerClassSelectModel = selectIdModelFactory.create(providerClassList, "className", "classId");
		goodsMap = new HashMap<Long, List<GoodsDTO>>();
		providerList = new ArrayList<Provider>();
		List<GoodsDTO> goodsList = stockManager.getStockWarningGoodsList(classId);
		for (GoodsDTO goods : goodsList) {
			Long providerId = goods.getProviderId();
			if (providerId == null) {
				providerId = 0L;
			}
			List<GoodsDTO> list = goodsMap.get(providerId);
			if (list == null) {
				list = new ArrayList<GoodsDTO>();
				goodsMap.put(providerId, list);
				Provider p = providerManager.getProvider(providerId);
				if (p != null) {
					providerList.add(p);
				} else {
					p = new Provider();
					p.setProviderId(0L);
					p.setProviderName("无供货商");
					providerList.add(p);
				}
			}
			list.add(goods);
		}
	}

	public List<GoodsDTO> getGoodsList() {
		return goodsMap.get(provider.getProviderId());
	}

	public String getPicPath() {
		if (goods.getPicPath() == null || goods.getPicPath().trim().equals("")) {
			return "#";
		} else {
			return goods.getPicPath() + "_310x310.jpg";
		}
	}
	
	public List<StockSpecDTO> getStockSpecList() {
		return stockManager.getStockSpecByGoods(goods.getGoodsId());
	}

	public String getShortStock() {
		Long shortStock = stockSpec.getSoldCount() - stockSpec.getStock() - stockSpec.getPurchaseCount();
		if (shortStock > 0) {
			return "(卖出:" + stockSpec.getSoldCount() + " 缺货:" + shortStock + ")";
		} else {
			return "";
		}
	}
	
	public Long getMaxShortStock() {
		return goods.getSellCountMonth() - goods.getStock() > 0 ? goods.getSellCountMonth() - goods.getStock() : 0;
	}

	public String getStockSpecStyle() {
		Long shortStock = stockSpec.getSoldCount() - stockSpec.getStock() - stockSpec.getPurchaseCount();
		if ((stockSpec.getStock() == 0 && (stockSpec.getFlagId() == null || stockSpec.getFlagId() != 13)) || shortStock > 0) {
			return "color:red;";
		} else if (stockSpec.getStock() == 0 && (stockSpec.getFlagId() != null && stockSpec.getFlagId() == 13)) {
			return "color:orange;";
		} else {
			return "";
		}
	}

	public void onDisableStockWarning(Long goodsId) {
		goodsManager.disableStockWarning(goodsId);
	}
	
	public void onHideOneDay(Long goodsId) {
		goodsManager.hideOneDay(goodsId);
	}
}
