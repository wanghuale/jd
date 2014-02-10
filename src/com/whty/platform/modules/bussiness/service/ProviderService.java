/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.service.BaseService;
import com.whty.platform.modules.bussiness.dao.OrderRecordDao;
import com.whty.platform.modules.bussiness.dao.ProviderDao;
import com.whty.platform.modules.bussiness.dao.ServicesDao;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;

/**
 * 服务提供方Service
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
@Transactional(readOnly = true)
public class ProviderService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProviderService.class);

	@Autowired
	private ProviderDao providerDao;

	@Autowired
	private OrderRecordDao orderRecordDao;

	@Autowired
	private ServicesDao servicesDao;

	public Provider get(Long id) {
		return providerDao.findOne(id);
	}

	public Page<Provider> find(Page<Provider> page, Provider provider) {
		DetachedCriteria dc = providerDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(provider.getName())) {
			dc.add(Restrictions.like("name", provider.getName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(provider.getCode())) {
			dc.add(Restrictions.like("code", provider.getCode(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(provider.getDescription())) {
			dc.add(Restrictions.like("description", provider.getDescription(), MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.desc("id"));
		return providerDao.find(page, dc);
	}

	public boolean checkdelete(Long providerid) {
		DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
		dc.createAlias("provider", "provider");
		dc.add(Restrictions.eq("provider.id", providerid));
		List<OrderRecord> list1 = orderRecordDao.find(dc);
		if (list1.size() > 0) {
			return false;
		}

		DetachedCriteria dcs = servicesDao.createDetachedCriteria();
		dcs.createAlias("provider", "provider");
		dcs.add(Restrictions.eq("provider.id", providerid));
		List<Services> list2 = servicesDao.find(dcs);
		if (list2.size() > 0) {
			return false;
		}
		return true;
	}

	public List<Provider> find(Provider provider) {
		DetachedCriteria dc = providerDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(provider.getName())) {
			dc.add(Restrictions.like("name", provider.getName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(provider.getName())) {
			dc.add(Restrictions.like("code", provider.getCode(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(provider.getDescription())) {
			dc.add(Restrictions.like("description", provider.getDescription(), MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.desc("id"));
		return providerDao.find(dc);
	}

	@Transactional(readOnly = false)
	public void save(Provider provider) {
		providerDao.save(provider);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		providerDao.deleteById(id);
	}

	public List<Provider> findALL() {
		return providerDao.findALL();
	}

	public Provider findByUsername(String username) {
		return providerDao.findByCode(username);
	}

}
