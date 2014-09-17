package com.qganlan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.common.TbkUtil;
import com.qganlan.dao.TradeDao;
import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;
import com.qganlan.service.EmailManager;
import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TradeManager;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;
import org.apache.commons.lang3.StringUtils;

@Service("tradeManager")
public class TradeManagerImpl implements TradeManager {

	@Autowired
	private TradeDao tradeDao;
	
	@Autowired
	private TaobaoApiManager taobaoApiManager;
	
	@Autowired
	private EmailManager emailManager;
	
	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	public TradeDao getTradeDao() {
		return tradeDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public List<Trade> getSndTradeList(Date startDate, Date endDate) {
		return tradeDao.getSndTradeList(startDate, endDate);
	}

	public List<TradeGoods> getTradeGoodsList(Long tradeId) {
		return tradeDao.getTradeGoodsList(tradeId);
	}

	public Trade getTrade(Long tradeId) {
		return tradeDao.get(tradeId);
	}

	public void onTradeMemoModified(String nick, Long tid) {
		com.taobao.api.domain.Trade taobaoTrade = taobaoApiManager.getTaobaoTrade(tid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(),taobaoApiManager.getSessionKey(nick));
		if (taobaoTrade != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Trade> listTrade = tradeDao.getTradeByTradeNO2("%" + tid + "%");
			for (Trade trade : listTrade) {
				String remark = taobaoTrade.getSellerMemo() + "(SYS " + sdf.format(new Date()) + ")" + "\n" + trade.getAppendRemark();
				if (remark.length() > 1000) {
					remark = remark.substring(0,1000);
				}
				tradeDao.updateRemark(trade.getTradeId(), remark);
				System.out.println("TradeStatus=" + trade.getTradeStatus());
				if (trade.getTradeStatus() == 5) {
					System.out.println("发货中，冻结订单。");
					tradeDao.freezeTrade(trade.getTradeId(), "修改备注");
				}
			}
		}
	}

	public void notifyByEmail(JRawTrade rawTrade) {
		System.out.println("订单成交发送邮件通知 店铺:" + rawTrade.getSellerNick() + " 买家:" + rawTrade.getBuyerNick());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='600' cellpadding='3' border='1' border-color='#dddddd' style='border-collapse:collapse;'>");
		sb.append("<tr>");
		sb.append("<td colspan='5'>");
		sb.append("店铺：" + rawTrade.getSellerNick());
		sb.append("&nbsp;&nbsp;订单号：" + rawTrade.getTid());
		sb.append("<br>买家：" + rawTrade.getBuyerNick());
		sb.append("<br>总金额：" + rawTrade.getPayment());
		sb.append("&nbsp;&nbsp;付款时间：" + sdf.format(rawTrade.getPayTime()));
		sb.append("<br>买家留言：" + (rawTrade.getBuyerMessage()==null?"":rawTrade.getBuyerMessage()));
		sb.append("<br>收件地址：" + rawTrade.getReceiverName() + "," + (rawTrade.getReceiverMobile()==null?"":rawTrade.getReceiverMobile()) + "," + (rawTrade.getReceiverPhone()==null?"":rawTrade.getReceiverPhone()) + "," + rawTrade.getReceiverState() + " " + rawTrade.getReceiverCity() + " " + rawTrade.getReceiverDistrict() + " " + rawTrade.getReceiverAddress() + "," + rawTrade.getReceiverZip());
		sb.append("</td>");
		sb.append("</tr>");
		
		sb.append("<tr>");
		sb.append("<td nowrap align='left' valign='top'>");//图片
		sb.append("图片");
		sb.append("</td>");
		sb.append("<td nowrap align='left' valign='top'>");//标题
		sb.append("宝贝");
		sb.append("</td>");
		sb.append("<td nowrap align='left' valign='top'>");//价格
		sb.append("单价");
		sb.append("</td>");
		sb.append("<td nowrap align='left' valign='top'>");//数量
		sb.append("数量");
		sb.append("</td>");
		sb.append("<td nowrap align='left' valign='top'>");//金额
		sb.append("金额");
		sb.append("</td>");
		sb.append("</tr>");
		
		boolean single = false;
		if (rawTrade.getOrders().size() == 1) {
			single = true;
		}
		for (JRawOrder order : rawTrade.getOrders()) {
			String nick = order.getProviderNick();
			String clickUrl = null;
			if (nick != null) {
				clickUrl = TbkUtil.getClickUrl(order.getProviderNick());
			}
			
			sb.append("<tr>");
			sb.append("<td align='left' valign='top'>");//图片
			sb.append("<img width='100' src='" + order.getPicPath() + "'>");
			sb.append("</td>");
			sb.append("<td align='left' valign='top'>");//标题
			sb.append("<a target='_blank' href='http://item.taobao.com/item.html?id=" + order.getNumIid() + "'>" + order.getTitle() + "</a>");
			sb.append("<br>" + order.getOuterIid());
			sb.append("<br>" + order.getSkuPropertiesName());
			if (nick != null && clickUrl != null) {
				sb.append("<br>");
				sb.append("<a target='_blank' href='" + clickUrl + "'>" + nick + "</a>");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append("<a target='_blank' href='http://item.taobao.com/item.html?id=" + order.getProviderNumIid() + "'>下单地址</a>");
			}
			sb.append("</td>");
			BigDecimal unitPrice;
			if (single) {
				BigDecimal payment = new BigDecimal(order.getPayment()!=null?order.getPayment():"0").subtract(new BigDecimal(rawTrade.getPostFee()!=null?rawTrade.getPostFee():"0"));
				unitPrice = payment.divide(new BigDecimal(order.getNum()), 2, RoundingMode.HALF_DOWN);
			} else {
				unitPrice = (new BigDecimal(order.getPayment())).divide(new BigDecimal(order.getNum()), 2, RoundingMode.HALF_DOWN);
			}
			sb.append("<td align='left' valign='top'>");//价格
			sb.append(unitPrice);
			sb.append("</td>");
			sb.append("<td align='left' valign='top'>");//数量
			sb.append(order.getNum());
			sb.append("</td>");
			sb.append("<td align='left' valign='top'>");//金额
			if (single) {
				sb.append(new BigDecimal(order.getPayment()!=null?order.getPayment():"0").subtract(new BigDecimal(rawTrade.getPostFee()!=null?rawTrade.getPostFee():"0")));
			} else {
				sb.append(order.getPayment());
			}
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("<tr>");
		sb.append("<td colspan='5'>");
		sb.append("邮费：" + (rawTrade.getPostFee()!=null?rawTrade.getPostFee():"0"));
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");
		
		String subject = "交易通知【" + rawTrade.getSellerNick() + "】：" + rawTrade.getBuyerNick() + " " + rawTrade.getTid() + " " + rawTrade.getPayment();
		String content = sb.toString();
		String[] toMails = { "9394908@qq.com", "1043436304@qq.com"};
		for (String mail : toMails) {
			try {
				String[] to = new String[1];
				to[0] = mail;
				emailManager.sentHtml(subject, content, to);
				System.out.println("邮件发送成功，发送给" + mail);
				Thread.sleep(15000L);
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("邮件发送失败");
			}
		}
		
	}
	
	public JRawTrade  recordThirdPartyTrade(com.taobao.api.domain.Trade trade) {
		boolean record = false;
		JRawTrade rawTrade = new JRawTrade();
		BeanCopier tradeCopier = BeanCopier.create(trade.getClass(), rawTrade.getClass(), false);
		tradeCopier.copy(trade, rawTrade, null);
		rawTrade.setCurStatus(0);
		
		HashMap<String, Item> itemMap = new HashMap<String, Item>();
		List<Order> orders = trade.getOrders();
		List<JRawOrder> rawOrders = new ArrayList<JRawOrder>();
		for (Order order : orders) {
			JRawOrder rawOrder = new JRawOrder();
			BeanCopier orderCopier = BeanCopier.create(order.getClass(), rawOrder.getClass(), false);
			orderCopier.copy(order, rawOrder, null);
			rawOrder.setTid(rawTrade.getTid());
			rawOrder.setCurStatus(0);
			
			if (order.getOuterIid() != null && (order.getOuterIid().toUpperCase().startsWith("ID-") || order.getOuterIid().toUpperCase().startsWith("TB-") || order.getOuterIid().toUpperCase().startsWith("TM-"))) {
				String[] id = order.getOuterIid().split("-");
				if (id.length == 2) {
					String outerNumIid = id[1];
					if (StringUtils.isNumeric(outerNumIid)) {
						Item item = itemMap.get(outerNumIid);
						if (item == null) { 
							item = taobaoApiManager.getTaobaoItemByNumIid(Long.valueOf(outerNumIid), taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), null);
							itemMap.put(outerNumIid, item);
						}
						if (item != null) {
							String nick = item.getNick();
							rawOrder.setProviderNick(nick);
							rawOrder.setProviderNumIid(item.getNumIid());
						}
					}
				}
			}
			
			rawOrders.add(rawOrder);
			if (!record && order.getOuterIid() != null) {
				String outerIid = order.getOuterIid().toUpperCase();
				if (outerIid.startsWith("ID-") || outerIid.startsWith("TB-") || outerIid.startsWith("TM-")) {
					record = true;
				}
			}
			if (!record && order.getOuterSkuId() != null) {
				String outerSkuIid = order.getOuterSkuId().toUpperCase();
				if (outerSkuIid.startsWith("ID-") || outerSkuIid.startsWith("TB-") || outerSkuIid.startsWith("TM-")) {
					record = true;
				}
			}
		}
		rawTrade.setOrders(rawOrders);
		if (record) {
			System.out.println("记录委外发货订单，订单号" + rawTrade.getTid() + "，买家" + rawTrade.getBuyerNick() + "，卖家" + rawTrade.getSellerNick());
			tradeDao.saveRawTrade(rawTrade);
			for (JRawOrder rawOrder : rawOrders) {
				tradeDao.saveRawOrder(rawOrder);
			}
		}
		return rawTrade;
	}
	
	public List<JRawTrade> getInProgressThirdPartyRawTradeList() {
		return tradeDao.getInProgressThirdPartyRawTradeList();
	}
	
	public List<JRawTrade> getCompletedThirdPartyRawTradeList() {
		return tradeDao.getRawTradeList(11);
	}

	@Override
	public List<JRawOrder> getRawOrderList(Long tid) {
		return tradeDao.getRawOrderList(tid);
		
	}

	@Override
	public String getTbkShopUrl(String providerNick) {
		return tradeDao.getTbkShopUrl(providerNick);
	}

	@Override
	public JRawTrade getRawTrade(Long tid) {
		return tradeDao.getRawTrade(tid);
	}

	@Override
	public void fillTrade(JRawTrade rawTrade, List<JRawOrder> rawOrders) {
		for (JRawOrder rawOrder : rawOrders) {
			tradeDao.fillOrder(rawOrder);
		}
	}

	@Override
	public void completeTrade(Long tid) {
		tradeDao.completeTrade(tid);
	}

	@Override
	public List<JRawTrade> getRecentRawTradeList() {
		return tradeDao.getRecentRawTradeList();
	}

}
