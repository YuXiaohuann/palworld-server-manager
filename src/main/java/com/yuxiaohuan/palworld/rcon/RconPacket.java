package com.yuxiaohuan.palworld.rcon;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 此文件代码来源于<a href = "https://github.com/kr5ch/rkon-core">rkon-core</a>项目
 * <br/>
 * 由于palworld的rcon对中文支持不友好
 * <br/>
 * 所以魔改了一些地方
 * <br/>
 * 详情请见{@link #read(Socket)}
 *
 * @author : 于小环
 * @since : 2024/1/31 10:09
 */
@Getter
@Slf4j
public class RconPacket {

	public static final int SERVERDATA_EXECCOMMAND = 2;
	public static final int SERVERDATA_AUTH = 3;

	private final int requestId;
	private final int type;
	private final byte[] payload;

	private RconPacket(int requestId, int type, byte[] payload) {
		this.requestId = requestId;
		this.type = type;
		this.payload = payload;
	}

	/**
	 * Send a Rcon packet and fetch the response
	 *
	 * @param rcon    Rcon instance
	 * @param type    The packet type
	 * @param payload The payload (password, command, etc.)
	 * @return A RconPacket object containing the response
	 */
	protected static RconPacket send(Rcon rcon, int type, byte[] payload) throws Exception {
		try {
			RconPacket.write(rcon.getSocket().getOutputStream(), rcon.getRequestId(), type, payload);
		} catch (SocketException se) {
			// Close the socket if something happens
			rcon.getSocket().close();

			// Rethrow the exception
			throw se;
		}

		return RconPacket.read(rcon.getSocket());
	}

	/**
	 * Write a rcon packet on an outputstream
	 *
	 * @param out       The OutputStream to write on
	 * @param requestId The request id
	 * @param type      The packet type
	 * @param payload   The payload
	 */
	private static void write(OutputStream out, int requestId, int type, byte[] payload) throws IOException {
		int bodyLength = RconPacket.getBodyLength(payload.length);
		int packetLength = RconPacket.getPacketLength(bodyLength);

		ByteBuffer buffer = ByteBuffer.allocate(packetLength);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		buffer.putInt(bodyLength);
		buffer.putInt(requestId);
		buffer.putInt(type);
		buffer.put(payload);

		// Null bytes terminators
		buffer.put((byte) 0);
		buffer.put((byte) 0);

		// Woosh!
		out.write(buffer.array());
		out.flush();
	}

	/**
	 * Read an incoming rcon packet
	 *
	 * @param socket The InputStream to read on
	 * @return The read RconPacket
	 */
	private static RconPacket read(Socket socket) throws Exception {
		InputStream in = socket.getInputStream();
		// Header is 3 4-bytes ints
		byte[] header = new byte[4 * 3];

		// Read the 3 ints
		in.read(header);

		// Use a bytebuffer in little endian to read the first 3 ints
		ByteBuffer buffer = ByteBuffer.wrap(header);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		int length = buffer.getInt();
		int requestId = buffer.getInt();
		int type = buffer.getInt();

		// Payload size can be computed now that we have its length
		int payloadSize = length - 4 - 4 - 2;
		byte[] payload = new byte[payloadSize];

		/*DataInputStream dis = new DataInputStream(in);

		// Read the full payload
		dis.readFully(payload);

		// Read the null bytes
		dis.read(new byte[2]);*/

		//以下代码为魔改代码 用于解决palworld rcon对中文支持不友好问题
		if (payloadSize > 0) {
			List<Byte> byteList = new ArrayList<>();
			int nullCount = 0;
			while (nullCount < 2) {
				byte read = (byte) in.read();
				if (read == 0x00) {
					nullCount++;
				} else {
					nullCount = 0;
				}
				byteList.add(read);
			}
			if (byteList.size() >= 2) {
				byteList = byteList.subList(0, byteList.size() - 2);
			}
			payload = ArrayUtils.toPrimitive(byteList.toArray(new Byte[0]));

			log.info("从[{}:{}]收到响应: {}", socket.getInetAddress().getHostAddress(), socket.getPort(), new String(payload));

		} else {
			in.read(new byte[2]);
		}


		return new RconPacket(requestId, type, payload);
	}

	private static int getPacketLength(int bodyLength) {
		// 4 bytes for length + x bytes for body length
		return 4 + bodyLength;
	}

	private static int getBodyLength(int payloadLength) {
		// 4 bytes for requestId, 4 bytes for type, x bytes for payload, 2 bytes for two null bytes
		return 4 + 4 + payloadLength + 2;
	}
}
