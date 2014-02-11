/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.service.BaseService;
import com.whty.platform.modules.bussiness.dao.ProfitRegionDao;
import com.whty.platform.modules.bussiness.entity.ProfitRegion;

/**
 * 分润 Service
 * @author 舒海洋
 * @version 2013-10-15
 */
@Component
@Transactional(readOnly = true)
public class ProfitRegionService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProfitRegionService.class);
	
	@Autowired
	private ProfitRegionDao profitRegionDao;
	
	public ProfitRegion get(Long id) {
		return profitRegionDao.findOne(id);
	}
	
	public Page<ProfitRegion> find(Page<ProfitRegion> page, ProfitRegion profitRegion) {
		DetachedCriteria dc = profitRegionDao.createDetachedCriteria();
		
		dc.add(Restrictions.eq(ProfitRegion.DEL_FLAG, ProfitRegion.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return profitRegionDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProfitRegion profitRegion) {
		profitRegionDao.save(profitRegion);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		profitRegionDao.deleteById(id);
	}
	
}
