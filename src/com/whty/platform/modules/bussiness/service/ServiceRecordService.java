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
import com.whty.platform.modules.bussiness.dao.ServiceRecordDao;
import com.whty.platform.modules.bussiness.entity.ServiceRecord;

/**
 * 服务Service
 * 
 * @author qimin
 * @version 2013-07-16
 */
@Component
@Transactional(readOnly = true)
public class ServiceRecordService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ServiceRecordService.class);

	@Autowired
	private ServiceRecordDao serviceRecordDao;

	public ServiceRecord get(Long id) {
		return serviceRecordDao.findOne(id);
	}

	public Page<ServiceRecord> find(Page<ServiceRecord> page, ServiceRecord serviceRecord) {
		DetachedCriteria dc = serviceRecordDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(serviceRecord.getUsername())) {
			dc.add(Restrictions.like("username", "%" + serviceRecord.getUsername() + "%"));
		}
		if (StringUtils.isNotEmpty(serviceRecord.getIp())) {
			dc.add(Restrictions.like("ip", "%" + serviceRecord.getIp() + "%"));
		}
		dc.add(Restrictions.eq(ServiceRecord.DEL_FLAG, ServiceRecord.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return serviceRecordDao.find(page, dc);
	}

	public ServiceRecord findServiceRecordByIp(Long serviceid, String ip) {
		List<Object> param = new ArrayList<Object>();
		String sql = "select sr.* from tab_service_record sr where sr.ip=? and sr.do_service=? and  re_service='SELECT_PRODUCT' order by create_date desc LIMIT 1";
		param.add(ip);
		param.add(serviceid);
		List<ServiceRecord> list = serviceRecordDao.findBySql(sql, ServiceRecord.class, param.toArray());
		if (list.size() > 0) {
			return list.get(0);
		}
		return new ServiceRecord();
	}

	@Transactional(readOnly = false)
	public void save(ServiceRecord serviceRecord) {
		serviceRecordDao.save(serviceRecord);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		serviceRecordDao.deleteById(id);
	}

}
