package com.qganlan.webapp.pages.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.model.Shop;
import com.qganlan.model.Trade;
import com.qganlan.service.ShopManager;
import com.qganlan.service.TradeManager;

public class ReportOrderProfit {
    
    @Property
    @Persist(PersistenceConstants.FLASH)
    private Date              startDate;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private Date              endDate;
    
    @Inject
    private TradeManager tradeManager;
 
    @Inject
    private ShopManager shopManager;
    
    @Property
    private List<Trade>     tradeList;

    @Property
    private Trade           trade;

	@Property
    private int               sndCount;
    
    @Property
    private BigDecimal        sumGoodsTotal   = BigDecimal.ZERO;
    
    @Property
    private BigDecimal        sumPostageTotal = BigDecimal.ZERO;
    
    @Property
    private BigDecimal        sumAllTotal     = BigDecimal.ZERO;
    
    @Property
    private BigDecimal        sumGoodsCost    = BigDecimal.ZERO;
    
    @Property
    private BigDecimal        sumPostage      = BigDecimal.ZERO;
    
    @Property
    private BigDecimal        sumTotalProfit  = BigDecimal.ZERO;

	@Property
    private String            avgProfitRate   = "";

	@Property
    private Set<Map.Entry<Shop, Long>> shopSndCountEntrySet;

	@Property
    private Map.Entry<Shop, Long> shopSndCountEntry;
    
    private Map<Long, Shop> shopMap;
    
    @Property
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public void setUpRender() {
    	shopMap = new HashMap<Long, Shop>();
    	if (startDate == null) { startDate = new Date(); }
        if (endDate == null) { endDate = new Date(); }
        tradeList = tradeManager.getSndTradeList(startDate, endDate);
        sndCount = tradeList.size();
        HashMap<Shop, Long> shopSndCountMap = new HashMap<Shop, Long>();
        for (Trade trade : tradeList) {
        	Shop shop = shopMap.get(trade.getShopId());
        	if (shop == null) {
        		shop = shopManager.getShop(trade.getShopId());
        		shopMap.put(trade.getShopId(), shop);
        	}
        	Long count = shopSndCountMap.get(shop);
        	if (count == null) {
        		shopSndCountMap.put(shop, 1L);
        	} else {
        		shopSndCountMap.put(shop, count + 1);
        	}
            sumGoodsTotal = sumGoodsTotal.add(trade.getGoodsTotal());
            sumPostageTotal = sumPostageTotal.add(trade.getPostageTotal());
            sumAllTotal = sumAllTotal.add(trade.getAllTotal());
            sumGoodsCost = sumGoodsCost.add(trade.getGoodsCost());
            sumPostage = sumPostage.add(trade.getPostage());
            sumTotalProfit = sumTotalProfit.add(trade.getTotalProfit());
            if (sumAllTotal.compareTo(BigDecimal.ZERO) != 0) {
                avgProfitRate = sumTotalProfit.divide(sumAllTotal, RoundingMode.HALF_UP).movePointRight(2).toString() + "%";
            } else {
                avgProfitRate = "0%";
            }
        }
        shopSndCountEntrySet = shopSndCountMap.entrySet();
    }
    
    public String caculateGrossProfitRate() {
        BigDecimal totalProfit = trade.getTotalProfit();
        BigDecimal allTotal = trade.getAllTotal();
        if (allTotal.compareTo(BigDecimal.ZERO) != 0) {
            return totalProfit.divide(allTotal, RoundingMode.HALF_UP).movePointRight(2).toString() + "%";
        } else {
            return "0%";
        }
    }
    
    public String getRowStyle() {
    	BigDecimal totalProfit = trade.getTotalProfit();
        BigDecimal allTotal = trade.getAllTotal();
        if (allTotal.compareTo(BigDecimal.ZERO) != 0) {
        	BigDecimal rate = totalProfit.divide(allTotal, RoundingMode.HALF_UP);
        	if (rate.compareTo(new BigDecimal("0.1")) < 0) {
        		return "color:red;";
        	} else {
        		return "";
        	}
        } else {
        	return "color:red;";
        }
    }

    public String getShopName() {
    	Shop shop = shopMap.get(trade.getShopId());
    	return shop.getShopName();
    }
    
    public void onLastDay() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_YEAR, -1);
    	startDate = cal.getTime();
    	endDate = cal.getTime();
    }
    
    public void onLastDay2() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_YEAR, -2);
    	startDate = cal.getTime();
    	endDate = cal.getTime();
    }
    
    public void onThisWeek() {
    	Calendar cal = Calendar.getInstance();
    	int max = cal.getActualMaximum(Calendar.DAY_OF_WEEK);
    	int min = cal.getActualMinimum(Calendar.DAY_OF_WEEK);
    	cal.set(Calendar.DAY_OF_WEEK, min);
    	startDate = cal.getTime();
    	cal.set(Calendar.DAY_OF_WEEK, max);
    	endDate = cal.getTime();
    }
    
    public void onLastWeek() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, 1);
    	cal.add(Calendar.DAY_OF_WEEK, -1);
    	endDate = cal.getTime();
    	cal.add(Calendar.DAY_OF_WEEK, -6);
    	startDate = cal.getTime();
    }
    
    public void onThisMonth() {
    	Calendar cal = Calendar.getInstance();
    	int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    	int min = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    	cal.set(Calendar.DAY_OF_MONTH, min);
    	startDate = cal.getTime();
    	cal.set(Calendar.DAY_OF_MONTH, max);
    	endDate = cal.getTime();
    }
    
    public void onLastMonth() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	endDate = cal.getTime();
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	startDate = cal.getTime();
    }
}
