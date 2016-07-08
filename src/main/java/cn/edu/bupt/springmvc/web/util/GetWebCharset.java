package cn.edu.bupt.springmvc.web.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetWebCharset {
	
	private String[] userAgents = {
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0",
			"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN) AppleWebKit/533.17.8 (KHTML, like Gecko) Version/5.0.1 Safari/533.17.8",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; 360SE)",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322) QQBrowser/6.8.10793.201"
	};
	
	public String[] getUserAgents() {
		return this.userAgents;
	}
	
	//获取网页编码
	public static String getCharset(String url,InputStream input) throws Exception {
		BufferedReader buff = null;
		String line = "";
		if(input != null){
//			input = entity.getContent();
			buff = new BufferedReader(new InputStreamReader(input, "utf-8"));
			while((line = buff.readLine()) != null){
				if(line.contains("charset")){
					String charset = line.toLowerCase().substring(line.indexOf("charset=") + 8);
					int j;
					int i = 0;
					for(j = 1; j < charset.length(); j++){
						if(charset.charAt(j) == '"'){
							break;
						}
					}
					if(charset.charAt(i) == '"'){
						i = 1;
					}
					return charset.substring(i,j);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
//		//mybatis的配置文件
//        String resource = "conf.xml";
//        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
//        InputStream is = GetWebCharset.class.getClassLoader().getResourceAsStream(resource);
//        //构建sqlSession的工厂
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
//        //Reader reader = Resources.getResourceAsReader(resource); 
//        //构建sqlSession的工厂
//        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        //创建能执行映射文件中sql的sqlSession
//        SqlSession session = sessionFactory.openSession();
//        /**
//         * 映射sql的标识字符串，
//         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
//         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
//         */
//        String statement = "mapping.comRegularMapper.getKey";//映射sql的标识字符串
//        //执行查询返回一个唯一user对象的sql
//        CompanyRegular user = session.selectOne(statement,"163");
//        System.out.println(user);
		System.out.print(Integer.toBinaryString(0x55555555));
	}
}
