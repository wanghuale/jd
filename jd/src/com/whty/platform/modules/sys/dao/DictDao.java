/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author 舒海洋
 * @version 2013-01-15
 */
public interface DictDao extends DictDaoCustom, CrudRepository<Dict, Long> {
	
	@Modifying
	@Query("update Dict set delFlag='" + Dict.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' order by sort")
	public List<Dict> findAllList();

	@Query("select type from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' group by type")
	public List<String> findTypeList();
	
	@Query("from Dict where value= ?1 and delFlag='" + Dict.DEL_FLAG_NORMAL + "'")
	public List<Dict> findByValue(String value);
	
	@Query("from Dict where value= ?1 and type=?2 and delFlag='" + Dict.DEL_FLAG_NORMAL + "'")
	public List<Dict> findByValueT(String value,String type);
		
//	@Query("from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' and type=?1 order by sort")
//	public List<Dict> findByType(String type);
//	
//	@Query("select label from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' and value=?1 and type=?2")
//	public List<String> findValueByValueAndType(String value, String type);
	
}

/**
 * DAO自定义接口
 * @author 舒海洋
 */
interface DictDaoCustom extends BaseDao<Dict> {

}

/**
 * DAO自定义接口实现
 * @author 舒海洋
 */
@Component
class DictDaoImpl extends BaseDaoImpl<Dict> implements DictDaoCustom {

}
