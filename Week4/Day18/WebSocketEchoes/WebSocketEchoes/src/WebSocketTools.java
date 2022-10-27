

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WebSocketTools {
	private static final String magicString = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	
	public static String getWebSocketResponseKey(String requestKey) throws NoSuchAlgorithmException {
		String concatenatedString = requestKey + magicString;
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		byte[] sha1Results = sha1.digest(concatenatedString.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(sha1Results);
	}
	
	
	public static boolean isWebSocket(Map<String, String> requestHeader) {
		return requestHeader.containsKey("Upgrade") && requestHeader.get("Upgrade").equals("websocket");
	}
	
	private static boolean isMasked(ArrayList<Byte> message) {
		return (message.get(1) & 0x80) != 0;
	}
	
	
	public static String getWebSocketResponsePayload(ArrayList<Byte> message, int payloadStart, int payloadLength) {
		boolean isMasked = isMasked(message);
		byte[] payload = new byte[payloadLength];
		for (int i = 0; i < payloadLength; i++) {
			payload[i] = message.get(i + payloadStart);
		}
		if (isMasked) {
			// decode the masked message
			byte[] masks = new byte[4];
			for (int i = 0; i < 4; i++) {
				masks[i] = message.get(i + payloadStart - 4);
			}
			for (int i = 0; i < payloadLength; i++) {
				payload[i] = (byte) (payload[i] ^ masks[i % 4]);
			}
		}
		return new String(payload, StandardCharsets.UTF_8);
	}
	
	private static ArrayList<Byte> getBytesFromNumber(int number) {
		ArrayList<Byte> results = new ArrayList<>();
		while (number > 0) {
			byte current = (byte) (number & 0xff);
			number = number >> 8;
			results.add(current);
		}
		Collections.reverse(results);
		return results;
	}
	
	public static byte[] getResponseFrame(String response) throws UnsupportedEncodingException {
		byte[] payload = response.getBytes(StandardCharsets.UTF_8);
		int payloadLength = payload.length;
		ArrayList<Byte> responseFrame = new ArrayList<>();
		// FIN / RSV*3 / OPCODE
		byte firstByte = (byte) 0x81;
		responseFrame.add(firstByte);
		if (payloadLength <= 0x7f) {
			byte secondByte = (byte) payloadLength;
			responseFrame.add(secondByte);
		} else if (payloadLength <= 0xffff) {
			byte secondByte = 126;
			responseFrame.add(secondByte);
			ArrayList<Byte> lengthBytes = getBytesFromNumber(payloadLength);
			responseFrame.addAll(lengthBytes);
		} else {
			byte secondByte = 127;
			responseFrame.add(secondByte);
			ArrayList<Byte> lengthBytes = getBytesFromNumber(payloadLength);
			responseFrame.addAll(lengthBytes);
		}
		for (byte p :
				payload) {
			responseFrame.add(p);
		}
		byte[] results = new byte[responseFrame.size()];
		for (int i = 0; i < responseFrame.size(); i++) {
			results[i] = responseFrame.get(i);
		}
		return results;
	}
	
	public static void makeHandshake(Socket socket, Request request) {
		OutputStream out;
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		byte[] response;
		try {
			response = ("HTTP/1.1 101 Switching Protocols\r\n"
					+ "Connection: Upgrade\r\n"
					+ "Upgrade: websocket\r\n"
					+ "Sec-WebSocket-Accept: "
					+ WebSocketTools.getWebSocketResponseKey(request.getWebsocketRequestKey())
					+ "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		try {
			out.write(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("handshake with the client");
	}
	
	public static String getRequest(DataInputStream dataInputStream) throws IOException {
		ArrayList<Byte> incomingBytes = new ArrayList<>();
		// read first 2 bytes
		for (int i = 0; i < 2; i++) {
			incomingBytes.add(dataInputStream.readByte());
		}
		boolean isMasked = isMasked(incomingBytes);
		int byte1PayloadLength = incomingBytes.get(1) & 0x7f;
		int payloadLength = 0;
		int payloadStart;
		if (byte1PayloadLength < 126) {
			// payload length in byte 1
			payloadLength = byte1PayloadLength;
			payloadStart = 2;
		} else if (byte1PayloadLength == 126) {
			// extended payload length in byte 2 ~ 3
			// read next 2 bytes
			for (int i = 2; i <= 3; i++) {
				incomingBytes.add(dataInputStream.readByte());
				payloadLength = (payloadLength << 8) | incomingBytes.get(i);
			}
			payloadStart = 4;
		} else {
			// extended payload length in byte 2 ~ 9
			// read next 8 bytes
			for (int i = 2; i <= 9; i++) {
				incomingBytes.add(dataInputStream.readByte());
				payloadLength = (payloadLength << 8) | incomingBytes.get(i);
			}
			payloadStart = 10;
		}
		if (isMasked) {
			// read next 4 bytes as masks
			for (int i = 0; i < 4; i++) {
				incomingBytes.add(dataInputStream.readByte());
			}
			payloadStart += 4;
		}
		// read next payloadLength bytes for the payload
		for (int i = 0; i < payloadLength; i++) {
			incomingBytes.add(dataInputStream.readByte());
		}
		return getWebSocketResponsePayload(incomingBytes, payloadStart, payloadLength);
	}
	
	public static void sendResponse(String response, Socket socket) throws IOException {
		OutputStream out = null;
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		out.write(getResponseFrame(response));
	}
	
	private static Map<String, String> getResponseJSON(String request) {
		String[] incomingRequests = request.split("\s");
		String type = incomingRequests[0];
		Map<String, String> responseJSON = new HashMap<>();
		switch (type) {
			case "join":
				responseJSON.put("type", "join");
				responseJSON.put("user", incomingRequests[1]);
				responseJSON.put("room", incomingRequests[2]);
				break;
			case "leave":
				responseJSON.put("type", "leave");
				responseJSON.put("user", incomingRequests[1]);
				responseJSON.put("room", incomingRequests[2]);
				break;
			default:
				responseJSON.put("type", "message");
				responseJSON.put("message", "message");
		}
		return responseJSON;
	}
	
	public static String stringifyJSON(Map<String, String> json) {
		String result = "";
		int i = 0;
		for (String key : json.keySet()) {
			result += "\"" + key + "\":" + "\"" + json.get(key) + "\"";
			i++;
			if (i < json.size()) {
				result += ",";
			}
		}
		return "{" + result + "}";
	}
	
	public static void handleResponse(Socket socket, String request) throws IOException {
		Map<String, String> responseJSON = getResponseJSON(request);
		String JSONString = stringifyJSON(responseJSON);
		sendResponse(JSONString, socket);
	}
	
}
