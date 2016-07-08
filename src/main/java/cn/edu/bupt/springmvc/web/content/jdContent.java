package cn.edu.bupt.springmvc.web.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class jdContent {
	
	public jdContent() {
		
	}
	
	public List solution(java.io.InputStream input,java.lang.String charset) throws IOException {
		List<String> list = new ArrayList<String>();
		
		BufferedReader buff = null;
		
		buff = new BufferedReader(new InputStreamReader(input, charset));
		String line = "";
		try {
			while((line = buff.readLine()) != null) {
				if(line.contains("<div class=\"breadcrumb\">")){
					while((line = buff.readLine()) != null){
						String[] strs = line.split("</a>");
						for(String a : strs){
							if(a.contains("clstag")){
								String m = new String(a.substring(a.indexOf("\">")+2));
								list.add(m + ":1.0");
							}
						}
						if(line.contains("</div>")){
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
		
		System.out.println(list.toString());
		return list;
	}
	
}
