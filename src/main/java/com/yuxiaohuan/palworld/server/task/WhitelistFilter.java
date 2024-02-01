package com.yuxiaohuan.palworld.server.task;

import com.yuxiaohuan.palworld.common.Utils;
import com.yuxiaohuan.palworld.rcon.PalworldRconCommand;
import com.yuxiaohuan.palworld.rcon.Rcon;
import com.yuxiaohuan.palworld.rcon.resultVo.PlayerVo;
import com.yuxiaohuan.palworld.rcon.resultVo.ResultParseUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 玩家steamId白名单过滤
 *
 * @author : 于小环
 * @since : 2024/1/31 18:05
 */
@Slf4j
@AllArgsConstructor
public class WhitelistFilter implements Runnable {
	private Rcon rcon;
	private List<String> steamIdWhitelist;
	/**
	 * 检查周期
	 */
	private int checkCycle;

	@SneakyThrows
	@Override
	public void run() {
		if (Utils.isEmpty(steamIdWhitelist)) {
			Task.latch.countDown();
			log.error("服务器[{}:{}]未配置steamId白名单,定时任务启动取消！", rcon.getSocket().getInetAddress().getHostAddress(), rcon.getSocket().getPort());
			return;
		}
		if (Utils.isEqual(checkCycle, 0) || Utils.isEmpty(checkCycle)) {
			log.error("服务器[{}:{}]steamId白名单检查周期配置异常！", rcon.getSocket().getInetAddress().getHostAddress(), rcon.getSocket().getPort());
			Task.latch.countDown();
			return;
		}
		try {
			while (true) {
				log.info("服务器[{}:{}]steamId白名单检查启动成功。", rcon.getSocket().getInetAddress().getHostAddress(), rcon.getSocket().getPort());
				List<PlayerVo> playerVoList = ResultParseUtil.parsePlayerInfo(rcon.command(PalworldRconCommand.SHOW_PLAYERS));
				for (PlayerVo playerVo : playerVoList) {
					if (!steamIdWhitelist.contains(playerVo.getSteamId())) {
						rcon.command(PalworldRconCommand.BAN_PLAYER + " " + playerVo.getSteamId());
					}
				}
				Thread.sleep(checkCycle);
			}
		} finally {
			log.info("服务器[{}:{}]steamId白名单检查退出。", rcon.getSocket().getInetAddress().getHostAddress(), rcon.getSocket().getPort());
			Task.latch.countDown();
		}
	}
}
