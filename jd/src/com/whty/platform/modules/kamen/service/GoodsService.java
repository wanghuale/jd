/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.kamen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.whty.platform.modules.kamen.dao.GoodsDao;
import com.whty.platform.modules.kamen.entity.Goods;

/**
 * 商品Service
 * 
 * @author 舒海洋
 * @version 2013-11-22
 */
@Component
@Transactional(readOnly = true)
public class GoodsService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GoodsService.class);

	@Autowired
	private GoodsDao goodsDao;

	public Goods get(Long id) {
		return goodsDao.findOne(id);
	}

	public Page<Goods> find(Page<Goods> page, Goods goods) {
		goodsDao.clear();
		DetachedCriteria dc = goodsDao.createDetachedCriteria();
		if (goods.getGoodsName() != null) {
			dc.add(Restrictions.like("goodsName", "%" + goods.getGoodsName() + "%"));
		}
		if (goods.getGoodsId() != null) {
			dc.add(Restrictions.like("goodsId", "%" + goods.getGoodsId() + "%"));
		}
		if (goods.getGoodsCatalogName() != null) {
			dc.add(Restrictions.like("goodsCatalogName", "%" + goods.getGoodsCatalogName() + "%"));
		}
		if (goods.getStockType() != null) {
			dc.add(Restrictions.like("stockType", "%" + goods.getStockType() + "%"));
		}
		dc.addOrder(Order.desc("goodsCatalogId"));
		return goodsDao.find(page, dc);
	}

	public List<Map<String, Object>> find() {
		List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();
		List<Object> paramList = new ArrayList<Object>();
		String sql = " select goods_type, goods_catalog_id,Goods_catalog_name from  tab_goods group by goods_type,goods_catalog_id,Goods_catalog_name order by goods_type";
		List<Object[]> list = goodsDao.findBySql(sql, paramList.toArray());
		for (Object[] o : list) {
			if (o.length == 3) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fgoodsid", o[0]);
				map.put("goodsid", o[1]);
				map.put("goodsname", o[2]);
				param.add(map);
			}
		}
		return param;
	}

	public Page<Goods> findByRequirement(Page<Goods> page, Goods goods) {
		DetachedCriteria dc = goodsDao.createDetachedCriteria();
		if(StringUtils.isNotEmpty(goods.getGoodsId())){
			dc.add(Restrictions.like("goodsId", "%" + goods.getGoodsId() + "%"));
		}
		if(StringUtils.isNotEmpty(goods.getGoodsName())) {
			dc.add(Restrictions.like("goodsName", "%" + goods.getGoodsName() + "%"));
		}
		if(StringUtils.isNotEmpty(goods.getGoodsType())) {
			dc.add(Restrictions.eq("goodsType",goods.getGoodsType()));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsCatalogName())) {
			dc.add(Restrictions.like("goodsCatalogName", "%" + goods.getGoodsCatalogName() + "%"));
		}
		if (StringUtils.isNotEmpty(goods.getStockType())) {
			dc.add(Restrictions.like("stockType", "%" + goods.getStockType() + "%"));
		}
		dc.addOrder(Order.asc("spreadprice"));
		return goodsDao.find(page, dc);
	}

	public List<Goods> findbylist(Goods goods) {
		DetachedCriteria dc = goodsDao.createDetachedCriteria();
		if (goods.getGoodsName() != null) {
			dc.add(Restrictions.like("goodsName", "%" + goods.getGoodsName() + "%"));
		}
		if (goods.getGoodsCatalogName() != null) {
			dc.add(Restrictions.like("goodsCatalogName", "%" + goods.getGoodsCatalogName() + "%"));
		}
		if (goods.getStockType() != null) {
			dc.add(Restrictions.like("stockType", "%" + goods.getStockType() + "%"));
		}
		return goodsDao.find(dc);
	}

	@Transactional(readOnly = false)
	public void save(Goods goods) {
		goodsDao.save(goods);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		goodsDao.deleteById(id);
	}

}
