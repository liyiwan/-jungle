package com.qganlan.service.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.qganlan.service.EmailManager;
import com.qganlan.service.RzcshopManager;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.request.ItemsCustomGetRequest;
import com.taobao.api.request.SkusCustomGetRequest;
import com.taobao.api.response.ItemQuantityUpdateResponse;
import com.taobao.api.response.ItemsCustomGetResponse;
import com.taobao.api.response.SkusCustomGetResponse;

@Service("rzcshopManager")
public class RzcshopManagerImpl implements RzcshopManager {
	
	private static String TAOBAO_API_URL = "http://gw.api.taobao.com/router/rest";
	
	@Autowired
	private EmailManager emailManager;
	
	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	public void execute() {
		try {
			String propertiesFile = "D:\\jungle\\conf\\rzcshop.properties";
			Properties props = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(propertiesFile));
			props.load(in);
			in.close();

			String cookie = props.getProperty("cookie");
			String appKey = props.getProperty("appkey");
			String appSecret = props.getProperty("appsecret");
			String sessionKey = props.getProperty("sessionkey");
			String sDate = props.getProperty("date");
			String dateFormat = props.getProperty("dateformat");
			
			SimpleDateFormat year = new SimpleDateFormat("yyyy");
			String currentyear = year.format(new Date());
			
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			
			Date fromDate = sdf.parse(sDate);
			
			final WebClient webClient = new WebClient();
			CookieManager cookieManager = webClient.getCookieManager();
			List<Cookie> cookies = parseCookies("www.rzcshop.com", cookie);
			for (Cookie aCookie : cookies) {
				System.out.println(aCookie.getName() + "=" + aCookie.getValue());
				cookieManager.addCookie(aCookie);
			}
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter rzcshopLogWriter = new PrintWriter(stringWriter);
			
			String startDatetime = sdf.format(new Date());
			System.out.println("运行开始时间 " + startDatetime);
			rzcshopLogWriter.println("运行开始时间 " + startDatetime);
			
			// 下架通知
			System.out.println("下架通知");
			rzcshopLogWriter.println("下架通知");
			HtmlPage page = webClient.getPage("http://www.rzcshop.com/member-1-2-updatelist.html");
			boolean done = false;
			while (!done) {
				HtmlTable dataTable = page.getFirstByXPath("//table[@class=\"liststyle tongbu\"]");
				HtmlTableBody tableBody = dataTable.getFirstByXPath("tbody");
				List<HtmlTableRow> rows =  tableBody.getHtmlElementsByTagName("tr");
				for (HtmlTableRow row: rows) {
					String downDate = row.getCell(0).getTextContent();
					Date rowDate = sdf.parse(currentyear + "-" + downDate);
					if (rowDate.before(fromDate)) {
						done = true;
						break;
					}
					
		            String goodsNo = row.getCell(4).getTextContent();
		            String goodsName = row.getCell(5).getTextContent();
		            
		            List<Sku> skus = taobaoSkusCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            if (skus != null && skus.size() > 0) {
		            	for (Sku sku : skus) {
		            		if (taobaoItemQuantityUpdate(sku.getNumIid(), sku.getSkuId(), 0L, appKey, appSecret, sessionKey)) {
		            			System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
			            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
		            		} else {
		            			System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
			            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
		            		}
		            	}
		            } else {
		            	List<Item> items = taobaoItemsCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            	if (items != null && items.size() > 0) {
		            		for (Item item : items) {
		            			if (taobaoItemQuantityUpdate(item.getNumIid(), null, 0L, appKey, appSecret, sessionKey)) {
		            				System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
				            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
		            			} else {
		            				System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
				            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
		            			}
		            		}
		            	} else {
		            		System.out.println(downDate + " " + goodsNo + " " + goodsName + " 没有在店里出售。");
		            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 没有在店里出售。");
		            	}
		            }
		            
		        }

				// 下一页
				HtmlAnchor nextPage = page.getFirstByXPath("//a[@class=\"next\"]");
				page = nextPage.click();
			}
			
			// 缺货通知
			rzcshopLogWriter.println("缺货通知");
			page = webClient.getPage("http://www.rzcshop.com/member-1-4-updatelist.html");
			done = false;
			while (!done) {
				HtmlTable dataTable = page.getFirstByXPath("//table[@class=\"liststyle tongbu\"]");
				HtmlTableBody tableBody = dataTable.getFirstByXPath("tbody");
				List<HtmlTableRow> rows =  tableBody.getHtmlElementsByTagName("tr");
				for (HtmlTableRow row: rows) {
					String downDate = row.getCell(0).getTextContent();
					Date rowDate = sdf.parse(currentyear + "-" + downDate);
					if (rowDate.before(fromDate)) {
						done = true;
						break;
					}
					
		            String goodsNo = row.getCell(4).getTextContent();
		            String goodsName = row.getCell(5).getTextContent();
		            
		            List<Sku> skus = taobaoSkusCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            if (skus != null && skus.size() > 0) {
		            	for (Sku sku : skus) {
		            		if (taobaoItemQuantityUpdate(sku.getNumIid(), sku.getSkuId(), 0L, appKey, appSecret, sessionKey)) {
		            			System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
			            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
		            		} else {
		            			System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
			            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
		            		}
		            	}
		            } else {
		            	List<Item> items = taobaoItemsCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            	if (items != null && items.size() > 0) {
		            		for (Item item : items) {
		            			if (taobaoItemQuantityUpdate(item.getNumIid(), null, 0L, appKey, appSecret, sessionKey)) {
		            				System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
				            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架成功。");
		            			} else {
		            				System.out.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
				            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 下架失败。");
		            			}
		            		}
		            	} else {
		            		System.out.println(downDate + " " + goodsNo + " " + goodsName + " 没有在店里出售。");
		            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 没有在店里出售。");
		            	}
		            }
		            
		        }
				
				// 下一页
				HtmlAnchor nextPage = page.getFirstByXPath("//a[@class=\"next\"]");
				page = nextPage.click();
			}
			
			// 补货通知
			System.out.println("补货通知");
			rzcshopLogWriter.println("补货通知");
			page = webClient.getPage("http://www.rzcshop.com/member-1-3-updatelist.html");
			done = false;
			while (!done) {
				HtmlTable dataTable = page.getFirstByXPath("//table[@class=\"liststyle tongbu\"]");
				HtmlTableBody tableBody = dataTable.getFirstByXPath("tbody");
				List<HtmlTableRow> rows =  tableBody.getHtmlElementsByTagName("tr");
				for (HtmlTableRow row: rows) {
					String downDate = row.getCell(0).getTextContent();
					Date rowDate = sdf.parse(currentyear + "-" + downDate);
					if (rowDate.before(fromDate)) {
						done = true;
						break;
					}
					
		            String goodsNo = row.getCell(4).getTextContent();
		            String goodsName = row.getCell(5).getTextContent();
		            String stock = row.getCell(6).getTextContent();
		            Long quantity = Long.valueOf(stock);
		            if (quantity == null) {
		            	quantity = 0L;
		            }
		            
		            List<Sku> skus = taobaoSkusCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            if (skus != null && skus.size() > 0) {
		            	for (Sku sku : skus) {
		            		if (taobaoItemQuantityUpdate(sku.getNumIid(), sku.getSkuId(), quantity, appKey, appSecret, sessionKey)) {
		            			System.out.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更新成功。");
			            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更新成功。");
		            		} else {
		            			System.out.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更新失败。");
			            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更失败。");
		            		}
		            	}
		            } else {
		            	List<Item> items = taobaoItemsCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            	if (items != null && items.size() > 0) {
		            		for (Item item : items) {
		            			if (taobaoItemQuantityUpdate(item.getNumIid(), null, quantity, appKey, appSecret, sessionKey)) {
		            				System.out.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更新成功。");
				            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更新成功。");
		            			} else {
		            				System.out.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更新失败。");
				            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " " + quantity + " 库存更失败。");
		            			}
		            		}
		            	} else {
		            		System.out.println(downDate + " " + goodsNo + " " + goodsName + " 没有在店里出售。");
		            		rzcshopLogWriter.println(downDate + " " + goodsNo + " " + goodsName + " 没有在店里出售。");
		            	}
		            }
		            
		        }
				
				// 下一页
				HtmlAnchor nextPage = page.getFirstByXPath("//a[@class=\"next\"]");
				page = nextPage.click();
			}
			
