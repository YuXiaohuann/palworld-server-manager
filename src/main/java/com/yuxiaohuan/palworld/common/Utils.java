package com.yuxiaohuan.palworld.common;

import java.util.Collection;
import java.util.Map;

/**
 * 工具类
 *
 * @author : 于小环
 * @since : 2024/1/31 18:10
 */
public class Utils {

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return str.isEmpty();
		}
		return false;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null) {
			return true;
		}
		return map.isEmpty();
	}

	public static boolean isEmpty(Collection<?> list) {
		if (list == null) {
			return true;
		}
		return list.isEmpty();
	}


	/**
	 * 判断两个Integer是否相等
	 */
	public static boolean isEqual(Integer i1, Integer i2) {
		if (i1 == null && i2 == null) {
			throw new RuntimeException("数据对比失败！请先判断参数是否为null！");
		}
		if (i1 == null || i2 == null) {
			return false;
		}
		return i1.equals(i2);
	}
}
