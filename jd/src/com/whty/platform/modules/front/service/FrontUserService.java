/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.service.BaseService;
import com.whty.platform.modules.front.dao.FrontUserDao;
import com.whty.platform.modules.front.entity.FrontUser;

/**
 * 服务消费者Service
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
@Transactional(readOnly = true)
public class FrontUserService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(FrontUserService.class);

	@Autowired
	private FrontUserDao frontUserDao;

	public FrontUser get(Long id) {
		return frontUserDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void save(FrontUser consumer) {
		frontUserDao.save(consumer);
		frontUserDao.flush();
		frontUserDao.clear();
	}

}
