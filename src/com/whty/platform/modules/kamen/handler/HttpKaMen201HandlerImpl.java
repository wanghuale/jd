package com.whty.platform.modules.kamen.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.whty.platform.modules.kamen.entity.Query;
import com.whty.platform.modules.kamen.service.KemenService;
import com.whty.platform.modules.kamen.utils.KaMenUtils;
import com.whty.platform.modules.server.utils.MapUtils;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sp.utils.ActinoInfoUtils;

/**
 * 卡门网 手机话费充值
 * 
 * @author qimin
 * 
 */
@Service("httpKaMen201Handler")
public class HttpKaMen201HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpKaMen201HandlerImpl.class);

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
			if (SpConsts.SELECT_PRODUCT.equals(hbr.getActionName())) {
				String phoneType = MapUtils.getValue(actininfo, "PHONE_TYPE");
				String businissType = SpConsts.BUSINISS_TYPE_YD;
				if (SpConsts.PHONE_TYPE_LT.equals(phoneType)) {
					businissType = SpConsts.BUSINISS_TYPE_LT;
				} else if (SpConsts.PHONE_TYPE_DX.equals(phoneType)) {
					businissType = SpConsts.BUSINISS_TYPE_DX;
				}
				List<GoodsInfo> goodes = kemenService.getGoodsInfos(new KamenUser(), businissType);
				List<Map<String, String>> goodsList = new ArrayList<Map<String, String>>();
				for (GoodsInfo info : goodes) {
					// 修改SP售价价格,无价格设置不需销售
					GoodsConsumer g = goodsConsumerService.getGoodsConcumerByGCODEandCID(info.getGoodsID(), hbr.getConsumer().getId());
					if (g != null && g.getId() != null) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("GOODS_PAR_VALUE", info.getGoodsParvalue());
						map.put("PURCHASE_PRICE", g.getSell_price().toString());
						map.put("PRODUCT_NAME", info.getGoodsName());
						map.put("PRODUCT_ID", info.getGoodsID());
						map.put("PAR_PRICE", g.getSell_price().toString());
						goodsList.add(map);
					}
				}
				// actioninfo 数据填充
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("PHONE_TYPE", "手机话费充值");
				actinoMap.put("PRODUCTS", goodsList);
				actinoMap.put("PHONE_PROVINCE", "暂无");
				actinoMap.put("MERCHANT_TYPE", "暂无");
				actinoMap.put("MERCHANT_ID", "暂无");
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus("1");
			} else if (SpConsts.CREATE_ORDER.equals(hbr.getActionName())) {
				String CustomerOrderNo = MapUtils.getValue(actininfo, "CUSTOMER_ORDER_NO");
				String ChargePhone = MapUtils.getValue(actininfo, "PHONE");
				String ChargeParValue = MapUtils.getValue(actininfo, "PAR_PRICE");

				if (StringUtils.isBlank(CustomerOrderNo) || StringUtils.isBlank(ChargePhone) || StringUtils.isBlank(ChargeParValue)) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("缺少必要参数");
					throw new KamenException(err);
				}

				if (ActinoInfoUtils.isExistsOrderNo(orderRecordService, hbr.getConsumer().getId(), CustomerOrderNo)) {
					Charge err = new Charge();
					err.setErrorCode("1015");
					err.setErrorMsg("客户外部系统订单号已存在");
					throw new KamenException(err);
				}
				// TODO 测试阶段只能充值1元
				if (NumberUtils.toDouble(ChargeParValue) > 1) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("测试阶段只能充值1元");
					throw new KamenException(err);
				}

				// 调用接口前准备数据
				orderRecord = ActinoInfoUtils.initOrder(orderRecordService, hbr, CustomerOrderNo);

				// 调用接口 找供应商下单
				Charge charge = null;
				try {
					charge = kemenService.chargePhone(new KamenUser(), orderRecord.getId().toString(), ChargePhone, ChargeParValue,
							hbr.getNotifyurl());
				} catch (KamenException e) {
					orderRecordService.delete(orderRecord.getId());
					throw e;
				}

				// 获取订单信息
				Query orderInfo = kemenService.getQueryNew(new KamenUser(), orderRecord.getId() + "");

				// 查询产品信息
				GoodsInfo goodsInfo = kemenService.getGoodsInfo(orderInfo.getProductId());

				// 修改SP售价价格
				GoodsConsumer g = goodsConsumerService.getGoodsConcumerByGCODEandCID(orderInfo.getProductId(), hbr.getConsumer().getId());
				if (g != null && g.getId() != null) {
					orderRecord.setAmount(g.getSell_price());
					orderRecord.setGoodsprice(g.getSell_price()); // 产品销售价格
					orderRecord.setGoodsparvalue(NumberUtils.toDouble(goodsInfo.getGoodsParvalue()));// 商品面值
					orderRecord.setPurchaserprice(NumberUtils.toDouble(goodsInfo.getSellPrice()));// 产品进价
				} else {// ?
					orderRecord.setAmount(NumberUtils.toDouble(ChargeParValue));
					orderRecord.setGoodsparvalue(NumberUtils.toDouble(ChargeParValue));// 商品面值
					orderRecord.setPurchaserprice(NumberUtils.toDouble(ChargeParValue));// 产品进价
				}

				// 填充供应商返回订单信息
				orderRecord.setOrderId(charge.getOrderNo());
				orderRecord.setNums(1);
				orderRecord.setStatus("3");
				orderRecord.setDispaly(ChargePhone);
				orderRecord.setProductid(orderInfo.getProductId());
				orderRecord.setProductname(orderInfo.getProductName());
				orderRecordService.save(orderRecord);

				// 返回报文给客户
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("CUSTOMER_ORDER_NO", orderRecord.getPayOrderId());
				actinoMap.put("ORDER_ID", orderRecord.getId());
				actinoMap.put("PRODUCT_ID", orderInfo.getProductId());
				actinoMap.put("PRODUCT_NAME", orderInfo.getProductName());
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
		return httpBusinessResponse;
	}

}