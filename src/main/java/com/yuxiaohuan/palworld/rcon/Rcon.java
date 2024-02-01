package com.yuxiaohuan.palworld.rcon;

import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 此文件代码来源于<a href = "https://github.com/kr5ch/rkon-core">rkon-core</a>项目
 *
 * @author : 于小环
 * @since : 2024/1/31 10:07
 */
@Data
@Slf4j
public class Rcon {

	private final Object sync = new Object();
	private final Random rand = new Random();

	private int requestId;
	@Getter
	private Socket socket;

	@Getter
	private Charset charset = StandardCharsets.UTF_8;

	/**
	 * Create, connect and authenticate a new Rcon object
	 *
	 * @param host     Rcon server address
	 * @param port     Rcon server port
	 * @param password Rcon server password
	 */
	@SneakyThrows
	public Rcon(String host, int port, byte[] password) {
		this.connect(host, port, password);
	}

	/**
	 * Connect to a rcon server
	 *
	 * @param host     Rcon server address
	 * @param port     Rcon server port
	 * @param password Rcon server password
	 */
	public void connect(String host, int port, byte[] password) throws Exception {
		if (host == null || host.trim().isEmpty()) {
			throw new IllegalArgumentException("Host can't be null or empty");
		}

		if (port < 1 || port > 65535) {
			throw new IllegalArgumentException("Port is out of range");
		}

		// Connect to the rcon server
		synchronized (sync) {
			// New random request id
			this.requestId = rand.nextInt();

			// We can't reuse a socket, so we need a new one
			log.info("开始连接rcon[{}:{}]", host, port);
			this.socket = new Socket(host, port);
		}

		// Send the auth packet
		RconPacket res = this.send(RconPacket.SERVERDATA_AUTH, password);

		// Auth failed
		if (res.getRequestId() == -1) {
			throw new Exception("Password rejected by server");
		}
	}

	/**
	 * Disconnect from the current server
	 */
	public void disconnect() throws IOException {
		synchronized (sync) {
			this.socket.close();
		}
	}

	/**
	 * Send a command to the server
	 *
	 * @param payload The command to send
	 * @return The payload of the response
	 */
	public String command(String payload) throws Exception {
		if (payload == null || payload.trim().isEmpty()) {
			throw new IllegalArgumentException("Payload can't be null or empty");
		}

		log.info("向[{}:{}]发送命令: {}", this.socket.getInetAddress().getHostAddress(), this.socket.getPort(), payload);

		RconPacket response = this.send(RconPacket.SERVERDATA_EXECCOMMAND, payload.getBytes());

		return new String(response.getPayload(), this.getCharset());
	}

	private RconPacket send(int type, byte[] payload) throws Exception {
		synchronized (sync) {
			return RconPacket.send(this, type, payload);
		}
	}

}
