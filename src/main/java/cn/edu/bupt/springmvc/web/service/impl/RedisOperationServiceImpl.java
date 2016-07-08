package cn.edu.bupt.springmvc.web.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import cn.edu.bupt.springmvc.web.model.ComRegular;
import cn.edu.bupt.springmvc.web.model.TypeRegular;
import cn.edu.bupt.springmvc.web.service.ComRegularService;
import cn.edu.bupt.springmvc.web.service.RedisOperationService;
import cn.edu.bupt.springmvc.web.service.TypeRegularService;

@Service
public class RedisOperationServiceImpl implements RedisOperationService {
	
	/**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

	private static Jedis jedis = null;
	private String comHashName = "comHash";
	private String typeHashName = "typeHash";
	
	private ComRegular comRegular = null;
	private TypeRegular typeRegular = null;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Autowired
	private ComRegularService com;
	
	private static synchronized Jedis getJedis() {
		// TODO Auto-generated method stub
		if(jedis == null) {
			jedis = new Jedis("120.27.115.214");
		}
		return jedis;
	}
	
	
	/**
	 * @param key:出现的关键字
	 * 		  choice: 0为公司，1位类型
	 */
	@Override
	public void putIntoRedis(String key,int choice) {
		// TODO Auto-generated method stub
		jedis = getJedis();
		if(jedis == null) {
			logger.error("Redis未初始化或未连接!");
			return;
		}
		
		String hashName = "";
		if(choice == 0) {
			hashName = comHashName;
		} else if(choice == 1) {
			hashName = typeHashName;
		} else {
			return;
		}
		lock.writeLock().lock();
		try {
			if(jedis.hsetnx(hashName, key, "1") == 0) {
				jedis.hincrBy(hashName, key, 1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void copyIntoMySQL() {
		// TODO Auto-generated method stub
		jedis = getJedis();
		if(jedis == null) {
			logger.error("Redis未初始化或未连接!");
			return;
		}
		Map<String, String> comMap = null;
		Map<String, String> typeMap = null;
		
		lock.writeLock().lock();
		try {
			if(jedis.hlen(comHashName) != 0) {
				comMap = jedis.hgetAll(comHashName);
				jedis.del(comHashName);
			}
			if(jedis.hlen(typeHashName) != 0) {
				typeMap = jedis.hgetAll(typeHashName);
				jedis.del(typeHashName);
			}
		} catch(Exception e) {
			logger.error("锁异常");
			e.printStackTrace();
			return;
		} finally {
			lock.writeLock().unlock();
		}
		Set<Entry<String, String>> set = null;
		if(comMap != null) {
			set = comMap.entrySet();
			
			for(Iterator<Entry<String, String>> it = set.iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				Long value = Long.valueOf(entry.getValue());
				
				System.out.println("the key is: " + key);
				if(key == null || key.isEmpty()) {
					continue;
				}
				
				try {
					comRegular = com.getComRegular(key);
					comRegular.setCount(comRegular.getCount() + value);
					com.update(comRegular);
				} catch(NullPointerException e) {
					comRegular = new ComRegular();
					comRegular.setCompany(key);
					comRegular.setCount(value);
					com.insert(comRegular);
				} catch(Exception e) {
					
				}
			}
		}
		
		if(typeMap != null) {
			set = typeMap.entrySet();
			TypeRegularService type = new TypeRegularServiceImpl();
			for(Iterator<Entry<String, String>> it = set.iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				Long value = Long.valueOf(entry.getValue());
				
				if(key == null || key.isEmpty()) {
					continue;
				}
				
				try{
					typeRegular = type.getTypeRegular(key);
					typeRegular.setCount(typeRegular.getCount() + value);
					type.update(typeRegular);
				}catch(NullPointerException e) {
					typeRegular = new TypeRegular();
					typeRegular.setName(key);
					typeRegular.setCount(value);
				}
			}
		}
	}

}
