package cn.edu.bupt.springmvc.web.service;

import java.util.List;

import cn.edu.bupt.springmvc.core.generic.GenericService;
import cn.edu.bupt.springmvc.web.model.ComRegular;
import cn.edu.bupt.springmvc.web.model.TypeRegular;

public interface TypeRegularService extends GenericService<TypeRegular, Long> {
	
	TypeRegular getTypeRegular(String name);
	
	List<TypeRegular> getByTypeAndCountForName(String name,String type,Long count);
}
