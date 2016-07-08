package cn.edu.bupt.springmvc.web.dao;

import cn.edu.bupt.springmvc.core.generic.GenericDao;
import cn.edu.bupt.springmvc.web.model.ComRegular;
import cn.edu.bupt.springmvc.web.model.ComRegularExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ComRegularMapper extends GenericDao<ComRegular, Long>{
    int countByExample(ComRegularExample example);

    int deleteByExample(ComRegularExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ComRegular record);

    int insertSelective(ComRegular record);

    List<ComRegular> selectByExample(ComRegularExample example);

    ComRegular selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ComRegular record, @Param("example") ComRegularExample example);

    int updateByExample(@Param("record") ComRegular record, @Param("example") ComRegularExample example);

    int updateByPrimaryKeySelective(ComRegular record);

    int updateByPrimaryKey(ComRegular record);
}