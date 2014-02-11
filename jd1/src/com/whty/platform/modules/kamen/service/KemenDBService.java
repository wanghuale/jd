/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.kamen.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.service.BaseService;
import com.whty.platform.common.utils.PropertiesLoader;
import com.whty.platform.modules.kamen.Exception.KamenException;
import com.whty.platform.modules.kamen.dao.GoodsInfoDao;
import com.whty.platform.modules.kamen.entity.Goods;
import com.whty.platform.modules.kamen.entity.GoodsCatalogInfo;
import com.whty.platform.modules.kamen.entity.GoodsInfo;
import com.whty.platform.modules.kamen.entity.KamenUser;

/**
 * 卡门接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Service
@Transactional(readOnly = true)
public class KemenDBService extends BaseService implements InitializingBean {

	@Autowired
	private GoodsInfoDao goodsInfoDao;

	@Autowired
	private KemenService kemenService;

	@Transactional(readOnly = false)
	public void saveGoods() throws KamenException {
		// 设置代理
		String proxySet = PropertiesLoader.getPValue("proxySet");
		if ("true".equals(proxySet)) {
			String proxyHost = PropertiesLoader.getPValue("http.proxyHost");
			String proxyPort = PropertiesLoader.getPValue("http.proxyPort");
			System.getProperties().setProperty("proxySet", proxySet); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			System.getProperties().setProperty("http.proxyHost", proxyHost);
			System.getProperties().setProperty("http.proxyPort", proxyPort);
		}
		for (GoodsCatalogInfo in : kemenService.getGoodsCatalogs(new KamenUser())) {
			for (GoodsInfo ins : kemenService.getGoodsInfos(new KamenUser(), in.getGoodsCatalogID())) {
				Goods g = this.findGoods(ins.getGoodsID());
				g.setGoodsCatalogId(in.getGoodsCatalogID());
				g.setGoodsCatalogName(in.getGoodsCatalogName());
				g.setGoodsId(ins.getGoodsID());
				g.setGoodsName(ins.getGoodsName());
				g.setGoodsParvalue(ins.getGoodsParvalue());
				g.setGoodsType(in.getGoodsNum());
				if (g.getId() == null) {
					g.setSellPrice(ins.getSellPrice());// SP的销售价就是 其他消费者的采购价
				}
				g.setPurchasePrice(ins.getSellPrice());// 卡门的销售价就是SP的采购价
				g.setBanBuyTime(ins.getBanBuyTime());
				g.setTemplateGuid(ins.getTemplateGuid());
				g.setStockType(ins.getStockType());
				g.setIsSell(ins.getIsSell());
				g.setSelectNum(ins.getSelectNum());
				double spreadprice=Double.parseDouble(g.getSellPrice())-Double.parseDouble(g.getPurchasePrice());//销售价格-进价
				//System.out.println(spreadprice);
				
				g.setSpreadprice(spreadprice);
				goodsInfoDao.save(g);
			}
		}
	}

	public static void main(String[] args) throws KamenException {
		System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
		System.getProperties().setProperty("http.proxyPort", "606");
		KemenDBService s = new KemenDBService();
		s.saveGoods();
		// RequestCharge params = new RequestCharge();
		// params.setCustomerOrderNo("21031029000002");
		// params.setProductId("888702");
		// params.setBuyNum("1");
		// params.setChargeAccount("278016391");
		// params.setChargePassword("");
		// params.setChargeGame("");
		// params.setChargeRegion("");
		// params.setChargeServer("");
		// params.setChargeType("");
		// params.setNotifyUrl("http://baidu.com");
		// System.out.println(s.charge(new KamenUser(), params).getOrderNo());
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	public Goods findGoods(String goodsid) {
		DetachedCriteria dc = goodsInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("goodsId", goodsid));
		List<Goods> list = goodsInfoDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Goods();
		}
	}

}
