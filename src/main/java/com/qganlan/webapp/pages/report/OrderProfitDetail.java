package com.qganlan.webapp.pages.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.model.Shop;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.TradeManager;
import com.qganlan.service.ShopManager;

public class OrderProfitDetail {

	@Property
	@PageActivationContext
    private Long tradeId;
	
	@Property
	private Trade trade;

	@Property
    private TradeGoods tradeGoods;
	
	@Inject
    private TradeManager tradeManager;
	
	@Inject
    private ShopManager shopManager;
	
	@Inject
    private GoodsManager goodsManager;
	
	public void setUpRender() {
		trade = tradeManager.getTrade(tradeId);
	}
    
    public BigDecimal getGoodsTotalProfit() {
    	return trade.getGoodsTotal().subtract(trade.getGoodsCost()).setScale(2, RoundingMode.HALF_UP);
    }
    
    public BigDecimal getPostageProfit() {
    	return trade.getPostageTotal().subtract(trade.getPostage()).setScale(2, RoundingMode.HALF_UP);
    }

    public String getGrossProfitRate() {
        BigDecimal totalProfit = trade.getTotalProfit();
        BigDecimal allTotal = trade.getAllTotal();
        if (allTotal.compareTo(BigDecimal.ZERO) != 0) {
            return totalProfit.divide(allTotal, RoundingMode.HALF_UP).movePointRight(2).toString() + "%";
        } else {
            return "0%";
        }
    }
    
    public BigDecimal getGoodsGrossProfit() {
    	BigDecimal profit = tradeGoods.getSellTotal().subtract(tradeGoods.getGoodsCost());
        return profit.setScale(2, RoundingMode.HALF_UP);
    }
    
    public String getGoodsGrossProfitRate() {
    	BigDecimal profit = tradeGoods.getSellTotal().subtract(tradeGoods.getGoodsCost());
        if (tradeGoods.getSellTotal().compareTo(BigDecimal.ZERO) != 0) {
        	BigDecimal percentage = profit.divide(tradeGoods.getSellTotal(), RoundingMode.HALF_UP).movePointRight(2).setScale(2, RoundingMode.HALF_UP);
            return percentage.toString() + "%";
        } else {
            return "0.00%";
        }
    }

	public List<TradeGoods> getTradeGoodsList() {
		return tradeManager.getTradeGoodsList(trade.getTradeId());
	}
	
	public GoodsSpecDTO getGoodsSpec() {
		return goodsManager.getGoodsSpec(tradeGoods.getGoodsId(), tradeGoods.getSpecId());
	}
	
	public Shop getShop() {
		return shopManager.getShop(trade.getShopId());
	}
    
	public String getRowStyle() {
		BigDecimal profit = tradeGoods.getSellPrice().multiply(new BigDecimal(tradeGoods.getSellCount())).subtract(tradeGoods.getGoodsCost());
		if (profit.compareTo(BigDecimal.ZERO) < 0) {
			return "color:red";
		} else {
			return "";
		}
	}
	
	public BigDecimal getDiscount() {
		BigDecimal d = tradeGoods.getSellPrice().multiply(new BigDecimal(tradeGoods.getSellCount()));
		if (tradeGoods.getSellTotal() != null && d.compareTo(BigDecimal.ZERO) > 0) {
			return tradeGoods.getSellTotal().divide(d, 2, RoundingMode.HALF_UP);
		} else {
			return BigDecimal.ZERO;
		}
	}
    
}
