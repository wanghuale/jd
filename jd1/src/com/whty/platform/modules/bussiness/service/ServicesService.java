/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

import java.util.ArrayList;
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
import com.whty.platform.modules.bussiness.dao.ServicesDao;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.sys.entity.User;

/**
 * 服务Service
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
@Transactional(readOnly = true)
public class ServicesService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ServicesService.class);

	@Autowired
	private ServicesDao servicesDao;

	public Services get(Long id) {
		return servicesDao.findOne(id);
	}

	public Services getByCode(String code) {
		DetachedCriteria dc = servicesDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(code)) {
			dc.add(Restrictions.eq("code", code));
		} else {
			return null;
		}
		dc.add(Restrictions.eq(User.DEL_FLAG, User.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		List<Services> list = servicesDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Page<Services> find(Page<Services> page, Services services) {
		DetachedCriteria dc = servicesDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(services.getName())) {
			dc.add(Restrictions.like("name", services.getName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(services.getDescription())) {
			dc.add(Restrictions.like("description", services.getDescription(), MatchMode.ANYWHERE));
		}
		if (services.getProvider() != null && services.getProvider().getId() != null && services.getProvider().getId() > 0) {
			dc.createAlias("provider", "provider").add(Restrictions.eq("provider.id", services.getProvider().getId()));
		}
		dc.add(Restrictions.eq(User.DEL_FLAG, User.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("bussinessType"));
		return servicesDao.find(page, dc);
	}

	public List<Services> find(Services services) {
		DetachedCriteria dc = servicesDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(services.getName())) {
			dc.add(Restrictions.like("name", services.getName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(services.getDescription())) {
			dc.add(Restrictions.like("description", services.getDescription(), MatchMode.ANYWHERE));
		}
		if (services.getProvider() != null && services.getProvider().getId() != null && services.getProvider().getId() > 0) {
			dc.createAlias("provider", "provider").add(Restrictions.eq("provider.id", services.getProvider().getId()));
		}
		dc.add(Restrictions.eq(User.DEL_FLAG, User.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return servicesDao.find(dc);
	}

	public List<Services> findServicesByIp(String btype, String ip) {
		List<Object> param = new ArrayList<Object>();
		String sql = "select ts.* from tab_services ts where ts.id in (select tsc.service_id from tab_consumer tc, tab_service_consumer tsc where tc.id=tsc.consumer_id and tc.ip = ?)";
		param.add(ip);
		sql = sql + " and del_flag = " + User.DEL_FLAG_NORMAL + "  and ts.bussiness_type = ? ";
		param.add(btype);
		List<Services> list = servicesDao.findBySql(sql, Services.class, param.toArray());
		return list;
	}

	public List<Services> findServicesByUsername(String btype, String username) {
		List<Object> param = new ArrayList<Object>();
		String sql = "select ts.* from tab_services ts where ts.id in (select tsc.service_id from tab_consumer tc, tab_service_consumer tsc where tc.id=tsc.consumer_id and tc.username = ?)";
		param.add(username);
		sql = sql + " and del_flag = " + User.DEL_FLAG_NORMAL + " and ts.bussiness_type = ? ";
		param.add(btype);
		List<Services> list = servicesDao.findBySql(sql, Services.class, param.toArray());
		return list;
	}

	@Transactional(readOnly = false)
	public void saveServices(Services services) {
		servicesDao.clear();
		servicesDao.save(services);
	}

	@Transactional(readOnly = false)
	public void save(Services services) {
		servicesDao.save(services);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		servicesDao.deleteById(id);
	}

}
