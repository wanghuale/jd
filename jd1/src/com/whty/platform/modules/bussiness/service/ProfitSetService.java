/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.whty.platform.modules.bussiness.dao.ProfitSetDao;
import com.whty.platform.modules.bussiness.entity.ProfitRegion;
import com.whty.platform.modules.bussiness.entity.ProfitSet;

/**
 * 分润 Service
 * 
 * @author 舒海洋
 * @version 2013-10-15
 */
@Component
@Transactional(readOnly = true)
public class ProfitSetService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProfitSetService.class);

	@Autowired
	private ProfitSetDao profitSetDao;

	@Autowired
	private ProfitRegionDao profitRegionDao;

	public ProfitSet get(Long id) {
		return profitSetDao.findOne(id);
	}

	public List<ProfitRegion> getRegions(Long id) {
		return profitRegionDao.find(id);
	}

	public Page<ProfitSet> find(Page<ProfitSet> page, ProfitSet profitSet) {
		DetachedCriteria dc = profitSetDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(profitSet.getBusinessType())) {
			dc.add(Restrictions.like("businessType", profitSet.getBusinessType()));
		}
		if (profitSet.getDistriType() != null) {
			dc.add(Restrictions.like("distriType", profitSet.getDistriType()));
		}
		dc.addOrder(Order.desc("id"));
		return profitSetDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(ProfitSet profitSet) {
		ProfitSet e = profitSetDao.save(profitSet);
		profitRegionDao.deleteByPid(e.getId());
		if (1 == e.getDistriType()) {
			for (ProfitRegion s : profitSet.getProfitRegions()) {
				s.setProfitSetId(e.getId());
			}
			profitRegionDao.save(profitSet.getProfitRegions());
		}
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		profitSetDao.deleteById(id);
		profitRegionDao.deleteByPid(id);
	}

	public ProfitSet getProfitSetByBusinessType(String BusinessType) {
		List<ProfitSet> list = new ArrayList<ProfitSet>();
		DetachedCriteria dc = profitSetDao.createDetachedCriteria();
		dc.add(Restrictions.like("businessType", BusinessType));
		list = profitSetDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

}
