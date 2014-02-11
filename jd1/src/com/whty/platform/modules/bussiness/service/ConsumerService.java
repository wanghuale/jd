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
import com.whty.platform.modules.bussiness.dao.ConsumerDao;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.sys.entity.User;

/**
 * 服务消费者Service
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
@Transactional(readOnly = true)
public class ConsumerService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private ConsumerDao consumerDao;

	public Consumer get(Long id) {
		return consumerDao.findOne(id);
	}

	public Consumer getConsumerByIP(String ip) {
		DetachedCriteria dc = consumerDao.createDetachedCriteria();
		dc.add(Restrictions.eq("ip", ip));
		dc.add(Restrictions.eq(User.DEL_FLAG, User.DEL_FLAG_NORMAL));
		List<Consumer> list = consumerDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Consumer();
		}

	}

	public Consumer getConsumerByName(String username) {
		DetachedCriteria dc = consumerDao.createDetachedCriteria();
		dc.add(Restrictions.eq("username", username));
		dc.add(Restrictions.eq(User.DEL_FLAG, User.DEL_FLAG_NORMAL));
		List<Consumer> list = consumerDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Consumer();
		}

	}

	public Page<Consumer> find(Page<Consumer> page, Consumer consumer) {
		DetachedCriteria dc = consumerDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(consumer.getName())) {
			dc.add(Restrictions.like("name", consumer.getName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(consumer.getCode())) {
			dc.add(Restrictions.like("code", consumer.getCode(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(consumer.getUsername())) {
			dc.add(Restrictions.like("username", consumer.getUsername(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(consumer.getDescription())) {
			dc.add(Restrictions.like("description", consumer.getDescription(), MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq(User.DEL_FLAG, User.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return consumerDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Consumer consumer) {
		consumerDao.save(consumer);
		consumerDao.flush();
		consumerDao.clear();
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		consumerDao.deleteById(id);
	}

	public Consumer findByUsername(String username) {
		return consumerDao.findByUsername(username);
	}

	public List<Consumer> findALL() {
		return consumerDao.findALL();
	}

}
