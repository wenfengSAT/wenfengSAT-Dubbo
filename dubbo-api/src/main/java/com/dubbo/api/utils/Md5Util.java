package com.dubbo.api.utils;

import java.security.MessageDigest;
/**
 * 
 * @Description： MD5加密工具类
 * @author [ Wenfeng.Huang ] on [2019年5月31日上午11:47:25]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class Md5Util {
	
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };


	public final static String md5(String s) {
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
