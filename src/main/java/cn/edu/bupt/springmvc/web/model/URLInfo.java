package cn.edu.bupt.springmvc.web.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class URLInfo implements Serializable{
	/**URL信息的实体
	 * 
	 */
	private static final long serialVersionUID = -7554370711489625723L;
	
	private String url;						//url的内容
	
	private String first_label;			//第一级标签
	
	private String second_label;			//第二级标签
	
	private String third_label;			//第三级标签
	
	private Map<String,String> map;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFirst_label() {
		return first_label;
	}

	public void setFirst_label(String first_label) {
		this.first_label = first_label;
	}

	public String getSecond_label() {
		return second_label;
	}

	public void setSecond_label(String second_label) {
		this.second_label = second_label;
	}

	public String getThird_label() {
		return third_label;
	}

	public void setThird_label(String third_label) {
		this.third_label = third_label;
	}

	public Map<String,String> getMap() {
		return map;
	}

	public void setMap(Map<String,String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("url:");
		sb.append(this.url);
		sb.append(", first_label:");
		sb.append(this.first_label);
		sb.append(", second_label:");
		sb.append(this.second_label);
		sb.append(", third_label:");
		sb.append(this.third_label);
//		sb.append();
		sb.append(", properties:{");
		System.out.println(map.toString());
		if(this.map != null) {
			StringBuffer subSb = new StringBuffer();
			for(String key : this.map.keySet()) {
				if(subSb.length() != 0) {
					subSb.append(",");
				}
				Double value = Double.valueOf(this.map.get(key).toString());
				subSb.append("\"" + key + ": " + value + "\"");
			}
			sb.append(subSb.toString());
		}
		sb.append("}");
		return sb.toString();
	}
	
//	private static class SingletonHolder{
//		private static URLInfo instance = new URLInfo();
//	}
//	
//	public static URLInfo getInstance(){
//		return SingletonHolder.instance;
//	}
}
