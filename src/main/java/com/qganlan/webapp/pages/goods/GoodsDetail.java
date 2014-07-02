package com.qganlan.webapp.pages.goods;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.common.encoders.GoodsProviderEncoder;
import com.qganlan.common.encoders.GoodsSpecEncoder;
import com.qganlan.dto.GoodsDTO;
import com.qganlan.dto.GoodsProviderDTO;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.model.TaobaoItem;
import com.qganlan.service.GoodsManager;

public class GoodsDetail {
	@Property
	@PageActivationContext
	private Long goodsId;
	@Property
	private GoodsDTO goods;
	@Property
	private GoodsSpecDTO goodsSpec;
	@Property
	private List<GoodsSpecDTO> goodsSpecList;
	
	private List<GoodsSpecDTO> selectedGoodsSpecList;
	
	private boolean inFormSubmission;
	@Property
    private final GoodsSpecEncoder goodsSpecEncoder = new GoodsSpecEncoder();
	@Property
    private final GoodsProviderEncoder goodsProviderEncoder = new GoodsProviderEncoder();
	@Property
    private GoodsProviderDTO goodsProvider;
	@Property
	private TaobaoItem taobaoItem;
	
	@Inject
	private GoodsManager goodsManager;
	
	public void onActivate() {
    	inFormSubmission = false;
    }
	
	public void onPrepareForSubmit() {
    	inFormSubmission = true;
    	selectedGoodsSpecList = new ArrayList<GoodsSpecDTO>();
    }

	public void setGoodsSpecSelected(boolean selected) {
        if (inFormSubmission) {
        	if (selected) {
        	    selectedGoodsSpecList.add(goodsSpec);
        	}
        }
    }
	
	public boolean isGoodsSpecSelected() {
    	return false;
    }
	
	public void setUpRender() {
		goods = goodsManager.getGoods(goodsId);
		goodsSpecList = goodsManager.getGoodsSpecList(goodsId);
	}
	
	public String getPicPath() {
		if (goods.getPicPath() == null || goods.getPicPath().trim().equals("")) {
			return "#";
		} else {
			return goods.getPicPath() + "_310x310.jpg";
		}
	}

	public void onSelectedFromDeleteGoodsSpec() {
		for (GoodsSpecDTO aGoodsSpec : selectedGoodsSpecList) {
        	goodsManager.deleteGoodsSpec(aGoodsSpec.getGoodsId(), aGoodsSpec.getSpecId());
        }
	}
	
	public void onDisableStockWarning() {
		goodsManager.disableStockWarning(goodsId);
	}

}
