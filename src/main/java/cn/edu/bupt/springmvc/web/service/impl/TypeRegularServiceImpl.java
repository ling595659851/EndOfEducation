package cn.edu.bupt.springmvc.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.bupt.springmvc.core.generic.GenericDao;
import cn.edu.bupt.springmvc.core.generic.GenericServiceImpl;
import cn.edu.bupt.springmvc.web.dao.TypeRegularMapper;
import cn.edu.bupt.springmvc.web.model.TypeRegular;
import cn.edu.bupt.springmvc.web.model.TypeRegularExample;
import cn.edu.bupt.springmvc.web.model.TypeRegularExample.Criteria;
import cn.edu.bupt.springmvc.web.service.TypeRegularService;

@Service
public class TypeRegularServiceImpl extends GenericServiceImpl<TypeRegular, Long> implements TypeRegularService {

	@Resource
	private TypeRegularMapper map;
	
	@Override
	public int insert(TypeRegular model) {
		// TODO Auto-generated method stub
		return map.insertSelective(model);
	}

	@Override
	public int update(TypeRegular model) {
		// TODO Auto-generated method stub
		return map.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return map.deleteByPrimaryKey(id);
	}

	@Override
	public TypeRegular selectById(Long id) {
		// TODO Auto-generated method stub
		return map.selectByPrimaryKey(id);
	}

	@Override
	public TypeRegular selectOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeRegular> selectList() {
		// TODO Auto-generated method stub
		TypeRegularExample example = new TypeRegularExample();
//		example.createCriteria();
		example.setOrderByClause("count DESC");
		List<TypeRegular> list = map.selectByExample(example);
		return list;
	}

	@Override
	public TypeRegular getTypeRegular(String name) {
		// TODO Auto-generated method stub
		TypeRegularExample example = new TypeRegularExample();
		example.createCriteria().andNameEqualTo(name);
		List<TypeRegular> list = map.selectByExample(example);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public GenericDao<TypeRegular, Long> getDao() {
		// TODO Auto-generated method stub
		return map;
	}

	@Override
	public List<TypeRegular> getByTypeAndCountForName(String name, String type, Long count) {
		// TODO Auto-generated method stub
		TypeRegularExample example = new TypeRegularExample();
		Criteria criteria = example.createCriteria();
		if(name != null) {
			criteria.andValueIsNull();
		}
		if(type != null) {
			criteria.andTypeEqualTo(type);
		}
		criteria.andCountGreaterThanOrEqualTo(count);
		example.setOrderByClause("count DESC");
		List<TypeRegular> list = map.selectByExample(example);
		return list;
	}

}
