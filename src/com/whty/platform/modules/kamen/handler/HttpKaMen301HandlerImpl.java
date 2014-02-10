package com.whty.platform.modules.kamen.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.platform.modules.bussiness.entity.GoodsConsumer;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.GoodsConsumerService;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.kamen.Exception.KamenException;
import com.whty.platform.modules.kamen.entity.Charge;
import com.whty.platform.modules.kamen.entity.GoodsInfo;
import com.whty.platform.modules.kamen.entity.KamenUser;
import com.whty.platform.modules.kamen.entity.RequestCharge;
import com.whty.platform.modules.kamen.service.KemenService;
import com.whty.platform.modules.kamen.utils.KaMenUtils;
import com.whty.platform.modules.server.utils.MapUtils;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sp.utils.ActinoInfoUtils;

/**
 * 卡门网 Q币充值
 * 
 * @author qimin
 * 
 */
@Service("httpKaMen301Handler")
public class HttpKaMen301HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpKaMen301HandlerImpl.class);

	@Autowired
	private KemenService kemenService;
	@Autowired
	private OrderRecordService orderRecordService;

	@Autowired
	private GoodsConsumerService goodsConsumerService;

	public HttpBusinessResponse handler(HttpBusinessRequest hbr) {
		Services services = hbr.getServices();
		String key = hbr.getKey();
		// 取得URL
		StringBuffer interFaceUrl = new StringBuffer("http://");
		Provider provider = services.getProvider();
		interFaceUrl.append(provider.getIp());
		interFaceUrl.append(":").append(provider.getPort());
		interFaceUrl.append(services.getUri());
		logger.debug(interFaceUrl.toString());

		OrderRecord orderRecord = null;
		// 返回请求数据装配
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setActionName(hbr.getActionName());
		try {
			Map<String, Object> actininfo = ActinoInfoUtils.info2map(hbr.getActionInfo(), key);
			System.out.println(actininfo);
			logger.debug("数据库：" + actininfo.toString());
			if (SpConsts.SELECT_PRODUCT.equals(hbr.getActionName())) {
				List<GoodsInfo> goodes = kemenService.getGoodsInfos(new KamenUser(), SpConsts.BUSINISS_TYPE_QB);
				List<Map<String, String>> goodsList = new ArrayList<Map<String, String>>();
				for (GoodsInfo info : goodes) {
					// 修改SP售价价格,无价格设置不需销售
					GoodsConsumer g = goodsConsumerService.getGoodsConcumerByGCODEandCID(info.getGoodsID(), hbr.getConsumer().getId());
					if (g != null && g.getId() != null) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("GOODS_PAR_VALUE", info.getGoodsParvalue());
						map.put("PURCHASE_PRICE", g.getSell_price().toString());// SP的销售价就是消费者的采购价
						map.put("NAME", info.getGoodsName());
						map.put("PRODUCT_ID", info.getGoodsID());
						map.put("PAR_PRICE", g.getSell_price().toString());
						System.out.println(map);
						goodsList.add(map);
					}
				}
				// actioninfo 数据填充
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("PRODUCTS", goodsList);
				actinoMap.put("PHONE_PROVINCE", "暂无");
				actinoMap.put("MERCHANT_TYPE", "暂无");
				actinoMap.put("MERCHANT_ID", "暂无");
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus("1");

			} else if (SpConsts.CREATE_ORDER.equals(hbr.getActionName())) {
				String CustomerOrderNo = MapUtils.getValue(actininfo, "CUSTOMER_ORDER_NO");// 客户外部系统订单号
				String ProductId = MapUtils.getValue(actininfo, "PRODUCT_ID");// 要充值的商品编号
				String product_name = MapUtils.getValue(actininfo, "PRODUCT_NAME");// 要充值的商品编号
				String nums = MapUtils.getValue(actininfo, "NUMS");// 要充值的商品编号
				String ChargeAccount = MapUtils.getValue(actininfo, "QQ");// 要充值的游戏账号/手机号/电话/QQ号等
				String NotifyUrl = hbr.getNotifyurl();

				// 验证订单号
				if (ActinoInfoUtils.isExistsOrderNo(orderRecordService, hbr.getConsumer().getId(), CustomerOrderNo)) {
					Charge err = new Charge();
					err.setErrorCode("1015");
					err.setErrorMsg("客户外部系统订单号已存在");
					throw new KamenException(err);
				}

				// 查询卡门商品
				GoodsInfo roodsInfo = getGoodsInfo(ProductId);
				if (roodsInfo == null) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("没有该充值产品");
					throw new KamenException(err);
				}

				// 查询顾客发布商品 修改SP售价价格
				GoodsConsumer g = goodsConsumerService.getGoodsConcumerByGCODEandCID(roodsInfo.getGoodsID(), hbr.getConsumer().getId());
				if (g == null || g.getId() == null) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("商品禁止销售,请联系管理人员帮您上架改商品");
					throw new KamenException(err);
				}

				// 调用接口
				Integer t_nums = NumberUtils.toInt(nums);
				t_nums = t_nums <= 0 ? 1 : t_nums;

				// TODO 测试阶段只能充值1元
				if (t_nums > 1) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("测试阶段只能充值1元");
					throw new KamenException(err);
				}

				// 调用接口前准备数据
				orderRecord = ActinoInfoUtils.initOrder(orderRecordService, hbr, CustomerOrderNo);
				RequestCharge params = new RequestCharge();
				params.setCustomerOrderNo(orderRecord.getId().toString());
				params.setProductId(ProductId);
				params.setBuyNum(t_nums + "");
				params.setChargeAccount(ChargeAccount);
				params.setNotifyUrl(NotifyUrl);

				Charge charge = null;
				try {
					charge = kemenService.charge(new KamenUser(), params);
				} catch (KamenException e) {
					orderRecordService.delete(orderRecord.getId());
					throw e;
				}

				// 填充供应商返回订单信息
				orderRecord.setOrderId(charge.getOrderNo());

				// 修改SP售价价格
				orderRecord.setNums(t_nums);
				orderRecord.setAmount(t_nums * g.getSell_price());
				orderRecord.setGoodsprice(t_nums * g.getSell_price()); // 产品销售价格
				orderRecord.setGoodsparvalue(t_nums * NumberUtils.toDouble(roodsInfo.getGoodsParvalue()));// 商品面值
				orderRecord.setPurchaserprice(t_nums * NumberUtils.toDouble(roodsInfo.getSellPrice()));// 产品进价
				orderRecord.setStatus("3");
				orderRecord.setDispaly(ChargeAccount);
				orderRecord.setProductid(ProductId);
				orderRecord.setProductname(product_name);
				orderRecordService.save(orderRecord);

				// 返回报文给客户
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("CUSTOMER_ORDER_NO", orderRecord.getPayOrderId());
				actinoMap.put("ORDER_ID", orderRecord.getId());
				actinoMap.put("MERCHANT_TYPE", "暂无");
				actinoMap.put("MERCHANT_ID", "暂无");
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus(SpConsts.SUSSCCE);
			}
		} catch (KamenException e) {
			e.printStackTrace();
			if (orderRecord != null) {
				orderRecord.setStatus("2");
				orderRecordService.save(orderRecord);
			}
			KaMenUtils.addReturnCode(httpBusinessResponse, e.getCharge());
		}
		// 返回数据
		return httpBusinessResponse;
	}

	public GoodsInfo getGoodsInfo(String productid) throws KamenException {
		List<GoodsInfo> goodes = new KemenService().getGoodsInfos(new KamenUser(), SpConsts.BUSINISS_TYPE_QB);
		for (GoodsInfo info : goodes) {
			if (info.getGoodsID().equals(productid)) {
				return info;
			}
		}
		return null;
	}

	public static void main(String[] args) throws KamenException {
		System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
		System.getProperties().setProperty("http.proxyPort", "606");
		String productid = "893942";
		List<GoodsInfo> goodes = new KemenService().getGoodsInfos(new KamenUser(), SpConsts.BUSINISS_TYPE_QB);
		for (GoodsInfo info : goodes) {
			if (info.getGoodsID().equals(productid)) {
				System.out.println(info.getGoodsParvalue());
				System.out.println(info.getSellPrice());
				System.out.println(info.getPurchasePrice());
			}

		}
	}
}