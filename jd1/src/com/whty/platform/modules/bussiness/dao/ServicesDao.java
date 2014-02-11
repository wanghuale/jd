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
import com.whty.platform.modules.bussiness.entity.Services;

/**
 * 服务DAO接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
public interface ServicesDao extends ServicesDaoCustom, CrudRepository<Services, Long> {

	@Modifying
	@Query("update Services set delFlag='" + ServiceRecord.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author qimin
 */
interface ServicesDaoCustom extends BaseDao<Services> {

}

/**
 * DAO自定义接口实现
 * 
 * @author qimin
 */
@Component
class ServicesDaoImpl extends BaseDaoImpl<Services> implements ServicesDaoCustom {

}
