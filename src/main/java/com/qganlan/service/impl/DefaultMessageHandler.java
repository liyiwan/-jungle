package com.qganlan.service.impl;

import org.apache.log4j.Logger;
import org.apache.tapestry5.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.service.GoodsManager;
import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TradeManager;

import com.taobao.api.domain.Trade;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;

@Service("messageHandler")
public class DefaultMessageHandler implements MessageHandler {

	private static final Logger logger = Logger.getLogger(DefaultMessageHandler.class);
	
	@Autowired
	private TradeManager tradeManager;
	
	@Autowired
	private TaobaoApiManager taobaoApiManager;
	
	@Autowired
	private GoodsManager goodsManager;

	public void setTradeManager(TradeManager tradeManager) {
		this.tradeManager = tradeManager;
	}

	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}
	
	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public void onMessage(Message message, MessageStatus status) throws Exception {
        try {
        	System.out.println(message.getTopic() + " " + message.getContent());
            if ("taobao_item_ItemAdd".equals(message.getTopic())) {
            	onItemAdd(message);
            } else if ("taobao_item_ItemDelete".equals(message.getTopic())) {
            	onItemDelete(message);
            } else if ("taobao_item_ItemUpdate".equals(message.getTopic())) {
            	onItemUpdate(message);
            } else if ("taobao_item_ItemUpshelf".equals(message.getTopic())) {
            	onItemUpshelf(message);
            } else if ("taobao_trade_TradeMemoModified".equals(message.getTopic())) {
            	onTradeMemoModified(message);
            } else if ("taobao_trade_TradeBuyerPay".equals(message.getTopic())) {
            	onTradeBuyerPay(message);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
            status.fail();
        }  
	}
	
	private void onTradeBuyerPay(Message message) {
		
		JSONObject jsonObject = new JSONObject(message.getContent());
		String sellerNick = jsonObject.getString("seller_nick");
		Long tid = jsonObject.getLong("tid");
		try {
			Trade trade = taobaoApiManager.getTradeFullInfo(tid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(sellerNick));
			if (trade != null) {
				tradeManager.notifyByEmail(trade);
				tradeManager.recordThirdPartyTrade(trade);
			}
		} catch (Throwable t) {
	    	logger.error("处理消息异常", t);
	    }
	}

	private void onItemAdd(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
    	Long numIid = jsonObject.getLong("num_iid");
    	String nick = jsonObject.getString("nick");
    	if (nick != null && !nick.equals("")) {
    		goodsManager.recordItemUpdate(numIid, nick);
    	}
	}
	
	private void onItemDelete(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
    	Long numIid = jsonObject.getLong("num_iid");
		try {
			goodsManager.deleteApiSysMatch(numIid);
		} catch (Throwable t) {
			logger.error("RESOLVE API SYS MATCH ERROR.", t);
			logger.error(message.getTopic());
            logger.error(message.getContent());
		}
	}

	private void onItemUpdate(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
    	Long numIid = jsonObject.getLong("num_iid");
    	String nick = jsonObject.getString("nick");
    	if (nick != null && !nick.equals("")) {
    		goodsManager.recordItemUpdate(numIid, nick);
    	}
	}
	
	private void onItemUpshelf(Message message) {
//		JSONObject jsonObject = new JSONObject(message.getContent());
//    	Long numIid = jsonObject.getLong("num_iid");
//    	String nick = jsonObject.getString("nick");
//		Item item = null;
//	    try {
//	    	item = taobaoApiManager.getTaobaoItemByNumIid(numIid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(nick));
//	    } catch (Throwable t) {
//	    	logger.error("处理消息异常", t);
//	    }
//	    if (item != null) {
//    		try {
//    			goodsManager.resolveApiSysMatch(item);
//    		} catch (Throwable t) {
//    			logger.error("RESOLVE API SYS MATCH ERROR.", t);
//    			logger.error(message.getTopic());
//                logger.error(message.getContent());
//    		}
//    	}
	}
	
	private void onTradeMemoModified(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
    	String nick = jsonObject.getString("seller_nick");
    	Long tid = jsonObject.getLong("tid");
		tradeManager.onTradeMemoModified(nick, tid);
	}

}
