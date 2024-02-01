package com.yuxiaohuan.palworld.server.task;

import com.yuxiaohuan.palworld.config.Config;
import com.yuxiaohuan.palworld.config.ServerConfig;
import com.yuxiaohuan.palworld.rcon.Rcon;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 * 定时任务管理类
 *
 * @author : 于小环
 * @since : 2024/2/1 15:30
 */
@Slf4j
public class Task {
	public static final ExecutorService executorService = new ForkJoinPool(0x7fff - 1, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
	public static CountDownLatch latch;

	/**
	 * 定时任务启动
	 */
	@SneakyThrows
	public static void start() {
		log.info("开始启动steamId白名单检查");
		latch = new CountDownLatch(Config.serverConfigList.size());
		log.info("待启动数量{}", Config.serverConfigList.size());
		for (ServerConfig serverConfig : Config.serverConfigList) {
			WhitelistFilter whitelistFilter = new WhitelistFilter(new Rcon(serverConfig.getServerHost(), serverConfig.getServerPort(), serverConfig.getServerPassword().getBytes()), serverConfig.getSteamIdWhitelist(), serverConfig.getCheckCycle());
			executorService.submit(whitelistFilter);
		}
		log.info("启动steamId白名单检查完毕");
		latch.await();
	}
}
