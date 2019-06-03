package com.dubbo.api.service.util.wxpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

/**
 * 
 * @Description： 微信支付工具类
 * @author [ Wenfeng.Huang ] on [2019年5月31日上午11:07:04]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class WeixinPayUtil {
	
	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 请求接口，已xml参数方式。
	 * 
	 * @param urlStr
	 *            接口url地址
	 * @param xml
	 *            xml格式的参数
	 * @return 接口返回的信息 异常时返回空字符串"".
	 */
	public static String post4xml(String urlStr, String xml) {

		try {
			URL url = new URL(urlStr);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestMethod("POST");
			if (xml != null) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(xml.getBytes());
				outputStream.close();
			}
			return getResponsMessage(connection);
		} catch (Exception e) {
			return "";
		}

	}


	private static String getResponsMessage(HttpsURLConnection connection) {
		InputStream inputStream;
		try {
			inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}
