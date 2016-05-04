package org.goffee.cxf.util;

import java.util.List;

public class Utils {

	public static String checksum(List<?> params) {
		
		StringBuffer sb = new StringBuffer();
		
		for (Object param : params) {
			sb.append(param.toString());
		}
		
		String checksum = "CHECKSUM" + sb.toString();
		System.out.println("In Util:" + checksum);
		return checksum;
	}
}
