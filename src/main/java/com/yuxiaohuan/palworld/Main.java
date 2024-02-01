package com.yuxiaohuan.palworld;

import com.yuxiaohuan.palworld.config.Config;
import com.yuxiaohuan.palworld.server.task.Task;
import lombok.extern.slf4j.Slf4j;

/**
 * 项目启动类
 *
 * @author : 于小环
 * @since : 2024/1/31 9:48
 */
@Slf4j
public class Main {
	public static void main(String[] args) {
		try {
			Config.readConfig();
			Task.start();
		} catch (Exception e) {
			log.error("出现异常", e);
			throw e;
		}
	}
}