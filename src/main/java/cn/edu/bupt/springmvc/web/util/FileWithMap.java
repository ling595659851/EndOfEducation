package cn.edu.bupt.springmvc.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileWithMap {
	public static ConcurrentHashMap<String,String> getMapFromFile(String filepath) throws Exception{
		//获取文件路径
		String path = FileWithMap.class.getResource("FileWithMap.class").toString();
//		System.out.println("path is: " + path);
		path = path.substring(path.indexOf("file:/") + 5,path.lastIndexOf("WEB-INF"));
//		System.out.println("path is: " + (path + filepath));
		File file = new File(path + filepath);
		if(!file.exists()){
			throw new Exception("指定的map不存在");
		}
		ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
		
		BufferedReader br = new BufferedReader(new UnicodeReader(new FileInputStream(file),"utf-8"));
		String str;
		while((str = br.readLine()) != null){
			String[] strs = str.split(" ");
			try{
				map.put(strs[0], strs[1]);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		br.close();
		return map;
	}
	
	public static void writeFileFromMap(Map<String,String> map,String filepath) throws IOException{
		File file = new File(System.getProperty("user.dir"),filepath);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
		for(Map.Entry<String, String> entry : map.entrySet()){
			bw.write(entry.getKey() + " " + entry.getValue());
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	/**以追加的方式将数据写入字符串中
	 * 
	 * @param str 追加的字符串数据
	 * @param filepath	要追加的文件位置
	 * @throws IOException
	 */
	public static void appendFile(String str,String filepath) throws IOException{
		File file = new File(System.getProperty("user.dir"),filepath);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8"));
		bw.write(str);
		bw.flush();
		bw.close();
	}
}
