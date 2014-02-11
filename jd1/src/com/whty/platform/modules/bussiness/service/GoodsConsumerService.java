/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.whty.platform.modules.bussiness.dao.GoodsConsumerDao;
import com.whty.platform.modules.bussiness.entity.GoodsConsumer;

/**
 * 对账Service
 * 
 * @author 舒海洋
 * @version 2013-11-22
 */
@Component
@Transactional(readOnly = true)
public class GoodsConsumerService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GoodsConsumerService.class);

	@Autowired
	private GoodsConsumerDao goodsConsumerDao;

	public GoodsConsumer get(Long id) {
		return goodsConsumerDao.findOne(id);
	}

	public Page<GoodsConsumer> find(Page<GoodsConsumer> page, GoodsConsumer goodsConsumer) {
		DetachedCriteria dc = goodsConsumerDao.createDetachedCriteria();
		dc.createAlias("consumer", "consumer");
		dc.createAlias("goods", "goods");
		if (goodsConsumer.getConsumer().getId() != null) {
			dc.add(Restrictions.eq("consumer.id", goodsConsumer.getConsumer().getId()));
		}
		if (goodsConsumer.getGoods() != null) {
			if (StringUtils.isNotBlank(goodsConsumer.getGoods().getGoodsId())) {
				dc.add(Restrictions.eq("goods.goodsId", goodsConsumer.getGoods().getGoodsId()));
			}
			if (StringUtils.isNotBlank(goodsConsumer.getGoods().getGoodsName())) {
				dc.add(Restrictions.like("goods.goodsName", goodsConsumer.getGoods().getGoodsName(), MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(goodsConsumer.getGoods().getGoodsCatalogName())) {
				dc.add(Restrictions.like("goods.goodsCatalogName", goodsConsumer.getGoods().getGoodsCatalogName(), MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(goodsConsumer.getGoods().getGoodsType())) {
				dc.add(Restrictions.eq("goods.goodsType", goodsConsumer.getGoods().getGoodsType()));
			}
		}
		dc.addOrder(Order.asc("spreadprice"));
		dc.addOrder(Order.desc("goods.goodsId"));
		return goodsConsumerDao.find(page, dc);
	}

	public String findGoodIDs(Long consumerid) {
		DetachedCriteria dc = goodsConsumerDao.createDetachedCriteria();
		dc.createAlias("consumer", "consumer");
		dc.add(Restrictions.eq("consumer.id", consumerid));
		List<GoodsConsumer> list = goodsConsumerDao.find(dc);
		String value = "";
		for (GoodsConsumer c : list) {
			if (c.getGoods() != null && c.getGoods().getId() != null) {
				value = value + "," + c.getGoods().getId();
			}
		}
		return value;
	}

	public List<GoodsConsumer> findLists(Long consumerid) {
		DetachedCriteria dc = goodsConsumerDao.createDetachedCriteria();
		dc.createAlias("consumer", "consumer");
		dc.add(Restrictions.eq("consumer.id", consumerid));
		List<GoodsConsumer> list = goodsConsumerDao.find(dc);
		return list;
	}

	/**
	 * 消费者ID 和物品表ID查询消费者记录
	 */
	public GoodsConsumer getGoodsConcumerByGIDandCID(Long gid, Long cid) {
		DetachedCriteria dc = goodsConsumerDao.createDetachedCriteria();
		dc.createAlias("consumer", "consumer");
		dc.createAlias("goods", "goods");
		dc.add(Restrictions.eq("consumer.id", cid));
		dc.add(Restrictions.eq("goods.id", gid));
		dc.addOrder(Order.desc("id"));
		List<GoodsConsumer> list = goodsConsumerDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new GoodsConsumer();
		}
	}

	/**
	 * 消费者ID和物品表编号查询消费记录
	 */
	public boolean getGoodsConcumerByLM(String labmu, Long cid) {
		DetachedCriteria dc = goodsConsumerDao.createDetachedCriteria();
		dc.createAlias("consumer", "consumer");
		dc.createAlias("goods", "goods");
		dc.add(Restrictions.eq("consumer.id", cid));
		dc.add(Restrictions.eq("goods.goodsCatalogId", labmu));
		dc.addOrder(Order.desc("id"));
		List<GoodsConsumer> list = goodsConsumerDao.find(dc);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 消费者ID和物品表编号查询消费记录
	 */
	public GoodsConsumer getGoodsConcumerByGCODEandCID(String gcode, Long cid) {
		DetachedCriteria dc = goodsConsumerDao.createDetachedCriteria();
		dc.createAlias("consumer", "consumer");
		dc.createAlias("goods", "goods");
		dc.add(Restrictions.eq("consumer.id", cid));
		dc.add(Restrictions.eq("goods.goodsId", gcode));
		dc.addOrder(Order.desc("id"));
		List<GoodsConsumer> list = goodsConsumerDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new GoodsConsumer();
		}
	}

	@Transactional(readOnly = false)
	public void save(GoodsConsumer goodsConsumer) {
		goodsConsumerDao.save(goodsConsumer);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		GoodsConsumer goodsConsumer = get(id);
		if (goodsConsumer != null && goodsConsumer.getId() != null) {
			goodsConsumerDao.delete(id);
		}
	}

	@Transactional(readOnly = false)
	public void deleteByConsumerId(Long Consumerid) {
		goodsConsumerDao.deleteByConsumerId(Consumerid);
	}

}
