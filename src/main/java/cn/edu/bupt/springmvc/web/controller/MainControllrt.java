package cn.edu.bupt.springmvc.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.bupt.springmvc.core.generic.GenericController;
import cn.edu.bupt.springmvc.web.model.URLInfo;
import cn.edu.bupt.springmvc.web.service.ComRegularService;
import cn.edu.bupt.springmvc.web.service.PreOperationService;
import cn.edu.bupt.springmvc.web.service.RedisOperationService;
import cn.edu.bupt.springmvc.web.service.TypeRegularService;
import cn.edu.bupt.springmvc.web.service.UrlOperationService;
import cn.edu.bupt.springmvc.web.service.WebContentOperationService;

@Controller
@RequestMapping(value = "/main")
public class MainControllrt extends GenericController{

	@Resource
	private PreOperationService preoperation;
	@Resource
	private ComRegularService com;
	@Resource
	private TypeRegularService type;
	@Resource
	private UrlOperationService urloperation;
	@Resource
	private RedisOperationService redis;
	@Resource
	private WebContentOperationService webcontent;
	
	private HashMap<String,String> contentMap = new HashMap<String,String>() {
		{
			put("京东","jd");
		}
	};
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void index(@RequestParam("url") String url, HttpServletResponse response) throws IOException, InterruptedException{
		String header_url = preoperation.DealHeader(url);
		StringBuilder sb = new StringBuilder();
		String parameter = "";		//参数
		String domain = "";			//域名
		List<String> uri = new LinkedList<String>();//uri列表
		
		if(header_url == null){
			logger.error("不支持的url类型");
			sb.append("{\"SUCCESS\":false,\"ERRMSG\":\"不支持url类型\"}");
		}else{
			String md_url = preoperation.Transcoding(header_url);
			//此处为调用HBaseAPI
			
			//此处为在HBase中查到数据时的操作
			
			//此处为在HBase中未查到数据时的操作
			//url分割
			String[] domains = header_url.split("/");
			int url_num = domains.length;
			if(url_num < 1){
				logger.error("域名分隔有误，此时的url是：" + header_url);
				sb.append("{\"SUCCESS\":false,\"ERRMSG\":\"不格式错误\"}");
			}else{
				domain = domains[0];
				for(int i = 1; i < url_num; i++){
					if(!domains[i].contains("?")){
						uri.add(domains[i]);
					}else{
						String[] temp = domains[i].split("?");
						if(temp.length < 3 && temp.length > 0){
							uri.add(temp[0]);
							if(temp.length == 2){
								parameter = temp[1];
							}
						}
						temp = null;
					}
				}
			}
			
			//域名处理
			String str1 = urloperation.domainOperation(domain);
			//uri处理
			String str2 = urloperation.uriOperation(uri);
			//参数处理
			String str3 = urloperation.parameterOperation(parameter);
			//URL内容处理
			List list = null;
			try {
				String label = str1.split(";")[1];
				if(contentMap.get(label) != null) {
					String value = contentMap.get(label);
					list = webcontent.connectToHttp(url, value);
				} else {
					logger.info("map表中不存在");
				}
			} catch(Exception e) {
				logger.error("爬取失败");
				e.printStackTrace();
			}
			URLInfo urlInfo = null;
			
			//拼接域名结果
			try{
				urlInfo = urloperation.combinationOperation(str1, str2, str3);
			} catch(Exception e) {
				logger.error("wrong at comibinationOperation");
			}
			urlInfo.setUrl(md_url);
			//拼接文本结果
			try{
				if(list == null) {
					System.out.println("list为空");
				}else{
					urlInfo = webcontent.combinationOperation(list, urlInfo);
//					System.out.println(list.toString());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			//写入HBase
			
			//输出
			myResponse(response,urlInfo.toString());
		}
		
	}
	
	@RequestMapping(value = "/gettype", method = RequestMethod.GET)
	public void getUrlName(@RequestParam("url") String url, HttpServletResponse response) throws IOException {
		if(url == null || url.isEmpty()) {
			logger.error("No url");
			return;
		}
		System.out.println("url is: " + url);
		String header_url = preoperation.DealHeader(url);
		if(header_url == null){
			logger.error("不支持的url类型");
			String result = "{\"SUCCESS\":false,\"DATA\":[],\"MSG\":\"不支持的url类型\"}";
			myResponse(response, result);
			return;
		}
		System.out.println("header_url is: " + header_url);
		String[] domains = header_url.split("/");
		int url_num = domains.length;
		if(url_num < 1){
			logger.error("url分隔错误");
			String result = "{\"SUCCESS\":false,\"DATA\":[],\"MSG\":\"url分隔错误\"}";
			myResponse(response, result);
			return;
		}
		String domain = domains[0];
		String out = "";
		
		try{
			out = urloperation.domainOperation(domain);
		}catch(Exception e) {
			String result = "{\"SUCCESS\":false,\"DATA\":[],\"MSG\":\"url处理错误\"}";
			myResponse(response, result);
			return;
		}
		
		String[] outDomains = out.split(";");
		for(String s : outDomains) {
			System.out.println("s: " + s);
		}
		String result = "{\"SUCCESS\":true,\"DATA\":[\"%s\",\"%s\",\"%s\"]}";
		
		result = String.format(result, outDomains[0].trim(),outDomains[1].trim(),outDomains[2].trim());
		myResponse(response, result);
	}
	
	@RequestMapping(value = "/geturltype", method = RequestMethod.GET)
	public void getUrlType(@RequestParam("url") String url, HttpServletResponse response) throws IOException {
		if(url == null || url.isEmpty()) {
			logger.error("No url");
			return;
		}
		System.out.println("url is: " + url);
		String header_url = preoperation.DealHeader(url);
		if(header_url == null){
			logger.error("不支持的url类型");
			String result = "{\"SUCCESS\":false,\"DATA\":[],\"MSG\":\"不支持的url类型\"}";
			myResponse(response, result);
			return;
		}
		System.out.println("header_url is: " + header_url);
		String[] domains = header_url.split("/");
		int url_num = domains.length;
		if(url_num < 1){
			logger.error("url分隔错误");
			String result = "{\"SUCCESS\":false,\"DATA\":[],\"MSG\":\"url分隔错误\"}";
			myResponse(response, result);
			return;
		}
		
		
	}
	
	@RequestMapping(value = "/tosql", method = RequestMethod.GET)
	public void redisToMysql(HttpServletResponse response) throws IOException {
		String result = "";
		try {
			redis.copyIntoMySQL();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("拷贝至数据库出错！");
			result = "{\"SUCCESS\":false}";
			myResponse(response, result);
			return;
		}
		result = "{\"SUCCESS\":true}";
		myResponse(response, result);
	}

}