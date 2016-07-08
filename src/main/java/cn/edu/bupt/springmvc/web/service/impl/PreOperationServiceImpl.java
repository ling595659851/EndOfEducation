package cn.edu.bupt.springmvc.web.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import cn.edu.bupt.springmvc.web.service.PreOperationService;
import cn.edu.bupt.springmvc.web.util.MD5Util;

@Service
public class PreOperationServiceImpl implements PreOperationService {

	private final String[] headers = {"https://","http://"};
	
	@Override
	public String DealHeader(String url) {
		// TODO Auto-generated method stub
		boolean flag = false;
		for(String header : headers){
			if(url.startsWith(header)){
				flag = true;
				url = url.substring(url.indexOf(header) + header.length());
				break;
			}
		}
		if(flag == true){
			return url;
		}
		return null;
	}

	@Override
	public String Transcoding(String url) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			result = MD5Util.UrlToMD5(url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
