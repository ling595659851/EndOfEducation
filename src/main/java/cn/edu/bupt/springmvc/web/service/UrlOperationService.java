package cn.edu.bupt.springmvc.web.service;

import java.util.List;

import cn.edu.bupt.springmvc.web.model.URLInfo;

public interface UrlOperationService {

	public String domainOperation(String domain);
	
	public String uriOperation(List<String> uri);
	
	public String parameterOperation(String parameter);
	
	public URLInfo combinationOperation(String str1,String str2,String str3);
}
