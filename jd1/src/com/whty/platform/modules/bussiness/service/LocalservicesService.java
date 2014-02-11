/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

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
import com.whty.platform.modules.bussiness.entity.Localservices;
import com.whty.platform.modules.bussiness.dao.LocalservicesDao;

/**
 * 本地服务Service
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
@Transactional(readOnly = true)
public class LocalservicesService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(LocalservicesService.class);

	@Autowired
	private LocalservicesDao localservicesDao;

	public Localservices get(Long id) {
		return localservicesDao.findOne(id);
	}

	public Page<Localservices> find(Page<Localservices> page, Localservices localservices) {
		DetachedCriteria dc = localservicesDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(localservices.getName())) {
			dc.add(Restrictions.like("name", "%" + localservices.getName() + "%"));
		}
		if (StringUtils.isNotEmpty(localservices.getCode())) {
			dc.add(Restrictions.like("code", "%" + localservices.getCode() + "%"));
		}
		if (StringUtils.isNotEmpty(localservices.getDescription())) {
			dc.add(Restrictions.like("description", "%" + localservices.getDescription() + "%"));
		}
		dc.addOrder(Order.desc("id"));
		return localservicesDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Localservices localservices) {
		localservicesDao.save(localservices);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		localservicesDao.deleteById(id);
	}

}
