package com.qganlan.service.impl;

import org.apache.log4j.Logger;
import org.apache.tapestry5.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.model.JRawTrade;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TradeManager;
import com.taobao.api.domain.Refund;
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
            } else if ("taobao_refund_RefundCreated".equals(message.getTopic())) {
            	onRefundCreated(message);
            } else if ("taobao_refund_RefundBuyerReturnGoods".equals(message.getTopic())) {
            	onRefundBuyerReturnGoods(message);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
            status.fail();
        }  
	}
	
	private void onRefundBuyerReturnGoods(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
		String buyer_nick = jsonObject.getString("buyer_nick");
		Long oid = jsonObject.getLong("oid");
		String refund_fee = jsonObject.getString("refund_fee");
		Long refund_id = jsonObject.getLong("refund_id");
		String seller_nick = jsonObject.getString("seller_nick");
		Long tid = jsonObject.getLong("tid");
		try {
			Refund refund = taobaoApiManager.getRefund(refund_id, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(seller_nick));
			if (refund != null) {
				tradeManager.refundBuyerReturnGoods(tid, oid, refund.getCompanyName(), refund.getSid());
			}
		} catch (Throwable t) {
	    	logger.error("处理消息异常", t);
	    }
	}

	private void onRefundCreated(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
		String buyer_nick = jsonObject.getString("buyer_nick");
		Long oid = jsonObject.getLong("oid");
		String refund_fee = jsonObject.getString("refund_fee");
		Long refund_id = jsonObject.getLong("refund_id");
		String seller_nick = jsonObject.getString("seller_nick");
		Long tid = jsonObject.getLong("tid");
		tradeManager.refundCreated(tid, oid, refund_id);
	}

	private void onTradeBuyerPay(Message message) {
		JSONObject jsonObject = new JSONObject(message.getContent());
		String sellerNick = jsonObject.getString("seller_nick");
		Long tid = jsonObject.getLong("tid");
		try {
			Trade trade = taobaoApiManager.getTradeFullInfo(tid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(sellerNick));
			if (trade != null) {
				System.out.println("有订单付款 tid=" + trade.getTid() + " sellerNick=" + trade.getSellerNick() + " buyerNick=" + trade.getBuyerNick());
				JRawTrade rawTrade = tradeManager.recordThirdPartyTrade(trade);
				if (rawTrade != null) {
					tradeManager.notifyByEmail(rawTrade);
				}
				if (trade.getSellerNick().equals("小脚丫商城") || trade.getSellerNick().equals("lingxige")) {
					tradeManager.saveTaobaoTrade(trade);
				}
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
