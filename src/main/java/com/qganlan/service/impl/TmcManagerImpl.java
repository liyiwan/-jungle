package com.qganlan.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.qganlan.service.TaobaoApiManager;
import com.qganlan.service.TmcManager;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.response.TmcUserPermitResponse;
import com.taobao.top.link.LinkException;

@Service("tmcManager")
public class TmcManagerImpl implements TmcManager {

	@Autowired
	private TaobaoApiManager taobaoApiManager;

	@Autowired
	private MessageHandler messageHandler;

	public void setTaobaoApiManager(TaobaoApiManager taobaoApiManager) {
		this.taobaoApiManager = taobaoApiManager;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void run() {
		List<String> authorizedSellers = taobaoApiManager.getTmcAuthorizedSellers();
		for (String nick : authorizedSellers) {
			String sessionKey = taobaoApiManager.getSessionKey(nick);
			TaobaoClient taobaoClient = new DefaultTaobaoClient(TaobaoApiManager.TAOBAO_API_URL, taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), "xml");
			TmcUserPermitRequest tmcUserPermitRequest = new TmcUserPermitRequest();
			try {
				TmcUserPermitResponse tmcUserPermitResponse = taobaoClient.execute(tmcUserPermitRequest, sessionKey);
				if (tmcUserPermitResponse.isSuccess()) {
					System.out.println(nick + "-订阅消息成功！");
				}
			} catch (ApiException e) {
				System.out.println(nick + "-订阅消息失败！");;
				e.printStackTrace();
			}
		}
		TmcClient client = new TmcClient(taobaoApiManager.getAppKey(), taobaoApiManager.getAppSecret(), "default");
	    client.setMessageHandler(messageHandler);

	    while (true) {
		    try {
				client.connect();
				System.out.println("消息服务连接成功！");
				break;
			} catch (LinkException e) {
				System.out.println("消息服务连接失败！");
				e.printStackTrace();
				try {
					Thread.sleep(10000L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
	    }
	}
}
