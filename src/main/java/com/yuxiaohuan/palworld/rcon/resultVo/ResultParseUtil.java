package com.yuxiaohuan.palworld.rcon.resultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 结果解析工具类
 *
 * @author : 于小环
 * @since : 2024/1/31 17:51
 */
public class ResultParseUtil {
	/**
	 * 解析玩家列表信息
	 *
	 * @param info rcon返回的玩家信息
	 * @return 玩家信息
	 */
	public static List<PlayerVo> parsePlayerInfo(String info) {
		List<PlayerVo> playerVoList = new ArrayList<>();
		String[] split = info.split("\n");
		for (int i = 0; i < split.length; i++) {
			if (i == 0 || i == split.length - 1) {
				continue;
			}
			String[] strings = split[i].split(",");
			String name = split[i].replace("," + strings[strings.length - 2] + "," + strings[strings.length - 1], "");
			playerVoList.add(new PlayerVo(name, strings[strings.length - 2], strings[strings.length - 1]));
		}
		return playerVoList;
	}

	public static void main(String[] args) {
		String info = "name,playeruid,steamid\n" +
				"钧轩一,184426763,76561198978085397\n" +
				"KANA,3003134254,76561199235581258\n" +
				"法国大屌,912953694,76561199343218144\n" +
				"花誓,418222302,76561199249660880\n" +
				"少爷,1806020295,76561198444744527\n" +
				"東雲絵名�\n" +
				"\n";
		System.out.println(parsePlayerInfo(info));
	}
}