			// 新货通知
			rzcshopLogWriter.println("新货通知");
			page = webClient.getPage("http://www.rzcshop.com/member-1-1-updatelist.html");
			done = false;
			while (!done) {
				HtmlTable dataTable = page.getFirstByXPath("//table[@class=\"liststyle tongbu\"]");
				HtmlTableBody tableBody = dataTable.getFirstByXPath("tbody");
				List<HtmlTableRow> rows =  tableBody.getHtmlElementsByTagName("tr");
				for (HtmlTableRow row: rows) {
					String upDate = row.getCell(0).getTextContent();
					Date rowDate = sdf.parse(currentyear + "-" + upDate);
					if (rowDate.before(fromDate)) {
						done = true;
						break;
					}
					String goodsNo = row.getCell(4).getTextContent();
		            String goodsName = row.getCell(5).getTextContent();
		            List<Sku> skus = taobaoSkusCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            if (skus != null && skus.size() > 0) {
		            	// 已经在架出售
		            } else {
		            	List<Item> items = taobaoItemsCustomGet(goodsNo, appKey, appSecret, sessionKey);
		            	if (items != null && items.size() > 0) {
		            		// 已经在架出售
		            	} else {
		            		System.out.println(upDate + " " + goodsNo + " " + goodsName + " 新货上架。");
		            		rzcshopLogWriter.println(upDate + " " + goodsNo + " " + goodsName + " 新货上架。");
		            	}
		            }
				}
				
				// 下一页
				HtmlAnchor nextPage = page.getFirstByXPath("//a[@class=\"next\"]");
				page = nextPage.click();
			}
			
