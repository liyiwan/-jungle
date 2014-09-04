package com.qganlan.webapp.pages.goods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.qganlan.service.TaobaoApiManager;
import com.taobao.api.domain.TbkItem;

public class TaobaoKe {
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String items;
	
	@Property
	private List<TbkItem> tbkItems;
	
	@Property
	private TbkItem tbkItem;
	
	@Inject
	private TaobaoApiManager taobaoApiManager;
	
	public void setUpRender() {
		if (items != null) {
			List<String> numIids = new ArrayList<String>();
			try {
				StringReader sr = new StringReader(items);
				BufferedReader br = new BufferedReader(sr);
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (StringUtils.isNumeric(line)) {
						numIids.add(line);
					} else {
						Pattern idPattern = java.util.regex.Pattern.compile("id=(\\d+)", java.util.regex.Pattern.CASE_INSENSITIVE);
						Matcher idMatcher = idPattern.matcher(line);
				    	if (idMatcher.find()) {
				    		String numIid = idMatcher.group(1);
				    		numIids.add(numIid);
				    	}
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			tbkItems = taobaoApiManager.getTbkItems(numIids);
		}
	}
}
