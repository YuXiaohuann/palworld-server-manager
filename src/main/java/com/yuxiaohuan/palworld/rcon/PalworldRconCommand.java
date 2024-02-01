package com.yuxiaohuan.palworld.rcon;

/**
 * @author : 于小环
 * @since : 2024/1/31 11:47
 */
public class PalworldRconCommand {
	/**
	 * 服务器将在{秒}后关闭
	 * <br/>
	 * Shutdown {秒} {信息}
	 */
	public static final String SHUTDOWN = "Shutdown";
	/**
	 * 强制关闭服务器
	 * <br/>
	 * DoExit
	 */
	public static final String DO_EXIT = "DoExit";
	/**
	 * 向服务器中所有玩家发送消息
	 * <br/>
	 * Broadcast {MessageText}
	 */
	public static final String BROADCAST = "Broadcast";
	/**
	 * 从服务器中踢出玩家
	 * <br/>
	 * KickPlayer {SteamID}
	 */
	public static final String KICK_PLAYER = "KickPlayer";
	/**
	 * 从服务器中封禁玩家
	 * <br/>
	 * BanPlayer {SteamID}
	 */
	public static final String BAN_PLAYER = "BanPlayer";
	/**
	 * 传送到目标玩家
	 * <br/>
	 * TeleportToPlayer {SteamID}
	 */
	public static final String TELEPORT_TO_PLAYER = "TeleportToPlayer";
	/**
	 * 将目标玩家传送到身边
	 * <br/>
	 * TeleportToMe {SteamID}
	 */
	public static final String TELEPORT_TO_ME = "TeleportToMe";
	/**
	 * 显示所有已连接玩家信息
	 * <br/>
	 * ShowPlayers
	 */
	public static final String SHOW_PLAYERS = "ShowPlayers";
	/**
	 * 显示服务器信息
	 * <br/>
	 * Info
	 */
	public static final String INFO = "Info";
	/**
	 * 保存游戏
	 * <br/>
	 * Save
	 */
	public static final String SAVE = "Save";
}
