package com.whty.platform.modules.sp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpConsts {
	public static String SELECT_GOOD = "SELECT_GOOD";
	public static String SELECT_PRODUCT = "SELECT_PRODUCT";
	public static String SELECT_PRODUCT2 = "SELECT_PRODUCT2";
	public static String SELECT_PRODUCT3 = "SELECT_PRODUCT3";
	public static String CREATE_ORDER = "CREATE_ORDER";
	public static String SELECT_AREA = "SELECT_AREA";

	public static String ACTION_RETURN_CODE_000000 = "000000";
	public static String ACTION_RETURN_CODE_100000 = "100000";
	public static String BUSINISS_TYPE_QB = "1090";// QB充值
	public static String BUSINISS_TYPE_YD = "4668";// 移动充值
	public static String BUSINISS_TYPE_LT = "4668";// 联通充值
	public static String BUSINISS_TYPE_DX = "4668";// 电信充值
	public static String PHONE_TYPE_YD = "YD";// 移动充值
	public static String PHONE_TYPE_LT = "LT";// 联通充值
	public static String PHONE_TYPE_DX = "DX";// 电信充值

	public static String SUSSCCE = "1";//

	public static String SELECT_ORDER = "SELECT_ORDER";
	public static String SELECT_ORDER_STATUS = "SELECT_ORDER_STATUS";

	public static Map<String, String> errorcode = new HashMap<String, String>();
	static {
		errorcode.put("000000", "成功");
		errorcode.put("100000", "其他错误");
		errorcode.put("100001", "更新 、插入、删除 操作失败");
		errorcode.put("100002", "创建的未支付订单达到限额");
		errorcode.put("100003", "密钥错误");
		errorcode.put("100004", "参数错误");
		errorcode.put("100005", "用户名错误");
		errorcode.put("100006", "IP访问受限");

		errorcode.put("100044", "Json Is Error");
		errorcode.put("100045", "服务handler设置出错，请联系管理员设置handler");
		errorcode.put("100046", "协议暂时不支持");
		errorcode.put("100047", "服务未开通，拒绝访问,请联系管理员开通服务");
		errorcode.put("100048", "系统繁忙，请稍后");

		errorcode.put("203001", "订单不存在");
		errorcode.put("203002", "充值的手机号是未知手机号");
		errorcode.put("203003", "根据商品ID查询商品不存在");
		errorcode.put("203004", "客户外部系统订单号已存在");
		errorcode.put("203005", "客户外部系统订单号不存在");
		errorcode.put("203006", "生成订单失败");
		errorcode.put("204001", "手机充值服务故障");
		errorcode.put("204002", "运营商不存在");
		errorcode.put("204003", "手机充值服务故障");
		errorcode.put("204004", "商品不存在或无法购买");
		errorcode.put("204005", "商品在禁售时间段内，无法购买。");
		errorcode.put("204006", "商品黑名单");
		errorcode.put("204007", "商品类型错误");
		errorcode.put("204008", "库存不足");
		errorcode.put("204009", "账户余额不足");
		errorcode.put("204010", "站点余额不足以支付");
		errorcode.put("204011", "无该账号的坐标数据");
		errorcode.put("204012", "该帐号没有充值区域商品权限");
		errorcode.put("204013", "商品购买策略限制");
		errorcode.put("204014", "该商品正在维护");
		errorcode.put("204015", "获取库存信息失败");
		errorcode.put("205001", "客户外部系统订单号已存在");
		errorcode.put("205002", "客户外部系统订单号不存在");
		errorcode.put("205003", "获取库存信息失败");
	}

	public static String getErrorValue(String key) {
		return errorcode.get(key) == null ? "未知错误" : errorcode.get(key);
	}

	public static List<Map<String, String>> p_goods = new ArrayList<Map<String, String>>();

	public static List<Map<String, String>> p_goods_all = new ArrayList<Map<String, String>>();

	public static Map<String, String> checkgood = new HashMap<String, String>();

	static {
		Map<String, String> good = new HashMap<String, String>();

		good.put("P_GOOD_ID", "1001");
		good.put("P_GOOD_NAME", "网络游戏点卡（卡密产品质保三个月） ");
		p_goods.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "1171");
		good.put("P_GOOD_NAME", "腾讯QQ专区");
		p_goods.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "1208");
		good.put("P_GOOD_NAME", "游戏道具及激活码");
		p_goods.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "1839");
		good.put("P_GOOD_NAME", "更多精彩,更多欢乐");
		p_goods.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "1866");
		good.put("P_GOOD_NAME", "视频娱乐棋牌类平台专区");
		p_goods.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "2002");
		good.put("P_GOOD_NAME", "行业服务软件类  ");
		p_goods.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "2660");
		good.put("P_GOOD_NAME", "网页游戏");
		p_goods.add(good);

		p_goods_all.addAll(p_goods);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "4425");
		good.put("P_GOOD_NAME", "中国移动（支持全国移动手机充值）");
		p_goods_all.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "4505");
		good.put("P_GOOD_NAME", "中国联通（支持全国联通手机充值）");
		p_goods_all.add(good);

		good = new HashMap<String, String>();
		good.put("P_GOOD_ID", "4515");
		good.put("P_GOOD_NAME", "中国电信（支持全国电信手机充值）");
		p_goods_all.add(good);

	}

}
