package com.whty.platform.modules.kamen.utils;

import java.util.HashMap;
import java.util.Map;

import com.whty.platform.common.utils.security.MD5;
import com.whty.platform.modules.kamen.entity.Charge;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;

public class KaMenUtils {
	// 2. 参数utf-8编码
	// 所有参数里，凡是中文的，例如游戏、区服等，均要用utf-8编码。
	// 3. 签名算法
	// Key为密钥（联系业务人员获取），Sign为签名计算结果，***为参数的值：
	// Sign=MD5("CustomerId=***CustomerOrderNo=***"+Key);
	// 参数组合的顺序应按文档里的参数由上往下的按顺序组合，否则签名将不正确。
	// 4. 为空字段不参与签名计算
	// 所有接口里，如果那个参数不需要，则不参与签名计算。
	// 例如充值手机话费，不需要ChargeGame、ChargeRegion等参数，则在计算签名时ChargeGame=***ChargeRegion=***等不需要在参数里出现，也不参与签名计算。
	public static String makekey(String value, String key) {
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(value + key);
	}

	public static void addReturnCode(HttpBusinessResponse hbr, Charge e) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1001", "100004");// 缺少必要参数
		map.put("1002", "100004");// 参数格式不正确
		map.put("1003", "100003");// 签名错误
		map.put("1004", "203001");// 该订单号不属于该站点
		map.put("1005", "203002");// 客户ID不存在
		map.put("1006", "204004");// 商品不存在或无法购买
		map.put("1007", "204005");// 商品在禁售时间段内，无法购买。
		map.put("1008", "204006");// 站点黑名单
		map.put("1009", "204006");// 商品黑名单
		map.put("1010", "204007");// 商品类型错误
		map.put("1011", "204008");// 库存不足
		map.put("1012", "204009");// 账户余额不足
		map.put("1013", "204010");// 站点余额不足以支付
		map.put("1014", "203006");// 生成订单失败
		map.put("1015", "203004");// 客户外部系统订单号已存在
		map.put("1016", "203005");// 客户外部系统订单号不存在
		map.put("1017", "205003");// 加提成失败
		map.put("1018", "204011");// 无该账号的坐标数据
		map.put("1019", "204012");// 该帐号没有充值区域商品权限
		map.put("1020", "204013");// 商品购买策略限制
		map.put("1021", "204014");// 该商品正在维护
		map.put("1022", "204015");// 获取库存信息失败
		hbr.addActionValue("ACTION_RETURN_CODE", getMapValue(map, e.getErrorCode()));
		hbr.addActionValue("MESSAGE", e.getErrorMsg());
		hbr.setStatus("2");
	}

	public static String getMapValue(Map<String, String> map, String key) {
		return map.get(key) == null ? SpConsts.ACTION_RETURN_CODE_100000 : map.get(key);
	}
}
