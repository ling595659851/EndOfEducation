package cn.edu.bupt.springmvc.web.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import cn.edu.bupt.springmvc.web.model.URLInfo;
import cn.edu.bupt.springmvc.web.service.WebContentOperationService;
import cn.edu.bupt.springmvc.web.util.GetWebCharset;

@Service
public class WebContentOperationServiceImpl implements WebContentOperationService {
	
	private String[] userAgents = {
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0",
			"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN) AppleWebKit/533.17.8 (KHTML, like Gecko) Version/5.0.1 Safari/533.17.8",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; 360SE)",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322) QQBrowser/6.8.10793.201"
	};
	
	private BufferedReader buff = null;
	
	private InputStream input = null;
	private String charset = null;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		WebContentOperationServiceImpl s = new WebContentOperationServiceImpl();
		s.connectToHttp("http://item.jd.com/1629605.html","jd");
	}

	@Override
	public List connectToHttp(String url,String methodName) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Random r =new Random((new Date()).getTime());
		int randomSleep = r.nextInt(10);
		//休眠部分时间
		System.out.println("sleep time is: " + (randomSleep+5)*600);
//		Thread.sleep((randomSleep+5)*600);
		int random = r.nextInt(this.userAgents.length);
		System.out.println("random is: " + random);
		HttpClient hc = new DefaultHttpClient();
		HttpGet hg = new HttpGet(url);
		hg.setHeader("User-Agent", this.userAgents[random]);
		HttpResponse response = hc.execute(hg);
		HttpEntity entity = response.getEntity();
		
		
		int code = response.getStatusLine().getStatusCode();
		
		if(code != 200){
			System.out.println("返回的状体码:" + code);
			return null;
		}
		
		String charset = null;
		List list = null;
		Header header = response.getFirstHeader("Content-Type");
		if(header != null) {
			System.out.println(header.getValue());
			try{
				String[] temp = header.getValue().split(";");
				charset = temp[1].trim();
				temp = null;
				charset = charset.substring(charset.indexOf("charset=") + 8);
			} catch(Exception e) {
			}
		}
		
		InputStream input = null;
		
		try {
			if(entity != null) {
				//分析内容
				input = entity.getContent();
				String realMethodName = methodName + "Content";
				//通过反射调用方法，增加系统的可扩展性
//				Class c = this.getClass();
//				Method m = c.getDeclaredMethod(realMethodName);
				Method method = null;
				Object o = null;
				try{
					//通过反射实现多类的统一方法调用
					Class c = Class.forName("cn.edu.bupt.springmvc.web.content." + realMethodName);
//					c.getDeclaredMethod("solution", parameterTypes)
					o = c.newInstance();
					
					method = c.getMethod("solution", new Class[]{InputStream.class,String.class});
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				if(charset == null)  {
					charset = GetWebCharset.getCharset(url,input);
				}
				if(charset == null || charset.isEmpty()) {
					charset = "utf-8";
				}
				if(input == null) {
					return null;
				}
				this.charset = charset;
				this.input = input;
				try{
					list = (List)method.invoke(o, new Object[]{input,charset});
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
		
		return list;
	}

//	private void jdContent() throws IOException {
//		List<String> list = new ArrayList<String>();
//		
//		buff = new BufferedReader(new InputStreamReader(this.input, this.charset));
//		String line = "";
//		try {
//			while((line = buff.readLine()) != null) {
//				if(line.contains("<div class=\"breadcrumb\">")){
//					while((line = buff.readLine()) != null){
//						String[] strs = line.split("</a>");
//						for(String a : strs){
//							if(a.contains("clstag")){
//								String m = new String(a.substring(a.indexOf("\">")+2));
//								list.add(m);
//							}
//						}
//						if(line.contains("</div>")){
//							break;
//						}
//					}
//					break;
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			buff.close();
//		}
//		
//		System.out.println(list.toString());
//	}
	
	private void Content() throws IOException {
		String line = "";
		try {
			buff = new BufferedReader(new InputStreamReader(this.input, this.charset));
			
			while((line = buff.readLine()) != null){
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			buff.close();
		}
	}
	
	private void suningContent() throws IOException {
		String line = "";
		List<String> list = new ArrayList<String>();
		
		try {
			buff = new BufferedReader(new InputStreamReader(this.input, this.charset));
			while((line = buff.readLine()) != null){
				if(line.contains("<div class=\"breadcrumb\">")){
					while((line = buff.readLine()) != null){
						if(line.contains("id=\"category1\"")){
							list.add(new String(line.substring(line.lastIndexOf("\" >")+3,line.indexOf("</a>"))));
							while((line = buff.readLine()) != null){
								if(line.contains("class=\"ft\"")){
									list.add(new String(line.substring(line.lastIndexOf("\">")+2,line.indexOf("</a>"))));
									while((line = buff.readLine()) != null){
										if(line.contains("class=\"ft\"")){
											list.add(new String(line.substring(line.lastIndexOf("\">")+2,line.indexOf("</a>"))));
											while((line = buff.readLine()) != null){
												if(line.contains("class=\"breadcrumb-title\"")){
													list.add(new String(line.substring(line.indexOf("title=\"")+7,line.indexOf("\" id=")).trim()));
													break;
												}
											}
											break;
										}
									}
									break;
								}
							}
							break;
						}
					}
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			buff.close();
		}
	}

	
	@Override
	public URLInfo combinationOperation(List<String> list, URLInfo urlInfo) {
		// TODO Auto-generated method stub
		if(list == null) {
			return urlInfo;
		}
		try{
			Map<String,String> map = new HashMap<String,String>();
			for(Iterator<String> it = list.iterator();it.hasNext();) {
				String key_value = it.next();
				System.out.println("key-value is:" + key_value);
				String[] temp = key_value.split(":");
				map.put(temp[0], temp[1]);
			}
			urlInfo.setMap(map);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return urlInfo;
	}

	
}
