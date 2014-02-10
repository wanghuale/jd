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

import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.kamen.Exception.KamenException;
import com.whty.platform.modules.kamen.entity.Charge;
import com.whty.platform.modules.kamen.entity.GoodsCatalogInfo;
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
 * 卡门网 游戏卡密
 * 
 * @author qimin
 * 
 */
@Service("httpKaMen302Handler")
public class HttpKaMen302HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpKaMen302HandlerImpl.class);

	@Autowired
	private KemenService kemenService;
	@Autowired
	private OrderRecordService orderRecordService;

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

		// 返回请求数据装配
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setActionName(hbr.getActionName());
		try {
			Map<String, Object> actininfo = ActinoInfoUtils.info2map(hbr.getActionInfo(), key);
			// TODO 解密key需要 存放 到数据库 获取
			System.out.println(actininfo);

			if (SpConsts.SELECT_PRODUCT.equals(hbr.getActionName())) {
				List<GoodsCatalogInfo> goodsc = kemenService.getGoodsCatalogs(new KamenUser());
				String goodsid = "";
				for (GoodsCatalogInfo inf : goodsc) {
					goodsid = inf.getGoodsCatalogID();
					System.out.println(inf.getGoodsCatalogID());
					System.out.println(inf.getGoodsCatalogName());
				}
				List<GoodsInfo> goodes = kemenService.getGoodsInfos(new KamenUser(), goodsid);
				List<Map<String, String>> goodsList = new ArrayList<Map<String, String>>();
				for (GoodsInfo info : goodes) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("GOODS_PAR_VALUE", info.getGoodsParvalue());
					map.put("PURCHASE_PRICE", info.getPurchasePrice());
					map.put("NAME", info.getGoodsName());
					map.put("PRODUCT_ID", info.getGoodsID());
					map.put("PAR_PRICE", info.getSellPrice());
					System.out.println(map);
					goodsList.add(map);
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
			} else if (SpConsts.SELECT_AREA.equals(hbr.getActionName())) {
				String templateguid = MapUtils.getValue(actininfo, "TEMPLATE_GUID");//
				String xml = kemenService.getTemplate(new KamenUser(), templateguid);
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("TEMPLATE", xml);
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus("1");
			} else if (SpConsts.CREATE_ORDER.equals(hbr.getActionName())) {
				String CustomerOrderNo = MapUtils.getValue(actininfo, "CUSTOMER_ORDER_NO");// 客户外部系统订单号
				String ProductId = MapUtils.getValue(actininfo, "PRODUCT_ID");// 要充值的商品编号
				String product_name = MapUtils.getValue(actininfo, "PRODUCT_NAME");// 要充值的商品编号
				String BuyNum = MapUtils.getValue(actininfo, "BUY_NUM");// 购买数量
				String ChargeAccount = MapUtils.getValue(actininfo, "CHARGE_ACCOUNT");// 要充值的游戏账号/手机号/电话/QQ号等
				String ChargePassword = MapUtils.getValue(actininfo, "CHARGE_PASSWORD");// 要充值的账号的密码，部分游戏需要。
				String ChargeGame = MapUtils.getValue(actininfo, "CHARGE_GAME");// 要充值的游戏，部分游戏需要。
				String ChargeRegion = MapUtils.getValue(actininfo, "CHARGE_REGION");// 要充值的游戏的区的名称，部分游戏需要。
				String ChargeServer = MapUtils.getValue(actininfo, "CHARGE_SERVER");// 要充值的游戏的服务器的名称，部分游戏需要。
				String ChargeType = MapUtils.getValue(actininfo, "TRANS_TYPE");// 要充值的游戏的计费方式/充值类型，部分游戏需要。
				String Sign = MapUtils.getValue(actininfo, "Sign");// 签名字符串
				String NotifyUrl = hbr.getNotifyurl();
				if (ActinoInfoUtils.isExistsOrderNo(orderRecordService, hbr.getConsumer().getId(), CustomerOrderNo)) {
					Charge err = new Charge();
					err.setErrorCode("1015");
					err.setErrorMsg("客户外部系统订单号已存在");
					throw new KamenException(err);
				}

				// 调用接口前准备数据
				OrderRecord orderRecord = ActinoInfoUtils.initOrder(orderRecordService, hbr, CustomerOrderNo);

				// 调用接口 找供应商下单
				RequestCharge params = new RequestCharge();
				params.setCustomerOrderNo(CustomerOrderNo);
				params.setProductId(ProductId);
				params.setBuyNum(BuyNum);
				params.setChargeAccount(ChargeAccount);
				params.setChargePassword(ChargePassword);
				params.setChargeGame(ChargeGame);
				params.setChargeRegion(ChargeRegion);
				params.setChargeServer(ChargeServer);
				params.setChargeType(ChargeType);
				params.setNotifyUrl(NotifyUrl);
				params.setSign(Sign);
				Charge charge = kemenService.charge(new KamenUser(), params);

				// 填充供应商返回订单信息
				orderRecord.setOrderId(charge.getOrderNo());
				orderRecord.setAmount(NumberUtils.toDouble(""));
				orderRecord.setNums(NumberUtils.toInt(BuyNum));
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
			KaMenUtils.addReturnCode(httpBusinessResponse, e.getCharge());
		}
		// 返回数据
		return httpBusinessResponse;
	}
}