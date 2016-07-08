package cn.edu.bupt.springmvc.web.dao;

import cn.edu.bupt.springmvc.core.generic.GenericDao;
import cn.edu.bupt.springmvc.web.model.TypeRegular;
import cn.edu.bupt.springmvc.web.model.TypeRegularExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TypeRegularMapper extends GenericDao<TypeRegular, Long> {
    int countByExample(TypeRegularExample example);

    int deleteByExample(TypeRegularExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TypeRegular record);

    int insertSelective(TypeRegular record);

    List<TypeRegular> selectByExample(TypeRegularExample example);

    TypeRegular selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TypeRegular record, @Param("example") TypeRegularExample example);

    int updateByExample(@Param("record") TypeRegular record, @Param("example") TypeRegularExample example);

    int updateByPrimaryKeySelective(TypeRegular record);

    int updateByPrimaryKey(TypeRegular record);
}