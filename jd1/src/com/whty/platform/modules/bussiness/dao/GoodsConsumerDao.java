/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.bussiness.entity.GoodsConsumer;

/**
 * 对账DAO接口
 * 
 * @author 舒海洋
 * @version 2013-11-22
 */
public interface GoodsConsumerDao extends GoodsConsumerDaoCustom, CrudRepository<GoodsConsumer, Long> {

	@Modifying
	@Query("delete GoodsConsumer  where id = ?1")
	public int deleteById(Long id);
	
	
	@Modifying
	@Query("delete GoodsConsumer  where consumer.id = ?1")
	public int deleteByConsumerId(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author 舒海洋
 */
interface GoodsConsumerDaoCustom extends BaseDao<GoodsConsumer> {

}

/**
 * DAO自定义接口实现
 * 
 * @author 舒海洋
 */
@Component
class GoodsConsumerDaoImpl extends BaseDaoImpl<GoodsConsumer> implements GoodsConsumerDaoCustom {

}
