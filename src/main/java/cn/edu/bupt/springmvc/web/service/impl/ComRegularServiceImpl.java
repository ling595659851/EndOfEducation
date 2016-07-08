package cn.edu.bupt.springmvc.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bupt.springmvc.core.generic.GenericDao;
import cn.edu.bupt.springmvc.core.generic.GenericServiceImpl;
import cn.edu.bupt.springmvc.web.dao.ComRegularMapper;
import cn.edu.bupt.springmvc.web.model.ComRegular;
import cn.edu.bupt.springmvc.web.model.ComRegularExample;
import cn.edu.bupt.springmvc.web.model.ComRegularExample.Criteria;
import cn.edu.bupt.springmvc.web.service.ComRegularService;

@Service
public class ComRegularServiceImpl extends GenericServiceImpl<ComRegular, Long> implements ComRegularService {

	@Autowired
	private ComRegularMapper map;
	
	
	@Override
	public int insert(ComRegular model) {
		// TODO Auto-generated method stub
		return map.insert(model);
	}

	@Override
	public int update(ComRegular model) {
		// TODO Auto-generated method stub
		return map.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return map.deleteByPrimaryKey(id);
	}

	@Override
	public ComRegular selectById(Long id) {
		// TODO Auto-generated method stub
		return map.selectByPrimaryKey(id);
	}

	@Override
	public ComRegular selectOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComRegular> selectList() {
		// TODO Auto-generated method stub
		ComRegularExample example = new ComRegularExample();
//		example.createCriteria().andCompanyEqualTo("");
		example.createCriteria().andCompanyIsNotNull();
		example.setOrderByClause("count DESC");
		List<ComRegular> list = map.selectByExample(example);
		return list;
	}

	@Override
	public ComRegular getComRegular(String company) {
		// TODO Auto-generated method stub
		ComRegularExample example = new ComRegularExample();
		example.createCriteria().andCompanyEqualTo(company);
		if(map == null) {
			System.out.println("map is null");
			System.exit(0);
		}
		List<ComRegular> list = map.selectByExample(example);
		System.out.println(list.toString());
		if(list != null && !list.isEmpty() && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public GenericDao<ComRegular, Long> getDao() {
		// TODO Auto-generated method stub
		return map;
	}

	@Override
	public List<ComRegular> getByTypeAndCountForCompany(String company,String type,Long count) {
		// TODO Auto-generated method stub
		ComRegularExample example = new ComRegularExample();
		Criteria criteria = example.createCriteria();
		if(company != null) {
			criteria.andNameIsNull();
		}
		if(type != null) {
			criteria.andTypeEqualTo(type);
		}
		criteria.andCountGreaterThanOrEqualTo(count);
		example.setOrderByClause("count DESC");
		List<ComRegular> list = map.selectByExample(example);
		return list;
	}

}
