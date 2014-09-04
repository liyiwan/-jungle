package com.qganlan.common;

public class TbkUtil {
	
	public static final String[][] clickUrls = {{"乖乖杨梅", "http://s.click.taobao.com/t?e=m%3D2%26s%3DnwtwIMVI0jQcQipKwQzePDAVflQIoZepLKpWJ%2Bin0XJRAdhuF14FMaB%2F7TzCAWhC5x%2BIUlGKNpX8VAM6jS9CnywI3fRdpdmTWRuY2RuUsBis7eqx%2FieWMeiPdb5GJTeL"}};
	
	public static String getClickUrl(String nick) {
		for (int x = 0; x < clickUrls.length; x++) {
			if (clickUrls[x][0].equals(nick)) {
				return clickUrls[x][1];
			}
		}
		return "";
	}
	
}