			rzcshopLogWriter.println("运行完成时间 " + sdf.format(new Date()));
			
			String[] receivers = new String[] {"1106628276@qq.com"};
			emailManager.sentText("RZCSHOP库存更新 " + sdf.format(new Date()), stringWriter.toString(), receivers);
			OutputStream outputStream = new FileOutputStream(propertiesFile);
			props.setProperty("date", startDatetime);
			props.store(outputStream, sdf.format(new Date()));
			outputStream.close();
			
			rzcshopLogWriter.close();
			
			webClient.closeAllWindows();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private static List<Cookie> parseCookies(String domain, String cookie) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		StringTokenizer st = new StringTokenizer(cookie, ";");
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			String[] c = token.split("=");
			Cookie aCookie = new Cookie(domain, c[0].trim(), c[1].trim());
			cookies.add(aCookie);
		}
		return cookies;
	}
	
	private List<Sku> taobaoSkusCustomGet(String outerId, String appkey, String appsecret, String sessionkey) {
		TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		SkusCustomGetRequest req = new SkusCustomGetRequest();
		req.setOuterId(outerId);
		req.setFields("num_iid,sku_id,properties");
		try {
			SkusCustomGetResponse response = client.execute(req , sessionkey);
			if (!response.isSuccess()) {
				System.out.println("ERROR:" + response.getErrorCode() + " " + response.getMsg());
			} 
			return response.getSkus();
		} catch (ApiException e) {
			e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		}
		return null;
	}
	
	private List<Item> taobaoItemsCustomGet(String outerId, String appkey, String appsecret, String sessionkey) {
		TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		ItemsCustomGetRequest req = new ItemsCustomGetRequest();
		req.setOuterId(outerId);
		req.setFields("num_iid,sku,item_img,prop_img");
		try {
			ItemsCustomGetResponse response = client.execute(req , sessionkey);
			if (!response.isSuccess()) {
				System.out.println("ERROR:" + response.getErrorCode() + " " + response.getMsg());
			} 
			return response.getItems();
		} catch (ApiException e) {
			e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		}
		return null;
	}
	
	private boolean taobaoItemQuantityUpdate(Long numIid, Long skuId, Long quanlity, String appkey, String appsecret, String sessionkey) {
		TaobaoClient client = new DefaultTaobaoClient(TAOBAO_API_URL, appkey, appsecret);
		ItemQuantityUpdateRequest req = new ItemQuantityUpdateRequest();
		req.setNumIid(numIid);
		if (skuId != null) {
			req.setSkuId(skuId);
		}
		req.setQuantity(quanlity);
		req.setType(1L);
		try {
			ItemQuantityUpdateResponse response = client.execute(req , sessionkey);
			return response.isSuccess();
		} catch (ApiException e) {
			e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		}
		return false;
	}
}
