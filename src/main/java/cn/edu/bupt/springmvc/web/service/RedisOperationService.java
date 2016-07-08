package cn.edu.bupt.springmvc.web.service;

public interface RedisOperationService {
	
	public void putIntoRedis(String key,int choice);
	
	public void copyIntoMySQL();
}
