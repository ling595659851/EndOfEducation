package cn.edu.bupt.springmvc.web.model;

import java.util.Map;

import cn.edu.bupt.springmvc.web.util.FileWithMap;


public final class DomainRule {

	private static final String file = "configure/TopLevelDomain.txt";
	
	public static Map<String,String> getDomainMap(){
		
		if(DomainHolder.DomainMap == null){
			try {
				DomainHolder.DomainMap = FileWithMap.getMapFromFile(file);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return DomainHolder.DomainMap;
	}
	
	private static class DomainHolder{
		private static Map<String,String> DomainMap;
	}
	
	public static String getValueOrNull(String key){
		return getDomainMap().get(key);
	}
}
