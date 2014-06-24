package com.qganlan.common;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class PropertyValueAlias {
	
	private Map<String,String> aliasMap;

	public PropertyValueAlias(String propertyAlias) {
		aliasMap = new HashMap<String,String>();
		String[] paArray = propertyAlias.split(";");
        for (String pa : paArray) {
            String[] s = pa.split(":");
            if (s.length  < 2) {
                continue;
            }
            aliasMap.put(s[0] + ":" + s[1], s[2]);
        }
	}

	public String getAlias(String propertyValue) {
		return aliasMap.get(propertyValue);
	}

	public String translate(String propertiesName) {
		String rtnValue = "";
		StringTokenizer st = new StringTokenizer(propertiesName, ";");
		while(st.hasMoreTokens()) {
			String  token = st.nextToken();
			String[] a = token.split(":");
			String pid = a[0];
			String vid = a[1];
			String vname = a[3];
			String value = aliasMap.get(pid + ":" + vid);
			if (value == null) {
				rtnValue = rtnValue + vname;
			} else {
				rtnValue = rtnValue + value;
			}
			if (st.hasMoreTokens()) {
				rtnValue = rtnValue + ";";
			}
		}
		return rtnValue;
	}
}
