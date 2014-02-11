package com.whty.platform.modules.kamen.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.kamen.Exception.KamenException;
import com.whty.platform.modules.kamen.entity.KamenUser;
import com.whty.platform.modules.kamen.entity.OrderInfo;
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
 * 卡门网 订单查询
 * 
 * @author qimin
 * 
 */
@Service("httpKaMen999Handler")
public class HttpKaMen999HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpKaMen999HandlerImpl.class);

	@Autowired
	private KemenService kemenService;

	public HttpBusinessResponse handler(HttpBusinessRequest httpBusinessRequest) {
		Services services = httpBusinessRequest.getServices();
		String key = httpBusinessRequest.getKey();
		// 取得URL
		StringBuffer interFaceUrl = new StringBuffer("http://");
		Provider provider = services.getProvider();
		interFaceUrl.append(provider.getIp());
		interFaceUrl.append(":").append(provider.getPort());
		interFaceUrl.append(services.getUri());
		logger.debug(interFaceUrl.toString());

		// 返回请求数据装配
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setActionName(httpBusinessRequest.getActionName());
		try {
			if (SpConsts.SELECT_ORDER.equals(httpBusinessRequest.getActionName())) {
				Map<String, Object> req_map = ActinoInfoUtils.info2map(httpBusinessRequest.getActionInfo(), key);
				logger.debug(req_map.toString());
				String StartTime = MapUtils.getValue(req_map, "START_DAY");// 客户外部系统订单号
				String EndTime = MapUtils.getValue(req_map, "END_DAY");// 客户外部系统订单号
				List<OrderInfo> orderInfos = kemenService.getGetOrderCustomers(new KamenUser(), StartTime, EndTime);
				List<Map<String, Object>> orders = new ArrayList<Map<String, Object>>();
				for (OrderInfo info : orderInfos) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ORDER_ID", info.getOrderId());
					map.put("CUSTOMER_ORDER_NO", info.getCustomerOrderNo());
					map.put("PRODUCT_NAME", info.getGoodsId());
					List<String> dataLlist = Lists.newArrayList();
					dataLlist.add("充值账户:" + info.getChargeAccount());
					dataLlist.add("购买数量:" + info.getChargeNum());
					dataLlist.add("面值:" + info.getChargePrice());
					map.put("DATA_LIST", dataLlist);
					map.put("CREATE_DATE", info.getChargeTime());
					map.put("STATUS", info.getOrderStatus());
					orders.add(map);
				}

				// actioninfo 数据填充
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("ORDERS", orders);
				actinoMap.put("PHONE_PROVINCE", "暂无");
				actinoMap.put("MERCHANT_TYPE", "暂无");
				actinoMap.put("MERCHANT_ID", "暂无");
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus("1");

			} else if (SpConsts.SELECT_ORDER_STATUS.equals(httpBusinessRequest.getActionName())) {
				Map<String, Object> req_map = ActinoInfoUtils.info2map(httpBusinessRequest.getActionInfo(), key);
				logger.debug(req_map.toString());
				String CustomerOrderNo = MapUtils.getValue(req_map, "CUSTOMER_ORDER_NO");// 客户外部系统订单号
				Query query = kemenService.getQueryNew(new KamenUser(), CustomerOrderNo);
				List<Map<String, Object>> orders = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ORDER_ID", query.getOrderNo());
				map.put("CUSTOMER_ORDER_NO", query.getCustomerOrderNo());
				map.put("DESCRIPTION", query.getDescription());
				map.put("STATUS", query.getOrderStatus());
				orders.add(map);
				// actioninfo 数据填充
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("ORDERS", orders);
				actinoMap.put("PHONE_PROVINCE", "暂无");
				actinoMap.put("MERCHANT_TYPE", "暂无");
				actinoMap.put("MERCHANT_ID", "暂无");
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus("1");
			} else {
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_100000);
				httpBusinessResponse.addActionValue("MESSAGE", "请检ACTION_NAME");
				httpBusinessResponse.setStatus("2");
			}
		} catch (KamenException e) {
			e.printStackTrace();
			KaMenUtils.addReturnCode(httpBusinessResponse, e.getCharge());
		}
		// 返回数据
		return httpBusinessResponse;
	}
}