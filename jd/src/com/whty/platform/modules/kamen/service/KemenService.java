/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.kamen.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.common.utils.XMLAdapter;
import com.whty.platform.common.utils.XMLFileUtil;
import com.whty.platform.modules.kamen.Exception.KamenException;
import com.whty.platform.modules.kamen.entity.Charge;
import com.whty.platform.modules.kamen.entity.GoodsCatalogInfo;
import com.whty.platform.modules.kamen.entity.GoodsInfo;
import com.whty.platform.modules.kamen.entity.KamenUser;
import com.whty.platform.modules.kamen.entity.OrderInfo;
import com.whty.platform.modules.kamen.entity.Query;
import com.whty.platform.modules.kamen.entity.RequestCharge;

/**
 * 卡门接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
public class KemenService {

	private String URL = "http://api3.kamennet.com";
	private static Logger logger = LoggerFactory.getLogger(KemenService.class);

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * 下单接口
	 * 
	 * @return
	 * @throws KamenException
	 */
	public Charge charge(KamenUser kamen, RequestCharge params) throws KamenException {
		String interFaceUrl = URL + "/api/Order/Charge.aspx";
		kamen.setCustomerID_name("CustomerId");
		kamen.addParam("CustomerOrderNo", params.getCustomerOrderNo());
		kamen.addParam("ProductId", params.getProductId());
		kamen.addParam("BuyNum", params.getBuyNum());
		kamen.addParam("ChargeAccount", XMLFileUtil.encode(params.getChargeAccount()));
		kamen.addParam("ChargePassword", params.getChargePassword());
		kamen.addParam("ChargeGame", XMLFileUtil.encode(params.getChargeGame()));
		kamen.addParam("ChargeRegion", XMLFileUtil.encode(params.getChargeRegion()));
		kamen.addParam("ChargeServer", XMLFileUtil.encode(params.getChargeServer()));
		kamen.addParam("ChargeType", XMLFileUtil.encode(params.getChargeType()));
		kamen.addParam("NotifyUrl", XMLFileUtil.encode(params.getNotifyUrl()));
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		return charge;
	}

	/**
	 * 话费商品下单接口
	 * 
	 * @param CustomerId
	 *            云接口用户
	 * @param CustomerOrderNo
	 *            客户外部系统订单号
	 * @param ChargePhone
	 *            充值手机号码
	 * @param ChargeParValue
	 *            充值面值
	 * @param NotifyUrl
	 *            客户外部系统用户接收充值结果的地址
	 * @return
	 * @throws KamenException
	 */
	public Charge chargePhone(KamenUser kamen, String CustomerOrderNo, String ChargePhone, String ChargeParValue,
			String NotifyUrl) throws KamenException {
		String interFaceUrl = URL + "/api/Order/ChargePhone.aspx";
		kamen.setCustomerID_name("CustomerId");
		kamen.addParam("CustomerOrderNo", CustomerOrderNo);
		kamen.addParam("ChargePhone", ChargePhone);
		kamen.addParam("ChargeParValue", ChargeParValue);
		kamen.addParam("NotifyUrl", NotifyUrl);
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		return charge;
	}

	/**
	 * 此接口供外部系统查询充值结果用。
	 * 
	 * @param CustomerId
	 *            是 客户ID
	 * @param CustomerOrderNo
	 *            是 客户外部系统订单号
	 * @return
	 * @throws KamenException
	 */
	public Query getQueryNew(KamenUser kamen, String CustomerOrderNo) throws KamenException {
		String interFaceUrl = URL + "/api/Order/Query_new.aspx";
		kamen.setCustomerID_name("CustomerId");
		kamen.addParam("CustomerOrderNo", CustomerOrderNo);
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		System.out.println(xml);
		Query query = XMLFileUtil.getDate(xml, Query.class, new XMLAdapter<Query>() {
			public void rowdata(String key, String value, Query o) {
				if ("CustomerOrderNo".equals(key)) {
					o.setCustomerOrderNo(value);
				} else if ("OrderNo".equals(key)) {
					o.setOrderNo(value);
				} else if ("OrderStatus".equals(key)) {
					o.setOrderStatus(value);
				} else if ("Description".equals(key)) {
					o.setDescription(value);
				} else if ("ErrorCode".equals(key)) {
					o.setErrorCode(value);
				} else if ("ErrorMsg".equals(key)) {
					o.setErrorMsg(value);
				} else if ("ProductId".equals(key)) {
					o.setProductId(value);
				} else if ("ProductName".equals(key)) {
					o.setProductName(value);
				}
			}
		});
		return query;
	}

	/**
	 * 此接口供外部系统查询充值结果用。
	 * 
	 * @throws KamenException
	 */
	public OrderInfo getOrderInfo(KamenUser kamen, String CustomerOrderNo) throws KamenException {
		String interFaceUrl = URL + "/api/Order/Query_new.aspx";
		kamen.setCustomerID_name("CustomerId");
		kamen.addParam("CustomerOrderNo", CustomerOrderNo);
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		OrderInfo order = XMLFileUtil.getDate(xml, OrderInfo.class, new XMLAdapter<OrderInfo>() {
			public void rowdata(String key, String value, OrderInfo o) {
				if ("CustomerOrderNo".equals(key)) {
					o.setCustomerOrderNo(value);
				} else if ("goodsId".equals(key)) {
					o.setGoodsId(value);
				} else if ("GoodsName".equals(key)) {
					o.setGoodsName(value);
				}
			}
		});
		return order;
	}

	/**
	 * 查询商品类目
	 * 
	 * @param CustomerId
	 *            string 是 客户ID
	 * @param GoodsCatalogID
	 *            string 是 商品类目ID
	 * @return
	 * @throws KamenException
	 */
	public List<GoodsCatalogInfo> getGoodsCatalogs(KamenUser kamen) throws KamenException {
		String interFaceUrl = URL + "/api/GoodsInterface/GoodsCatalog.aspx";
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		System.out.println(xml);
		List<GoodsCatalogInfo> list = XMLFileUtil.getDate(xml, "GoodsCatalogInfo", GoodsCatalogInfo.class,
				new XMLAdapter<GoodsCatalogInfo>() {
					public void rowdata(String key, String value, GoodsCatalogInfo o) {
						if ("GoodsCatalogID".equals(key)) {
							o.setGoodsCatalogID(value);
						} else if ("GoodsCatalogName".equals(key)) {
							o.setGoodsCatalogName(value);
						} else if ("GoodsNum".equals(key)) {
							o.setGoodsNum(value);
						}
					}
				});
		return list;
	}

	/**
	 * 查询商品信息
	 * 
	 * @return
	 * @throws KamenException
	 */
	public List<GoodsInfo> getGoodsInfos(KamenUser kamen, String GoodsCatalogID) throws KamenException {
		String interFaceUrl = URL + "/api/GoodsInterface/GoodsInfo.aspx";
		kamen.addParam("GoodsCatalogID", GoodsCatalogID);
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		List<GoodsInfo> list = XMLFileUtil.getDate(xml, "GoodsInfo", GoodsInfo.class, new XMLAdapter<GoodsInfo>() {
			public void rowdata(String key, String value, GoodsInfo o) {
				if ("GoodsID".equals(key)) {
					o.setGoodsID(value);
				} else if ("GoodsName".equals(key)) {
					o.setGoodsName(value);
				} else if ("GoodsParvalue".equals(key)) {
					o.setGoodsParvalue(value);
				} else if ("SellPrice".equals(key)) {
					o.setSellPrice(value);
				} else if ("PurchasePrice".equals(key)) {
					o.setPurchasePrice(value);
				} else if ("BanBuyTime".equals(key)) {
					o.setBanBuyTime(value);
				} else if ("TemplateGuid".equals(key)) {
					o.setTemplateGuid(value);
				} else if ("StockType".equals(key)) {
					o.setStockType(value);
				} else if ("IsSell".equals(key)) {
					o.setIsSell(value);
				} else if ("SelectNum".equals(key)) {
					o.setSelectNum(value);
				} else if ("ErrorCode".equals(key)) {
					o.setErrorCode(value);
				} else if ("ErrorMsg".equals(key)) {
					o.setErrorMsg(value);
				}
			}
		});
		return list;
	}

	/**
	 * 查询商品信息
	 * 
	 * @return
	 * @throws KamenException
	 */
	public String getTemplate(KamenUser kamen, String templateguid) throws KamenException {
		String interFaceUrl = "http://ccapi.kabaling.com/Interface/Method";
		kamen.setSignmodel(true);
		kamen.setCustomerID_name("customerid");
		kamen.addParam("format", "xml");
		kamen.addParam("method", "kamenwang.goods.template.get");
		kamen.addParam("templateguid", templateguid);
		kamen.addParam("timestamp", DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		kamen.addParam("v", "1.0");
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		return xml;
	}

	/**
	 * 订单查询接口
	 * 
	 * @param CustomerId
	 * @param StartTime
	 * @param EndTime
	 * @return
	 * @throws KamenException
	 */
	public List<OrderInfo> getGetOrderCustomers(KamenUser kamen, String StartTime, String EndTime)
			throws KamenException {
		String interFaceUrl = URL + "/api/order/GetOrder_Customer.aspx";
		kamen.setCustomerID_name("CustomerId");
		kamen.addParam("StartTime", StartTime);
		kamen.addParam("EndTime", EndTime);
		String xml = this.find(interFaceUrl, kamen.getParams());
		Charge charge = this.getCargeByXML(xml);
		if (StringUtils.isNotBlank(charge.getErrorCode())) {
			throw new KamenException(charge);
		}
		List<OrderInfo> list = XMLFileUtil.getDate(xml, "OrderInfo", OrderInfo.class, new XMLAdapter<OrderInfo>() {
			public void rowdata(String key, String value, OrderInfo o) {
				if ("OrderId".equals(key)) {
					o.setOrderId(value);
				} else if ("CustomerOrderNo".equals(key)) {
					o.setCustomerOrderNo(value);
				} else if ("GoodsId".equals(key)) {
					o.setGoodsId(value);
				} else if ("ChargeNum".equals(key)) {
					o.setChargeNum(value);
				} else if ("ChargeAccount".equals(key)) {
					o.setChargeAccount(value);
				} else if ("ChargePrice".equals(key)) {
					o.setChargePrice(value);
				} else if ("ChargeTime".equals(key)) {
					o.setChargeTime(value);
				} else if ("OrderStatus".equals(key)) {
					o.setOrderStatus(value);
				}
			}
		});
		return list;
	}

	public GoodsInfo getGoodsInfo(String productid) throws KamenException {
		KemenService s = new KemenService();
		for (GoodsCatalogInfo in : s.getGoodsCatalogs(new KamenUser())) {
			for (GoodsInfo info : s.getGoodsInfos(new KamenUser(), in.getGoodsCatalogID())) {
				if (info.getGoodsID().equals(productid)) {
					return info;
				}
			}
		}
		return null;
	}

	public String find(String interFaceUrl, Map<String, String> map) {
		String restr = "";
		try {
			logger.debug("URL:" + interFaceUrl);
			logger.debug("PARAMS:" + map.toString());
			HttpURL http = new HttpURL();
			restr = http.doPost(interFaceUrl, map, false);
			System.out.println(restr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restr;
	}

	public Charge getCargeByXML(String xml) {
		Charge charge = XMLFileUtil.getDate(xml, Charge.class, new XMLAdapter<Charge>() {
			public void rowdata(String key, String value, Charge o) {
				if ("CustomerOrderNo".equals(key)) {
					o.setCustomerOrderNo(value);
				} else if ("OrderNo".equals(key)) {
					o.setOrderNo(value);
				} else if ("Status".equals(key)) {
					o.setStatus(value);
				} else if ("ErrorCode".equals(key)) {
					o.setErrorCode(value);
				} else if ("ErrorMsg".equals(key)) {
					o.setErrorMsg(value);
				}
			}
		});
		return charge;
	}

	public void getCargeByXML() throws KamenException {
		for (GoodsCatalogInfo in : getGoodsCatalogs(new KamenUser())) {
			for (GoodsInfo ins : getGoodsInfos(new KamenUser(), in.getGoodsCatalogID())) {

				System.out.println(in.getGoodsCatalogID());
				System.out.println(in.getGoodsCatalogName());
				System.out.println(ins.getGoodsID());
				System.out.println(ins.getGoodsName());
			}
		}
	}

	public static void main(String[] args) throws KamenException {
		System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
		System.getProperties().setProperty("http.proxyPort", "606");
		KemenService s = new KemenService();

		// System.out.println(s.getQueryNew(new KamenUser(),
		// "37").getProductName());

		// Map<String, String> map = new HashMap<String, String>();
		// for (GoodsCatalogInfo in : s.getGoodsCatalogs(new KamenUser())) {
		// map.put(in.getGoodsNum(), in.getGoodsCatalogName());
		// }
		//
		// Iterator<String> it = map.keySet().iterator();
		// while (it.hasNext()) {
		// String key = it.next();
		// System.out.println(key + "--" + map.get(key));
		// }

		//
		for (GoodsCatalogInfo in : s.getGoodsCatalogs(new KamenUser())) {
			for (GoodsInfo ins : s.getGoodsInfos(new KamenUser(), in.getGoodsCatalogID())) {
				System.out.println(in.getGoodsCatalogID());
				System.out.println(in.getGoodsCatalogName());
				System.out.println(ins.getGoodsID());
				System.out.println(ins.getGoodsName());
			}
		}
		System.out.println(

		s.getTemplate(new KamenUser(), "1c88564a-cd32-4444-a483-34f735ca78c9"));

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

}
