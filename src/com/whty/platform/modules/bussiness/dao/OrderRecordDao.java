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
import com.whty.platform.modules.bussiness.entity.OrderRecord;

/**
 * 订单DAO接口
 * 
 * @author qimin
 * @version 2013-08-13
 */
public interface OrderRecordDao extends OrderRecordDaoCustom, CrudRepository<OrderRecord, Long> {

	@Modifying
	@Query("delete OrderRecord where id = ?1")
	public int deleteById(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author qimin
 */
interface OrderRecordDaoCustom extends BaseDao<OrderRecord> {

}

/**
 * DAO自定义接口实现
 * 
 * @author qimin
 */
@Component
class OrderRecordDaoImpl extends BaseDaoImpl<OrderRecord> implements OrderRecordDaoCustom {

}
