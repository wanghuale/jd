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
 * 卡门网 游戏直充
 * 
 * @author qimin
 * 
 */
@Service("httpKaMen303Handler")
public class HttpKaMen303HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpKaMen303HandlerImpl.class);

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
		kemenService.setURL(interFaceUrl.toString());
		logger.debug(kemenService.getURL());
		// 返回请求数据装配
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setActionName(hbr.getActionName());
		try {
			Map<String, Object> actininfo = ActinoInfoUtils.info2map(hbr.getActionInfo(), key);
			if (SpConsts.SELECT_GOOD.equals(hbr.getActionName())) {
				List<GoodsCatalogInfo> goodsList = kemenService.getGoodsCatalogs(new KamenUser());
				List<Map<String, Object>> re_pList = new ArrayList<Map<String, Object>>();
				for (Map<String, String> p_good : SpConsts.p_goods) {
					String p_good_id = p_good.get("P_GOOD_ID");
					List<Map<String, Object>> re_goodsList = new ArrayList<Map<String, Object>>();
					for (GoodsCatalogInfo goods : goodsList) {
						// 检查类目
						if (p_good_id.equals(goods.getGoodsNum())
								&& goodsConsumerService.getGoodsConcumerByLM(goods.getGoodsCatalogID(), hbr.getConsumer().getId())) {
							Map<String, Object> goodsMap = new HashMap<String, Object>();
							goodsMap.put("GOODS_ID", goods.getGoodsCatalogID());
							goodsMap.put("GOODS_NAME", goods.getGoodsCatalogName());
							goodsMap.put("P_GOODS_ID", p_good_id);
							goodsMap.put("P_GOODS_NAME", p_good.get("P_GOOD_NAME"));
							re_goodsList.add(goodsMap);
						}
					}
					Map<String, Object> p_goodsMap = new HashMap<String, Object>();
					p_goodsMap.put("P_GOODS_ID", p_good_id);
					p_goodsMap.put("P_GOODS_NAME", p_good.get("P_GOOD_NAME"));
					p_goodsMap.put("GOODS", re_goodsList);
					if (re_goodsList.size() > 0) {
						re_pList.add(p_goodsMap);
					}
				}
				// actioninfo 数据填充
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("P_GOODS", re_pList);
				httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
				httpBusinessResponse.addActionValue("ACTION_INFO", ActinoInfoUtils.map2info(actinoMap, key));
				httpBusinessResponse.setStatus("1");
			} else if (SpConsts.SELECT_PRODUCT.equals(hbr.getActionName())) {
				String goods_id = MapUtils.getValue(actininfo, "GOODS_ID");// 类目编号
				List<Map<String, Object>> re_goodsList = new ArrayList<Map<String, Object>>();
				List<GoodsCatalogInfo> goodsList = kemenService.getGoodsCatalogs(new KamenUser());
				for (GoodsCatalogInfo goods : goodsList) {
					if (goods.getGoodsCatalogID().equals(goods_id)) {
						Map<String, Object> goodsMap = new HashMap<String, Object>();
						goodsMap.put("GOODS_ID", goods.getGoodsCatalogID());
						goodsMap.put("GOODS_NAME", goods.getGoodsCatalogName());
						if (goods.getGoodsCatalogName().indexOf("话费") < 0) {
							List<Map<String, String>> re_productsList = new ArrayList<Map<String, String>>();
							List<GoodsInfo> productsList = kemenService.getGoodsInfos(new KamenUser(), goods.getGoodsCatalogID());
							for (GoodsInfo info : productsList) {
								// 校验商品设置
								GoodsConsumer g = goodsConsumerService.getGoodsConcumerByGCODEandCID(info.getGoodsID(), hbr.getConsumer()
										.getId());
								if (g != null && g.getId() != null) {
									Map<String, String> map = new HashMap<String, String>();
									map.put("GOODS_PAR_VALUE", info.getGoodsParvalue());
									map.put("PURCHASE_PRICE", g.getSell_price().toString());
									map.put("NAME", info.getGoodsName());
									map.put("PRODUCT_ID", info.getGoodsID());
									map.put("PAR_PRICE", g.getSell_price().toString());
									map.put("TEMPLATE_GUID", info.getTemplateGuid());
									re_productsList.add(map);
								}
							}
							goodsMap.put("PRODUCTS", re_productsList);
							re_goodsList.add(goodsMap);
						}
					}
				}
				// actioninfo 数据填充
				Map<String, Object> actinoMap = new HashMap<String, Object>();
				actinoMap.put("GOODS", re_goodsList);
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

				GoodsInfo goodsInfo = kemenService.getGoodsInfo(ProductId);
				if (goodsInfo == null) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("没有该充值产品");
					throw new KamenException(err);
				}

				// 查询顾客发布商品 修改SP售价价格
				GoodsConsumer g = goodsConsumerService.getGoodsConcumerByGCODEandCID(goodsInfo.getGoodsID(), hbr.getConsumer().getId());
				if (g == null || g.getId() == null) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("商品禁止销售,请联系管理人员帮您上架改商品");
					throw new KamenException(err);
				}

				if (NumberUtils.toDouble(goodsInfo.getGoodsParvalue()) > 1) {
					Charge err = new Charge();
					err.setErrorCode("1001");
					err.setErrorMsg("测试阶段不能充值大于1元的产品");
					throw new KamenException(err);
				}

				// 调用接口前准备数据
				OrderRecord orderRecord = ActinoInfoUtils.initOrder(orderRecordService, hbr, CustomerOrderNo);

				// 调用接口 找供应商下单
				RequestCharge params = new RequestCharge();
				params.setCustomerOrderNo(orderRecord.getId() + "");
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

				try {
					Charge charge = kemenService.charge(new KamenUser(), params);
					// 填充供应商返回订单信息
					orderRecord.setOrderId(charge.getOrderNo());
					orderRecord.setAmount(NumberUtils.toInt(BuyNum) * NumberUtils.toDouble(goodsInfo.getGoodsParvalue()));
					orderRecord.setNums(NumberUtils.toInt(BuyNum));
					orderRecord.setAmount(NumberUtils.toInt(BuyNum) * g.getSell_price());
					orderRecord.setGoodsprice(NumberUtils.toInt(BuyNum) * g.getSell_price()); // 产品销售价格
					orderRecord.setGoodsparvalue(NumberUtils.toInt(BuyNum) * NumberUtils.toDouble(goodsInfo.getGoodsParvalue()));// 商品面值
					orderRecord.setPurchaserprice(NumberUtils.toInt(BuyNum) * NumberUtils.toDouble(goodsInfo.getSellPrice()));// 产品进价
					orderRecord.setStatus("3");
					orderRecord.setDispaly(ChargeAccount);
					orderRecord.setProductid(ProductId);
					orderRecord.setProductname(product_name);
					orderRecordService.save(orderRecord);
				} catch (KamenException e) {
					orderRecordService.delete(orderRecord.getId());
					throw e;
				}

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