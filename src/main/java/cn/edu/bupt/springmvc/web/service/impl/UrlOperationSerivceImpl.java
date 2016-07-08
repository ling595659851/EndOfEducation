package cn.edu.bupt.springmvc.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.edu.bupt.springmvc.web.model.ComRegular;
import cn.edu.bupt.springmvc.web.model.DomainRule;
import cn.edu.bupt.springmvc.web.model.TypeRegular;
import cn.edu.bupt.springmvc.web.model.URLInfo;
import cn.edu.bupt.springmvc.web.service.ComRegularService;
import cn.edu.bupt.springmvc.web.service.RedisOperationService;
import cn.edu.bupt.springmvc.web.service.TypeRegularService;
import cn.edu.bupt.springmvc.web.service.UrlOperationService;

@Service
public class UrlOperationSerivceImpl implements UrlOperationService {

	/**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
	@Resource
	private ComRegularService com;
	@Resource
	private TypeRegularService type;
	@Resource
	private RedisOperationService redis;
	
	private TypeRegular typeBean = null;
	
	private String[] words = {"wd","q"};
	
	@Override
	public String domainOperation(String domain) {
		// TODO Auto-generated method stub
		String topDomain = " ";
		String secondDomain = " ";
		String thirdDomain = " ";
		String[] domains = domain.split("\\.");
		int length = domains.length;
		if(length < 1) {
			logger.error("分割错误");
			return null;
		}
		String temp = "";
		int nowSite = length - 1;
		while(nowSite > 0) {
			//---------------------处理顶级域名---------------------
			while((temp = DomainRule.getValueOrNull(domains[nowSite])) != null) {
				if(topDomain.trim().isEmpty()) {
					topDomain = temp;
				} else {
					topDomain = topDomain + "," + temp;
				}
				nowSite--;
			}
			if(nowSite == length - 1) {
				//此处说明顶级域名没有匹配成功，可添加操作处理顶级域名
				logger.info("顶级域名未匹配成功，该域名是: " + domains[nowSite]);
				nowSite--;
			}
			ComRegular comBean = null;
			//---------------------顶级域名处理结束-------------------
			//---------------------处理二级域名---------------------
			if((comBean = com.getComRegular(domains[nowSite])) != null) {
				secondDomain = comBean.getName();
			} else {
				//没有匹配成功，进行未匹配成功的操作
				logger.info("二级域名未匹配成功，该域名是: " + domains[nowSite]);
			}
			redis.putIntoRedis(domains[nowSite], 0);
			nowSite--;
			//---------------------二级域名处理结束------------------
			this.typeBean = null;
			//---------------------处理三级域名---------------------
			
			while(nowSite > -1) {
				if(!domains[nowSite].equals("www")){
					if((this.typeBean = type.getTypeRegular(domains[nowSite])) != null) {
						if(thirdDomain.trim().isEmpty()){
							thirdDomain = this.typeBean.getValue();
						} else {
							thirdDomain = thirdDomain + "," + this.typeBean.getValue();
						}
					} else {
						logger.info("三级域名未匹配成功,该域名是: " + domains[nowSite]);
						
					}
					redis.putIntoRedis(domains[nowSite], 1);
				}
				nowSite--;
			}
			//---------------------三级域名处理结束------------------
		}
		return topDomain + ";" + secondDomain + ";" + thirdDomain;
	}

	@Override
	public String uriOperation(List<String> uri) {
		// TODO Auto-generated method stub
		this.typeBean = null;
		String result = "";
		if(uri == null || uri.isEmpty()) {
			return null;
		}
		for(String word : uri) {
			if((this.typeBean = type.getTypeRegular(word)) != null) {
				result += this.typeBean.getValue() + ",";
			}
		}
		if(result.length() == 0) {
			return null;
		} else {
			return result.substring(0, result.length() - 1);
		}
	}

	@Override
	public String parameterOperation(String parameter) {
		// TODO Auto-generated method stub
		if(parameter == null || parameter.isEmpty()) {
			return null;
		}
		try {
			parameter = URLDecoder.decode(parameter,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//urldecode错误，添加日志
			logger.error("url转码错误!");
			return null;
		}
		String[] keyValues = parameter.split("&");
		for(String keyValue : keyValues) {
			String[] keyAndValue = keyValue.split("=");
			if(keyAndValue.length == 2) {
				//此部分针对参数键值对进行操作
				String key = keyAndValue[0];
				String value = keyAndValue[1];
				
				
			} else {
				//异常部分，可添加日志进行处理
				logger.error("参数不正确");
			}
		}
		return null;
	}

	/**
	 * @param str1:包含第一级标签，可能会包含第二级标签
	 * 		  str2:可能会包含第三级标签
	 * 	      str3:可能会包含第三级标签
	 */
	@Override
	public URLInfo combinationOperation(String str1, String str2, String str3) {
		// TODO Auto-generated method stub
		URLInfo urlInfo = new URLInfo();
		if((str1 == null || str1.isEmpty()) ) {
			urlInfo.setFirst_label("empty");
		} else {
			String[] strs = str1.split(";");
			try {
				urlInfo.setFirst_label(strs[1]);
				if(!strs[2].trim().isEmpty()) {
					urlInfo.setSecond_label(strs[2]);
				}
			} catch(Exception e) {
				logger.error("something wrong with str1 and str1 is: " + str1);
			}
		}
		
		if(str2 == null || str2.isEmpty()) {
			if(urlInfo.getSecond_label() == null) {
				urlInfo.setSecond_label("empty");
			}
		} else {
			if(urlInfo.getSecond_label() == null) {
				urlInfo.setSecond_label(str2);
			} else {
				urlInfo.setThird_label(str2);
			}
		}
		
		if(str3== null || str3.isEmpty()) {
			if(urlInfo.getThird_label() == null) {
				urlInfo.setThird_label("empty");
			}
		} else {
			if(urlInfo.getThird_label() == null) {
				urlInfo.setThird_label(str3);
			}
		}
		return urlInfo;
	}
}
