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
import com.whty.platform.modules.bussiness.entity.ProfitSet;

/**
 * 分润 DAO接口
 * 
 * @author 舒海洋
 * @version 2013-10-15
 */
public interface ProfitSetDao extends ProfitSetDaoCustom, CrudRepository<ProfitSet, Long> {

	@Modifying
	@Query("delete ProfitSet  where id = ?1")
	public int deleteById(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author 舒海洋
 */
interface ProfitSetDaoCustom extends BaseDao<ProfitSet> {

}

/**
 * DAO自定义接口实现
 * 
 * @author 舒海洋
 */
@Component
class ProfitSetDaoImpl extends BaseDaoImpl<ProfitSet> implements ProfitSetDaoCustom {

}
