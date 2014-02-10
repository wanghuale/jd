/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.service.BaseService;
import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.modules.bussiness.dao.OrderRecordDao;
import com.whty.platform.modules.bussiness.entity.OrderRecord;

/**
 * 订单Service
 * 
 * @author qimin
 * @version 2013-08-13
 */
@Component
@Transactional(readOnly = true)
public class OrderRecordService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(OrderRecordService.class);

	@Autowired
	private OrderRecordDao orderRecordDao;

	public OrderRecord get(Long id) {
		return orderRecordDao.findOne(id);
	}

	public OrderRecord getByOrderID(String payOrderId) {
		DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(payOrderId)) {
			dc.add(Restrictions.eq("orderId", payOrderId));
			dc.addOrder(Order.desc("createDate"));
			List<OrderRecord> list = orderRecordDao.find(dc);
			if (list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

	public OrderRecord getByOrderID(String payOrderID, Long ConsumerID) {
		DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
		dc.add(Restrictions.eq("orderId", payOrderID));
		dc.createAlias("consumer", "consumer", JoinType.LEFT_OUTER_JOIN);
		dc.add(Restrictions.eq("consumer.id", ConsumerID));
		dc.addOrder(Order.desc("createDate"));
		List<OrderRecord> list = orderRecordDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Page<OrderRecord> find(Page<OrderRecord> page, OrderRecord orderRecord) {
		DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
		if (orderRecord != null) {
			if (orderRecord.getConsumer() != null) {
				dc.createAlias("consumer", "consumer", JoinType.LEFT_OUTER_JOIN);
				if (orderRecord.getConsumer().getId() != null) {
					dc.add(Restrictions.eq("consumer.id", orderRecord.getConsumer().getId()));
				}
				if (StringUtils.isNotBlank(orderRecord.getConsumer().getName())) {
					dc.add(Restrictions.like("consumer.name", orderRecord.getConsumer().getName(), MatchMode.ANYWHERE));
				}
			}
			if (orderRecord.getProvider() != null) {
				dc.createAlias("provider", "provider", JoinType.LEFT_OUTER_JOIN);
				if (StringUtils.isNotBlank(orderRecord.getProvider().getName())) {
					dc.add(Restrictions.eq("provider.id", orderRecord.getProvider().getId()));
				}
				if (StringUtils.isNotBlank(orderRecord.getConsumer().getName())) {
					dc.add(Restrictions.like("provider.name", orderRecord.getProvider().getName(), MatchMode.ANYWHERE));
				}
			}
			if (StringUtils.isNotEmpty(orderRecord.getOrderId())) {
				dc.add(Restrictions.eq("orderId", orderRecord.getOrderId()));
			}
			if (StringUtils.isNotEmpty(orderRecord.getPayOrderId())) {
				dc.add(Restrictions.eq("payOrderId", orderRecord.getPayOrderId()));
			}
		}
		dc.addOrder(Order.desc("createDate"));
		return orderRecordDao.find(page, dc);
	}

	public Page<OrderRecord> find(String did,Page<OrderRecord> page, OrderRecord orderRecord, Map<String, Object> paramMap) {
		DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
		if (orderRecord != null) {
			//平台用户
			if (orderRecord.getConsumer() != null) {
				dc.createAlias("consumer", "consumer", JoinType.LEFT_OUTER_JOIN);
				if (orderRecord.getConsumer().getId() != null) {
					dc.add(Restrictions.eq("consumer.id", orderRecord.getConsumer().getId()));
				}
				if (StringUtils.isNotBlank(orderRecord.getConsumer().getName())) {
					dc.add(Restrictions.like("consumer.name", orderRecord.getConsumer().getName(), MatchMode.ANYWHERE));
				}
			}
			//供应者
			if (orderRecord.getProvider() != null) {
				dc.createAlias("provider", "provider", JoinType.LEFT_OUTER_JOIN);
			
				if (StringUtils.isNotBlank(orderRecord.getProvider().getName())) {
					dc.add(Restrictions.like("provider.name", orderRecord.getProvider().getName(), MatchMode.ANYWHERE));
				}
			}
			
		
			//本站订单号
			if(StringUtils.isNotEmpty(did)){
				dc.add(Restrictions.eq("id", Long.parseLong(did)));
			}
			//服务名称或服务ID号
			if (orderRecord.getService() != null) {
				dc.createAlias("service", "service", JoinType.LEFT_OUTER_JOIN);
				
				if (StringUtils.isNotBlank(orderRecord.getService().getName())) {
					dc.add(Restrictions.like("service.name", orderRecord.getService().getName(), MatchMode.ANYWHERE));
				}
				if (StringUtils.isNotBlank(orderRecord.getService().getBussinessType())) {
					dc.add(Restrictions.eq("service.bussinessType", orderRecord.getService().getBussinessType()));
				}
			}
			//产品编号
			if (StringUtils.isNotEmpty(orderRecord.getProductid())) {
				dc.add(Restrictions.like("productid", orderRecord.getProductid(),MatchMode.ANYWHERE));
			}
			//产品名称
			if (StringUtils.isNotEmpty(orderRecord.getProductname())) {
				dc.add(Restrictions.like("productname", orderRecord.getProductname(),MatchMode.ANYWHERE));
			}
			//顾客信息
			if (StringUtils.isNotEmpty(orderRecord.getDispaly())) {
				dc.add(Restrictions.like("dispaly", orderRecord.getDispaly(),MatchMode.ANYWHERE));
			}
			//数量
			if (orderRecord.getNums()!=null) {
				dc.add(Restrictions.eq("nums", orderRecord.getNums()));
			}
			//面值
			if (orderRecord.getGoodsparvalue()!=null) {
				dc.add(Restrictions.eq("goodsparvalue", orderRecord.getGoodsparvalue()));
			}
			//支付金额
			if (orderRecord.getAmount()!=null) {
				dc.add(Restrictions.eq("amount", orderRecord.getAmount()));
			}
			//售价
			if (orderRecord.getGoodsprice()!=null) {
				dc.add(Restrictions.eq("goodsprice", orderRecord.getGoodsprice()));
			}
			//进价
			if (orderRecord.getPurchaserprice()!=null) {
				dc.add(Restrictions.eq("purchaserprice", orderRecord.getPurchaserprice()));
			}
			//状态
			if (StringUtils.isNotEmpty(orderRecord.getStatus())) {
				dc.add(Restrictions.eq("status", orderRecord.getStatus()));
			}
			//时间
			Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
			if (beginDate == null) {
				beginDate = DateUtils.setDays(new Date(), 1);
				paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00"));
			}
			Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
			if (endDate == null) {
				endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
				paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59"));
			}
			dc.add(Restrictions.between("createDate", beginDate, endDate));

			if (StringUtils.isNotEmpty(orderRecord.getOrderId())) {
				dc.add(Restrictions.like("orderId", orderRecord.getOrderId(),MatchMode.ANYWHERE));
			}
			//外部订单号
			if (StringUtils.isNotEmpty(orderRecord.getPayOrderId())) {
				dc.add(Restrictions.like("payOrderId", "%"+orderRecord.getPayOrderId()+"%"));
			}
		}
		dc.addOrder(Order.desc("createDate"));
		return orderRecordDao.find(page, dc);
	}

	public List<OrderRecord> findcall(OrderRecord orderRecord) {
		DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
		if (orderRecord != null) {
			if (orderRecord.getConsumer() != null) {
				dc.createAlias("consumer", "consumer", JoinType.LEFT_OUTER_JOIN);
				if (orderRecord.getConsumer().getId() != null) {
					dc.add(Restrictions.eq("consumer.id", orderRecord.getConsumer().getId()));
				}
				if (StringUtils.isNotBlank(orderRecord.getConsumer().getName())) {
					dc.add(Restrictions.like("consumer.name", orderRecord.getConsumer().getName(), MatchMode.ANYWHERE));
				}
			}
			if (orderRecord.getProvider() != null) {
				dc.createAlias("provider", "provider", JoinType.LEFT_OUTER_JOIN);
				if (StringUtils.isNotBlank(orderRecord.getProvider().getName())) {
					dc.add(Restrictions.eq("provider.id", orderRecord.getProvider().getId()));
				}
				if (StringUtils.isNotBlank(orderRecord.getConsumer().getName())) {
					dc.add(Restrictions.like("provider.name", orderRecord.getProvider().getName(), MatchMode.ANYWHERE));
				}
			}
			if (StringUtils.isNotEmpty(orderRecord.getOrderId())) {
				dc.add(Restrictions.eq("orderId", orderRecord.getOrderId()));
			}
			if (StringUtils.isNotEmpty(orderRecord.getPayOrderId())) {
				dc.add(Restrictions.eq("payOrderId", orderRecord.getPayOrderId()));
			}
		}
		dc.addOrder(Order.desc("createDate"));
		return orderRecordDao.find(dc);
	}

	@Transactional(readOnly = false)
	public void save(OrderRecord orderRecord) {
		orderRecordDao.save(orderRecord);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		orderRecordDao.deleteById(id);
	}

}
