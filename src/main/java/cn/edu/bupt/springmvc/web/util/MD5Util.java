package cn.edu.bupt.springmvc.web.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Util {
	
	/**MD5加密算法
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String UrlToMD5(String url) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }
        byte[] byteArray = url.getBytes("utf-8");
        byte[] md5Bytes = md5.digest(byteArray);
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                sb.append("0");  
            sb.append(Integer.toHexString(val));  
        }  
		return sb.toString();
	}
}
