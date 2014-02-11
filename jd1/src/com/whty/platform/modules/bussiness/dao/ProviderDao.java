/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.bussiness.entity.Provider;

/**
 * 服务提供方DAO接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
public interface ProviderDao extends ProviderDaoCustom, CrudRepository<Provider, Long> {

	@Modifying
	@Query("delete Provider  where id = ?1")
	public int deleteById(Long id);

	@Query("from Provider order by name ")
	public List<Provider> findALL();

	@Query("from Provider where code = ?1 ")
	public Provider findByCode(String code);

}

/**
 * DAO自定义接口
 * 
 * @author qimin
 */
interface ProviderDaoCustom extends BaseDao<Provider> {

}

/**
 * DAO自定义接口实现
 * 
 * @author qimin
 */
@Component
class ProviderDaoImpl extends BaseDaoImpl<Provider> implements ProviderDaoCustom {

}
