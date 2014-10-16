package com.qganlan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qganlan.common.TbkUtil;
import com.qganlan.dao.TradeDao;
import com.qganlan.dto.GoodsSpecDTO;
import com.qganlan.dto.LogisticsInfo;
import com.qganlan.model.GApiTrade;
import com.qganlan.model.GApiTradeGoods;
import com.qganlan.model.GCfgLogistics;
import com.qganlan.model.JLogisticsCompany;
import com.qganlan.model.JRawOrder;
import com.qganlan.model.JRawTrade;
import com.qganlan.model.JTbkShopUrl;
import com.qganlan.model.Shop;
import com.qganlan.model.Trade;
import com.qganlan.model.TradeGoods;
import com.qganlan.service.EmailManager;
import com.qganlan.service.GoodsManager;
import com.qganlan.service.ShopManager;
import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TradeManager;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.LogisticsCompany;
import com.taobao.api.domain.Order;

@Service("tradeManager")
public class TradeManagerImpl implements TradeManager {

	@Autowired
	private TradeDao tradeDao;
	
	@Autowired
	private TaobaoApiManager taobaoApiManager;
	
	@Autowired
	private EmailManager emailManager;
	
	@Autowired
	private GoodsManager goodsManager;
	
	@Autowired
	private ShopManager shopManager;
	
