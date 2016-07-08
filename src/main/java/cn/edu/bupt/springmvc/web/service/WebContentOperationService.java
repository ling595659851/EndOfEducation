package cn.edu.bupt.springmvc.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.edu.bupt.springmvc.web.model.URLInfo;

public interface WebContentOperationService {
	public List connectToHttp(String url,String className) throws IOException, InterruptedException;
	
	public URLInfo combinationOperation(List<String> list, URLInfo urlInfo);
}
