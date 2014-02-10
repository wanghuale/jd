package com.whty.platform.modules.front.dao;

/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.front.entity.FrontUser;

/**
 * 服务消费者DAO接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
public interface FrontUserDao extends FrontUserDaoCustom, CrudRepository<FrontUser, Long> {

	@Modifying
	@Query("delete FrontUser  where id = ?1")
	public int deleteById(Long id);

	@Query("from FrontUser where username = ?1 ")
	public FrontUser findByUsername(String username);

}

/**
 * DAO自定义接口
 * 
 * @author qimin
 */
interface FrontUserDaoCustom extends BaseDao<FrontUser> {

}

/**
 * DAO自定义接口实现
 * 
 * @author qimin
 */
@Component
class FrontUserDaoImpl extends BaseDaoImpl<FrontUser> implements FrontUserDaoCustom {

}
