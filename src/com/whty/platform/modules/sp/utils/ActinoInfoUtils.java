package com.whty.platform.modules.sp.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.whty.platform.common.utils.mapper.JsonMapper;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;

/**
 * action_info数据处理工具类
 * 
 * @author qimin
 * 
 */
public class ActinoInfoUtils {

	/**
	 * 解密信息并返回MAP
	 */
	public static Map<String, Object> info2map(String info, String mallKey) {
		try {
			return jsonToMap(DecodeUtils.enData(info, mallKey));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 返回JSON加密字符串
	 */
	public static String map2info(Map<String, Object> map, String mallKey) {
		try {
			return DecodeUtils.deData(mapToJson(map), mallKey);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * map转化为json
	 */
	public static Map<String, Object> jsonToMap(String json) throws UnsupportedEncodingException {
		return JsonMapper.getInstance()
				.fromJson(json, JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class));

	}

	/**
	 * json转化为MAP
	 */
	public static String mapToJson(Map<String, Object> map) throws UnsupportedEncodingException {
		// System.out.println(JsonMapper.getInstance().toJson(map));
		return JsonMapper.getInstance().toJson(map);
	}

	public static OrderRecord initOrder(OrderRecordService orderRecordService, HttpBusinessRequest hbr, String CustomerOrderNo) { // 保存订单
		OrderRecord orderRecord = new OrderRecord();
		orderRecord.setPayOrderId(CustomerOrderNo);
		orderRecord.setOrderId("");
		orderRecord.setAmount(0D);
		orderRecord.setGoodsprice(0D);
		orderRecord.setGoodsparvalue(0D);
		orderRecord.setPurchaserprice(0D);
		orderRecord.setProductname("");
		orderRecord.setDispaly("0");
		orderRecord.setNums(1);
		orderRecord.setStatus("0");
		orderRecord.setCreateDate(new Date());
		orderRecord.setConsumer(hbr.getConsumer());
		orderRecord.setCallBackUrl(hbr.getConsumer().getNotifyurl());
		orderRecord.setService(hbr.getServices());
		orderRecord.setProvider(hbr.getServices().getProvider());
		orderRecordService.save(orderRecord);
		return orderRecord;
	}

	public static boolean isExistsOrderNo(OrderRecordService orderRecordService, Long ConsumerID, String CustomerOrderNo) {
		if (orderRecordService.getByOrderID(CustomerOrderNo, ConsumerID) != null) {
			return true; // 存在
		} else {
			return false;// 不存在
		}
	}
}
