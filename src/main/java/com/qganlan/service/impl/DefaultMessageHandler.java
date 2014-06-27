package com.qganlan.service.impl;

import org.apache.log4j.Logger;
import org.apache.tapestry5.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.service.TradeManager;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;

@Service("messageHandler")
public class DefaultMessageHandler implements MessageHandler {

	private static final Logger logger = Logger.getLogger(DefaultMessageHandler.class);
	
	@Autowired
	private TradeManager tradeManager;

	public void setTradeManager(TradeManager tradeManager) {
		this.tradeManager = tradeManager;
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
		
//		JSONObject jsonObject = new JSONObject(message.getContent());
//		//String buyerNick = jsonObject.getString("buyer_nick");
//		String sellerNick = jsonObject.getString("seller_nick");
//		Long tid = jsonObject.getLong("tid");
//		try {
//			Trade trade = taobaoService.getTradeFullInfo(sellerNick, tid);
//			logger.info("trade="+trade);
//			if (trade != null) {
//				tradeService.emailNotify(trade);
//			}
//		} catch (Throwable t) {
//	    	logger.error("处理消息异常", t);
//	    }
	}

	private void onItemAdd(Message message) {
//		JSONObject jsonObject = new JSONObject(message.getContent());
//    	Long numIid = jsonObject.getLong("num_iid");
//    	String nick = jsonObject.getString("nick");
//		Item item = null;
//	    try {
//	    	item = taobaoService.getItemFromTaobao(numIid, nick);
//	    } catch (Throwable t) {
//	    	logger.error("处理消息异常", t);
//	    }
//    	if (item != null) {
//    		try {
//    			goodsService.resolveApiSysMatch(item);
//    		} catch (Throwable t) {
//    			logger.error("RESOLVE API SYS MATCH ERROR.", t);
//    			logger.error(message.getTopic());
//                logger.error(message.getContent());
//    		}
//			try {
//				goodsService.saveTaobaoItem(item);
//			} catch (Throwable t) {
//				logger.error("SAVE TAOBAO ITEM ERROR.", t);
//				logger.error(message.getTopic());
//                logger.error(message.getContent());
//			}
//    	}
	}
	
	private void onItemDelete(Message message) {
//		JSONObject jsonObject = new JSONObject(message.getContent());
//    	Long numIid = jsonObject.getLong("num_iid");
//		try {
//			goodsService.deleteApiSysMatch(numIid);
//		} catch (Throwable t) {
//			logger.error("RESOLVE API SYS MATCH ERROR.", t);
//			logger.error(message.getTopic());
//            logger.error(message.getContent());
//		}
//
//		try {
//			goodsService.deleteTaobaoItem(numIid);
//		} catch (Throwable t) {
//			logger.error("SAVE TAOBAO ITEM ERROR.", t);
//			logger.error(message.getTopic());
//            logger.error(message.getContent());
//		}
	}

	private void onItemUpdate(Message message) {
//		
//		JSONObject jsonObject = new JSONObject(message.getContent());
//    	Long numIid = jsonObject.getLong("num_iid");
//    	String nick = jsonObject.getString("nick");
//    	String changedFields = jsonObject.getString("changed_fields");
//		
//    	Item item = null;
//    	if (nick != null && !nick.trim().equals("")) {
//		    try {
//		    	item = taobaoService.getItemFromTaobao(numIid, nick);
//		    } catch (Throwable t) {
//		    	logger.error("处理消息异常", t);
//		    }
//    	}
//
//	    if (item == null) {
//	    	return;
//	    }
//
//    	if (changedFields.contains("sku")) {
//    		logger.info("宝贝变更（SKU） " + item.getNumIid() + " " + item.getOuterId() + " " + item.getTitle());
//    		Long skuId = jsonObject.getLong("sku_id");
//    		Sku sku = taobaoService.getTaobaoItemSku(nick, numIid, skuId);
//    		if (sku != null && sku.getStatus().equals("normal")) {
//    			logger.info("SKU变更 重新匹配 " + sku.getSkuId() + " " + sku.getPropertiesName() + " " + sku.getOuterId());
//    			goodsService.resolveApiSysMatch(item, sku);
//    		} else {
//    			logger.info("SKU删除，删除匹配。" + sku.getSkuId() + " " + sku.getPropertiesName() + " " + sku.getOuterId());
//    			goodsService.deleteApiSysMatch(numIid.toString(), skuId.toString());
//    		}
//    	} else {
//    		logger.info("宝贝变更（非SKU） 全部重新匹配 " + item.getNumIid() + " " + item.getOuterId() + " " + item.getTitle());
//    		goodsService.resolveApiSysMatch(item);
//    	}
	}
	
	private void onItemUpshelf(Message message) {
//		JSONObject jsonObject = new JSONObject(message.getContent());
//    	Long numIid = jsonObject.getLong("num_iid");
//    	String nick = jsonObject.getString("nick");
//		Item item = null;
//	    try {
//	    	item = taobaoService.getItemFromTaobao(numIid, nick);
//	    } catch (Throwable t) {
//	    	logger.error("处理消息异常", t);
//	    }
//	    if (item != null) {
//    		try {
//    			goodsService.resolveApiSysMatch(item);
//    		} catch (Throwable t) {
//    			logger.error("RESOLVE API SYS MATCH ERROR.", t);
//    			logger.error(message.getTopic());
//                logger.error(message.getContent());
//    		}
//			try {
//				goodsService.saveTaobaoItem(item);
//			} catch (Throwable t) {
//				logger.error("SAVE TAOBAO ITEM ERROR.", t);
//				logger.error(message.getTopic());
//                logger.error(message.getContent());
//			}
//    	}
	}
	
	private void onTradeMemoModified(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
    	String nick = jsonObject.getString("seller_nick");
    	Long tid = jsonObject.getLong("tid");
		tradeManager.onTradeMemoModified(nick, tid);
	}

}
