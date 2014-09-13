package com.qganlan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.common.TbkUtil;
import com.qganlan.dao.TradeDao;
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

	public void notifyByEmail(com.taobao.api.domain.Trade trade) {
		System.out.println("订单成交发送邮件通知 店铺:" + trade.getSellerNick() + " 买家:" + trade.getBuyerNick());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='600' cellpadding='3' border='1' border-color='#dddddd' style='border-collapse:collapse;'>");
		sb.append("<tr>");
		sb.append("<td colspan='5'>");
		sb.append("店铺：" + trade.getSellerNick());
		sb.append("&nbsp;&nbsp;订单号：" + trade.getTid());
		sb.append("<br>买家：" + trade.getBuyerNick());
		sb.append("<br>总金额：" + trade.getPayment());
		sb.append("&nbsp;&nbsp;付款时间：" + sdf.format(trade.getPayTime()));
		sb.append("<br>买家留言：" + (trade.getBuyerMessage()==null?"":trade.getBuyerMessage()));
		sb.append("<br>收件地址：" + trade.getReceiverName() + " " + (trade.getReceiverMobile()==null?"":trade.getReceiverMobile()) + " " + (trade.getReceiverPhone()==null?"":trade.getReceiverPhone()) + " " + trade.getReceiverState() + " " + trade.getReceiverCity() + " " + trade.getReceiverDistrict() + " " + trade.getReceiverAddress());
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
		
		for (Order order : trade.getOrders()) {
			String nick = null;
			String clickUrl = null;
			String outerNumIid = null;
			if (order.getOuterIid() != null && (order.getOuterIid().toUpperCase().startsWith("ID-") || order.getOuterIid().toUpperCase().startsWith("TB-") || order.getOuterIid().toUpperCase().startsWith("TM-"))) {
				String[] id = order.getOuterIid().split("-");
				if (id.length == 2) {
					outerNumIid = id[1];
					if (StringUtils.isNumeric(outerNumIid)) {
						Item item = taobaoApiManager.getTaobaoItemByNumIid(Long.valueOf(outerNumIid), taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), null);
						nick = item.getNick();
						clickUrl = TbkUtil.getClickUrl(item.getNick());
					}
				}
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
				sb.append("<a target='_blank' href='http://item.taobao.com/item.html?id=" + outerNumIid + "'>下单地址</a>");
			}
			sb.append("</td>");
			BigDecimal unitPrice = (new BigDecimal(order.getPayment())).divide(new BigDecimal(order.getNum()), 2, RoundingMode.HALF_DOWN);
			sb.append("<td align='left' valign='top'>");//价格
			sb.append(unitPrice);
			sb.append("</td>");
			sb.append("<td align='left' valign='top'>");//数量
			sb.append(order.getNum());
			sb.append("</td>");
			sb.append("<td align='left' valign='top'>");//金额
			sb.append(order.getPayment());
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("<tr>");
		sb.append("<td colspan='5'>");
		sb.append("邮费：" + (trade.getPostFee()!=null?trade.getPostFee():""));
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");
		
		String subject = "交易通知【" + trade.getSellerNick() + "】：" + trade.getBuyerNick() + " " + trade.getTid() + " " + trade.getPayment();
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

}
