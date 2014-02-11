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
import com.whty.platform.modules.bussiness.entity.ServiceRecord;

/**
 * 服务DAO接口
 * 
 * @author qimin
 * @version 2013-07-16
 */
public interface ServiceRecordDao extends ServiceRecordDaoCustom, CrudRepository<ServiceRecord, Long> {

	@Modifying
	@Query("update ServiceRecord set delFlag='" + ServiceRecord.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author qimin
 */
interface ServiceRecordDaoCustom extends BaseDao<ServiceRecord> {

}

/**
 * DAO自定义接口实现
 * 
 * @author qimin
 */
@Component
class ServiceRecordDaoImpl extends BaseDaoImpl<ServiceRecord> implements ServiceRecordDaoCustom {

}
