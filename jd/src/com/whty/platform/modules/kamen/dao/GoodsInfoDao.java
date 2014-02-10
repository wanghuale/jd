/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.kamen.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.kamen.entity.Goods;

/**
 * 字典DAO接口
 * 
 * @author 舒海洋
 * @version 2013-01-15
 */
public interface GoodsInfoDao extends GoodsInfoDaoCustom, CrudRepository<Goods, Long> {

}

/**
 * DAO自定义接口
 * 
 * @author 舒海洋
 */
interface GoodsInfoDaoCustom extends BaseDao<Goods> {

}

/**
 * DAO自定义接口实现
 * 
 * @author 舒海洋
 */
@Component
class GoodsInfoDaoImpl extends BaseDaoImpl<Goods> implements GoodsInfoDaoCustom {

}
