package com.zhaodi.util;

import java.security.MessageDigest;

public class MD5Util {
	    public static String encode(String sourceStr) {
			String result = "";
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(sourceStr.getBytes("UTF-8"));
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				result = buf.toString();
			} catch (Exception e) {
			}
			return result;
	    }
	  
}
