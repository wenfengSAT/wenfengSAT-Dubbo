package com.dubbo.api.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 
 * @Description：IPUtil
 * @author [ Wenfeng.Huang ] on [2019年5月31日上午11:20:46]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class IPUtil {
	
	public static String getLocalAddr() {
		Enumeration<?> interfaces = null;

		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException var4) {
			var4.printStackTrace();
			return null;
		}

		while (interfaces.hasMoreElements()) {
			NetworkInterface ifc = (NetworkInterface) interfaces.nextElement();
			Enumeration<?> addressesOfAnInterface = ifc.getInetAddresses();

			while (addressesOfAnInterface.hasMoreElements()) {
				InetAddress address = (InetAddress) addressesOfAnInterface.nextElement();
				if (address.isSiteLocalAddress()) {
					return address.getHostAddress();
				}
			}
		}
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return null;
	}

}
