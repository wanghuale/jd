/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.sys.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author 舒海洋
 * @version 2013-06-02
 */
public interface LogDao extends LogDaoCustom, CrudRepository<Log, Long> {
	
}

/**
 * DAO自定义接口
 * @author 舒海洋
 */
interface LogDaoCustom extends BaseDao<Log> {

}

/**
 * DAO自定义接口实现
 * @author 舒海洋
 */
@Component
class LogDaoImpl extends BaseDaoImpl<Log> implements LogDaoCustom {

}
