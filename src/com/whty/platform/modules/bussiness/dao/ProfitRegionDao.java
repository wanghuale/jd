/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.bussiness.entity.ProfitRegion;

/**
 * 分润 DAO接口
 * 
 * @author 舒海洋
 * @version 2013-10-15
 */
public interface ProfitRegionDao extends ProfitRegionDaoCustom, CrudRepository<ProfitRegion, Long> {

	@Modifying
	@Query(" delete ProfitRegion  where id = ?1")
	public int deleteById(Long id);

	@Modifying
	@Query(" delete ProfitRegion  where profitSetId = ?1")
	public int deleteByPid(Long id);

}

/**
 * DAO自定义接口
 * 
 * @author 舒海洋
 */
interface ProfitRegionDaoCustom extends BaseDao<ProfitRegion> {
	public List<ProfitRegion> find(Long pid);
}

/**
 * DAO自定义接口实现
 * 
 * @author 舒海洋
 */
@Component
class ProfitRegionDaoImpl extends BaseDaoImpl<ProfitRegion> implements ProfitRegionDaoCustom {
	public List<ProfitRegion> find(Long pid) {
		DetachedCriteria dc = this.createDetachedCriteria();
		dc.add(Restrictions.eq("profitSetId", pid));
		dc.addOrder(Order.asc("id"));
		return this.find(dc);
	}
}
