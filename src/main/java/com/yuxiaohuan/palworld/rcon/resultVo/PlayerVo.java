package com.yuxiaohuan.palworld.rcon.resultVo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 在线玩家信息
 *
 * @author : 于小环
 * @since : 2024/1/31 17:49
 */
@Data
@AllArgsConstructor
public class PlayerVo {
	private String name;
	private String playerUid;
	private String steamId;
}
