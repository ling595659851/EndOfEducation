package cn.edu.bupt.springmvc.web.service;

import java.util.List;

import cn.edu.bupt.springmvc.core.generic.GenericService;
import cn.edu.bupt.springmvc.web.model.ComRegular;

public interface ComRegularService extends GenericService<ComRegular, Long> {
	
	ComRegular getComRegular(String company);
	
	List<ComRegular> getByTypeAndCountForCompany(String company,String type,Long count);
}