	public void setShopManager(ShopManager shopManager) {
		this.shopManager = shopManager;
	}

	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public TaobaoApiManager getTaobaoApiManager() {
		return taobaoApiManager;
	}

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
		sb.append("<br>收件地址：" + rawTrade.getReceiverName() + "，" + (rawTrade.getReceiverMobile()==null?"":rawTrade.getReceiverMobile()) + "，" + (rawTrade.getReceiverPhone()==null?"":rawTrade.getReceiverPhone()) + "，" + rawTrade.getReceiverState() + " " + rawTrade.getReceiverCity() + " " + rawTrade.getReceiverDistrict() + " " + rawTrade.getReceiverAddress() + "，" + rawTrade.getReceiverZip());
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
		String[] toMails = { "9394908@qq.com", "1043436304@qq.com", "19647746@qq.com"};
		for (String mail : toMails) {
			if ("丹丹284272539".equals(rawTrade.getSellerNick()) && !mail.equals("9394908@qq.com")) {
				continue;
			}
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
		JRawTrade rawTrade = tradeDao.getRawTrade(trade.getTid());
		if (rawTrade != null) {
			return null;
		}
		boolean record = false;
		rawTrade = new JRawTrade();
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

			String[] id = order.getOuterIid().split("-");
			String outerNumIid = null;
			if (id.length == 2) {
				outerNumIid = id[1];
			} else if (id.length == 1) {
				outerNumIid = id[0];
			}
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

			rawOrders.add(rawOrder);
			
			if (!record) {
				if (trade.getSellerNick().equals("dwf306") || trade.getSellerNick().equals("janny0293") || trade.getSellerNick().equals("狐狐屋")) {
					record = true;
				}
			}
			
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
		HashMap<Long, com.taobao.api.domain.Trade> tradeMap = new HashMap<Long, com.taobao.api.domain.Trade>();
		for (JRawOrder rawOrder : rawOrders) {
			if (rawOrder.getPurchaseTid() != null && !rawOrder.getPurchaseTid().equals(0L)) {
				com.taobao.api.domain.Trade trade = tradeMap.get(rawOrder.getPurchaseTid());
				if (trade == null) {
					trade = taobaoApiManager.getTradeFullInfo(rawOrder.getPurchaseTid(), taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(rawOrder.getPurchaseNick()));
					tradeMap.put(rawOrder.getPurchaseTid(), trade);
				}
				if (trade != null) {
					if (!StringUtils.equals(trade.getReceiverName(), rawTrade.getReceiverName()) ||
							!StringUtils.equals(trade.getReceiverMobile(), rawTrade.getReceiverMobile()) ||
							!StringUtils.equals(trade.getReceiverState(), rawTrade.getReceiverState()) ||
							!StringUtils.equals(trade.getReceiverCity(), rawTrade.getReceiverCity())) {
						tradeDao.setRawOrderStatus(rawOrder, 2L);
						System.out.println(rawOrder.getTid() + "," + rawOrder.getOid() + " 地址核对不正确");
					}
				}
			}
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

	@Override
	public void downloadLogisticsCompanyList() {
		List<LogisticsCompany> logisticsCompanyList = taobaoApiManager.getLogisticsCompanyList();
		if (logisticsCompanyList != null) {
			System.out.println("快递公司数：" + logisticsCompanyList.size());
			for (LogisticsCompany comp : logisticsCompanyList) {
				JLogisticsCompany jcomp = new JLogisticsCompany();
				BeanCopier copier = BeanCopier.create(comp.getClass(), jcomp.getClass(), false);
				copier.copy(comp, jcomp, null);
				jcomp.setUseFlag(0);
				tradeDao.saveOrUpdate(jcomp);
			}
		}
	}

	@Override
	public List<JLogisticsCompany> getLogisticsCompanyList() {
		return tradeDao.getLogisticsCompanyList();
	}
	
	public void autoSendTrade(Long tid) {
		JRawTrade rawTrade = tradeDao.getRawTrade(tid);
		List<JRawOrder> rawOrders = tradeDao.getRawOrderList(tid);
		HashMap<Long, com.taobao.api.domain.Trade> purchaseTradeMap = new HashMap<Long, com.taobao.api.domain.Trade>();
		HashMap<Long, JRawOrder> rawOrderMap = new HashMap<Long, JRawOrder>();
		for (JRawOrder rawOrder : rawOrders) {
			rawOrderMap.put(rawOrder.getOid(), rawOrder);
			if (!rawOrder.getCurStatus().equals(11)) {
				com.taobao.api.domain.Trade purchaseTrade = null;
				String purchaseNick = rawOrder.getPurchaseNick();
				Long purchaseTid = rawOrder.getPurchaseTid();
				if (purchaseNick != null && !purchaseNick.trim().equals("") && purchaseTid != null && purchaseTid != 0) {
					purchaseTrade = purchaseTradeMap.get(purchaseTid);
					if (purchaseTrade == null) {
						purchaseTrade = taobaoApiManager.getTradeFullInfo(purchaseTid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(purchaseNick));
						if (purchaseTrade != null) {
							purchaseTradeMap.put(purchaseTid, purchaseTrade);
						}
					}
				}
				if (purchaseTrade != null) {
					List<Order> purchaseOrders = purchaseTrade.getOrders();
					String invoiceNo = purchaseOrders.get(0).getInvoiceNo();
					String companyCode = null;
					String logisticsCompany = purchaseOrders.get(0).getLogisticsCompany();
					if (logisticsCompany != null && !logisticsCompany.equals("") && invoiceNo != null && !invoiceNo.equals("")) {
						companyCode = getLogisticsCompanyCode(logisticsCompany);
						rawOrder.setInvoiceNo(invoiceNo);
						rawOrder.setCompanyCode(companyCode);
						rawOrder.setLogisticsCompany(logisticsCompany);
					}
				}
			}
		}
		List<LogisticsInfo> logisticsInfoList = new ArrayList<LogisticsInfo>();
		int count = 0;
		for (JRawOrder rawOrder : rawOrders) {
			if (!rawOrder.getCurStatus().equals(11)) {
				String outSid = rawOrder.getInvoiceNo();
				if (outSid != null && !outSid.equals("")) {
					count = count + 1;
					LogisticsInfo logisticsInfo = null;
					for (LogisticsInfo info : logisticsInfoList) {
						if (outSid.equals(info.getOutSid())) {
							logisticsInfo = info;
							break;
						}
					}
					if (logisticsInfo == null) {
						logisticsInfo = new LogisticsInfo();
						logisticsInfo.setOutSid(outSid);
						logisticsInfo.setCompanyCode(rawOrder.getCompanyCode());
						logisticsInfo.setTid(rawOrder.getTid());
						logisticsInfo.setSubTid(rawOrder.getOid()+"");
						logisticsInfoList.add(logisticsInfo);
					} else {
						logisticsInfo.setSubTid(logisticsInfo.getSubTid() + "," + rawOrder.getOid());
					}
				}
			}
		}
		if (rawOrders.size() == count && logisticsInfoList.size() == 1) {
			String invoiceNo = logisticsInfoList.get(0).getOutSid();
			String companyCode = logisticsInfoList.get(0).getCompanyCode();
			boolean isSuccess = taobaoApiManager.sendTrade(rawTrade.getTid(), null, invoiceNo, companyCode, taobaoApiManager.getSessionKey(rawTrade.getSellerNick()));
			if (isSuccess) {
				String subTid = logisticsInfoList.get(0).getSubTid();
				System.out.println("发货完成:" + rawTrade.getTid() + ":" + subTid);
				String[] aSubTid = subTid.split(",");
				for (String oid : aSubTid) {
					JRawOrder aRawOrder = rawOrderMap.get(Long.valueOf(oid));
					aRawOrder.setCurStatus(11);
				}
			}
		} else {
			for (LogisticsInfo info : logisticsInfoList) {
				boolean isSuccess = taobaoApiManager.sendTrade(rawTrade.getTid(), info.getSubTid(), info.getOutSid(), info.getCompanyCode(), taobaoApiManager.getSessionKey(rawTrade.getSellerNick()));
				if (isSuccess) {
					String subTid = info.getSubTid();				
					System.out.println("发货完成:" + rawTrade.getTid() + ":" + subTid);
					String[] aSubTid = subTid.split(",");
					for (String oid : aSubTid) {
						JRawOrder aRawOrder = rawOrderMap.get(Long.valueOf(oid));
						aRawOrder.setCurStatus(11);
					}
				}
			}
		}
		boolean allcomplete = true;
		for (JRawOrder rawOrder : rawOrders) {
			if (rawOrder.getCurStatus().equals(11)) {
				tradeDao.updateLogistics(rawOrder);
			} else if (allcomplete) {
				allcomplete = false;
			}
		}
		if (allcomplete) {
			tradeDao.completeTrade(rawTrade.getTid());
		}
	}
	
	public String getLogisticsCompanyCode(String logisticsCompany) {
		return tradeDao.getLogisticsCompanyCode(logisticsCompany);
	}

	@Override
	public void saveTradeFromTaobao(String nick, Long tid) {
		try {
			com.taobao.api.domain.Trade trade = taobaoApiManager.getTradeFullInfo(tid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(nick));
			if (trade != null) {
				recordThirdPartyTrade(trade);
				saveTaobaoTrade(trade);
			}
		} catch (Throwable t) {
	    	t.printStackTrace();
	    }
	}

	@Override
	public List<JTbkShopUrl> getTbkShopUrlList() {
		return tradeDao.getTbkShopUrlList();
	}

	@Override
	public void saveTbkShopUrl(String nick, String tbkUrl) {
		tradeDao.saveTbkShopUrl(nick, tbkUrl);
	}

	@Override
	public List<JRawTrade> getReFundRawTradeList() {
		return tradeDao.getReFundRawTradeList();
	}

	@Override
	public void refundTrade(Long tid) {
		JRawTrade rawTrade = tradeDao.getRawTrade(tid);
		try {
			com.taobao.api.domain.Trade trade = taobaoApiManager.getTradeFullInfo(tid, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), taobaoApiManager.getSessionKey(rawTrade.getSellerNick()));
			if (trade != null) {
				tradeDao.refundTrade(trade);
			}
		} catch (Throwable t) {
	    	t.printStackTrace();
	    }
	}

	@Override
	public void refundBuyerReturnGoods(Long tid, Long oid, String companyName, String sid) {
		tradeDao.refundBuyerReturnGoods(tid, oid, companyName, sid);
	}

	@Override
	public void refundCreated(Long tid, Long oid, Long refund_id) {
		tradeDao.refundCreated(tid, oid, refund_id);
	}

	@Override
	public List<JRawTrade> getPendingThirdPartyRawTradeList() {
		return tradeDao.getPendingThirdPartyRawTradeList();
	}

	@Override
	public void saveTaobaoTrade(com.taobao.api.domain.Trade trade) {
		GApiTrade apiTrade = tradeDao.getApiTrade(trade.getTid());
		if (apiTrade != null) {
			return;
		}
		Long billId = tradeDao.getNextTradeBillId();
		apiTrade = new GApiTrade();
		apiTrade.setBillId(billId);
		apiTrade.setAdr(trade.getReceiverState() + " " + trade.getReceiverCity() + " " + trade.getReceiverDistrict() + " " + trade.getReceiverAddress());
		apiTrade.setApitype(1);
		apiTrade.setBbrandSale(false);
		apiTrade.setBdelayForOrder(false);
		apiTrade.setBdelayForRemark(false);
		apiTrade.setBfx(false);
		apiTrade.setBsplit(false);
		apiTrade.setBsoldOver(false);
		apiTrade.setBwlb(false);
		apiTrade.setCity(trade.getReceiverCity());
		apiTrade.setCodserviceFee(BigDecimal.ZERO);
		apiTrade.setCurStatus(2);
		apiTrade.setCustomerId(trade.getBuyerNick());
		apiTrade.setCustomerName(trade.getReceiverName());
		apiTrade.setCustomerRemark(trade.getBuyerMessage());
		apiTrade.setDrawBack(0);
		apiTrade.setDtimeStamp(0.0);
		apiTrade.setEmail(trade.getBuyerEmail());
		apiTrade.setFavourableMoney(BigDecimal.ZERO);
		apiTrade.setFromId(1);
		apiTrade.setGetTime(new Date());
		apiTrade.setGoodsFee(new BigDecimal(trade.getPayment()));
		apiTrade.setMobile(trade.getReceiverMobile());
		apiTrade.setPayAccount(trade.getBuyerAlipayNo());
		apiTrade.setPayId(trade.getAlipayNo());
		apiTrade.setPayTime(trade.getPayTime());
		apiTrade.setPhone(trade.getReceiverMobile() + " " + trade.getReceiverPhone());
		apiTrade.setPostFee(BigDecimal.ZERO);
		apiTrade.setProvince(trade.getReceiverState());
		apiTrade.setRemark(trade.getSellerMemo());
		apiTrade.setRemind(false);
		apiTrade.setSeller(trade.getSellerNick());
		Shop shop = shopManager.getShopByNick(trade.getSellerNick());
		if (shop != null) {
			apiTrade.setShopId(shop.getShopId());
		} else {
			apiTrade.setShopId(1000L);
		}
		apiTrade.setSndStyle("快递");
		apiTrade.setSynStatus(0);
		apiTrade.setTotalMoney(new BigDecimal(trade.getPayment()));
		apiTrade.setTown(trade.getReceiverDistrict());
		apiTrade.setTradeId(0L);
		apiTrade.setTradeNo(trade.getTid()+"");
		apiTrade.setTradeStatus("买家已付款");
		apiTrade.setTradeTime(trade.getCreated());
		apiTrade.setTradeType("零售业务");
		apiTrade.setZip(trade.getReceiverZip());
		apiTrade.setCountry("");
		apiTrade.setChargeType("");
		apiTrade.setQq("");
		apiTrade.setInvoiceTitle("");
		apiTrade.setSynCause("");
		apiTrade.setSummary("");
		tradeDao.saveApiTrade(apiTrade);
		for (Order order : trade.getOrders()) {
			GApiTradeGoods apiTradeGoods = new GApiTradeGoods();
			apiTradeGoods.setBfit(false);
			apiTradeGoods.setBillId(apiTrade.getBillId());
			apiTradeGoods.setOid(order.getOid()+"");
			apiTradeGoods.setCurStatus(0);
			apiTradeGoods.setDiscountMoney(BigDecimal.ZERO);
			apiTradeGoods.setGoodsCount(order.getNum());
			apiTradeGoods.setIsOversold(false);
			apiTradeGoods.setPrice(new BigDecimal(order.getPrice()));
			apiTradeGoods.setTradeGoodsName(order.getTitle());
			GoodsSpecDTO goodsSpec = null;
			if (order.getOuterSkuId() != null && !order.getOuterSkuId().equals("")) {
				apiTradeGoods.setTradeGoodsNo(order.getOuterSkuId());
				goodsSpec = goodsManager.getGoodsSpec(order.getOuterSkuId());
			} else if (order.getOuterIid() != null && !order.getOuterIid().equals("")) {
				apiTradeGoods.setTradeGoodsNo(order.getOuterIid());
				goodsSpec = goodsManager.getGoodsSpec(order.getOuterIid());
			}
			if (goodsSpec != null) {
				apiTradeGoods.setGoodsId(goodsSpec.getGoodsId());
				apiTradeGoods.setGoodsSpec(goodsSpec.getSpecName());
				apiTradeGoods.setSpecId(goodsSpec.getSpecId());
			}
			apiTradeGoods.setTradeGoodsSpec(order.getSkuPropertiesName());
			tradeDao.saveApiTradeGoods(apiTradeGoods);
		}
	}

	@Override
	public List<GApiTrade> getApiTradeForSyncLogistics() {
		return tradeDao.getApiTradeForSyncLogistics();
	}

	@Override
	public void syncLogistics(GApiTrade apiTrade) {
		Trade trade = tradeDao.getTradeByTradeID(apiTrade.getTradeId());
		if (trade != null && trade.getTradeStatus().equals(11)) {
			String invoiceNo = trade.getPostId();
			Long logisticsId = trade.getLogisticId();
			GCfgLogistics cfgLogistics = tradeDao.getGCfgLogistics(logisticsId);
			String companyCode = cfgLogistics.getTaobaoCompanyCode();
			boolean isSuccess = taobaoApiManager.sendTrade(Long.valueOf(apiTrade.getTradeNo()), null, invoiceNo, companyCode, taobaoApiManager.getSessionKey(apiTrade.getSeller()));
			if (isSuccess) {
				System.out.println("发货完成: " + apiTrade.getSeller() + " " + apiTrade.getTradeNo() + " " + apiTrade.getCustomerId());
				tradeDao.updateLogisticsSuccess(apiTrade);
			} else {
				System.out.println("发货失败: " + apiTrade.getSeller() + " " + apiTrade.getTradeNo() + " " + apiTrade.getCustomerId());
				tradeDao.updateLogisticsFail(apiTrade);
			}
		}
	}

}
