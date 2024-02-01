package com.yuxiaohuan.palworld.config;

import lombok.Data;

import java.util.List;

/**
 * 服务器配置
 *
 * @author : 于小环
 * @since : 2024/1/31 18:17
 */
@Data
public class ServerConfig {
	/**
	 * 服务器地址
	 */
	private String serverHost;
	/**
	 * 服务器rcon端口
	 */
	private int serverPort;
	/**
	 * 服务器rcon密码
	 */
	private String serverPassword;
	/**
	 * 白名单检查周期
	 */
	private int checkCycle;
	/**
	 * 白名单列表
	 */
	private List<String> steamIdWhitelist;
}
