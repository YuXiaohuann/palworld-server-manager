package com.yuxiaohuan.palworld.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuxiaohuan.palworld.common.Utils;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 配置类
 *
 * @author : 于小环
 * @since : 2024/1/31 9:48
 */
@Slf4j
@Data
public class Config {
	public static List<ServerConfig> serverConfigList;

	/**
	 * 读取配置文件
	 */
	@SneakyThrows
	public static void readConfig() {
		log.info("开始读取配置");
		String configFilePath = System.getProperty("user.dir") + "\\config.json";
		FileInputStream inputStream = new FileInputStream(configFilePath);
		if (!Utils.isEmpty(inputStream)) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				StringBuilder stringBuilder = new StringBuilder();
				reader.lines().forEach(stringBuilder::append);
				JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
				serverConfigList = JSONObject.parseArray(jsonObject.getString("serverConfigList"), ServerConfig.class);
			}
		} else {
			log.error("未读取到config.json文件！请检查此文件是否与jar在同级目录！");
			throw new Exception();
		}
		log.info(JSON.toJSONString(Config.serverConfigList));
		log.info("配置读取完毕");
	}
}
