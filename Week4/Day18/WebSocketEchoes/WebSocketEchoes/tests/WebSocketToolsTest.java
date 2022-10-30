import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketToolsTest {
	@Test
	void testGetWebSocketResponseKey() throws NoSuchAlgorithmException {
		String requestKey = "dGhlIHNhbXBsZSBub25jZQ==";
		String expectedResponseKey = "s3pPLMBiTxaQ9kYGzzhZRbK+xOo=";
		String actualResponseKey = WebSocketTools.getWebSocketResponseKey(requestKey);
		assertEquals(expectedResponseKey, actualResponseKey);
	}
	
	@Test
	void testGetPayloadAndGetResponseFrame() throws IOException {
		// Unmasked text message
		byte[] test1bytes = {(byte) 0x81, 0x05, 0x48, 0x65, 0x6c, 0x6c, 0x6f};
		ByteArrayInputStream test1 = new ByteArrayInputStream(test1bytes);
 		String payload1 = "Hello";
		assertEquals(payload1, WebSocketTools.getRequest(new DataInputStream(test1)));
		
		byte[] test1Results = WebSocketTools.getResponseFrame(payload1);
		assertArrayEquals(test1bytes, test1Results);
		
		// Masked text message
		byte[] test2bytes = {(byte) 0x81, (byte) 0x85, 0x37, (byte) 0xfa,0x21, 0x3d ,0x7f, (byte) 0x9f, 0x4d, 0x51, 0x58 };
		ByteArrayInputStream test2 = new ByteArrayInputStream(test2bytes);
		String payload2 = "Hello";
		assertEquals(payload2, WebSocketTools.getRequest(new DataInputStream(test2)));
		
		// 256 bytes binary message in a single unmasked frame
		byte[] test3bytes = new byte[256 + 4];
		test3bytes[0] = (byte) 0x81;
		test3bytes[1] = 0x7E;
		test3bytes[2] = 0x01;
		test3bytes[3] = 0x00;
		for (int i = 4; i < 256 + 4; i++) {
			test3bytes[i] = 0x6f;
		}
		ByteArrayInputStream test3 = new ByteArrayInputStream(test3bytes);
		String payload3 = "o".repeat(256);
		assertEquals (payload3, WebSocketTools.getRequest(new DataInputStream(test3)));
		
		
		// 64 KiB binary message in a single unmasked frame
		byte[] test4bytes = new byte[10 + 65536];
		test4bytes[0] = (byte) 0x81;
		test4bytes[1] = 0x7F;
		for(int i=2; i<2+5; i++)
		{
			test4bytes[i] = 0x00;
		}
		test4bytes[7] = 0x01;
		test4bytes[8] = test4bytes[9] = 0x00;
		for (int i = 10; i < 65536 + 10; i++) {
			test4bytes[i] = 0x65;
		}
		ByteArrayInputStream test4 = new ByteArrayInputStream(test4bytes);
		String payload4 = "e".repeat(65536);
		assertEquals (payload4, WebSocketTools.getRequest(new DataInputStream(test4)));
	}
	
	@Test
	void testJSONStringify()
	{
		Map<String, String> json = new HashMap<>();
		json.put("type", "join");
		json.put("user", "Lydia");
		json.put("room", "room42");
		String expected = "{\"type\":\"join\",\"user\":\"Lydia\",\"room\":\"room42\"}";
		assertEquals(expected, WebSocketTools.stringifyJSON(json));
	}
	
	@Test
	void testJSONParse()
	{
		Map<String, String> testJSON = new HashMap<>();
		testJSON.put("type", "join");
		testJSON.put("user", "Lydia");
		testJSON.put("room", "room42");
		testJSON.put("timestamp", "1667017937122");
		String stringJSON = WebSocketTools.stringifyJSON(testJSON);
		Map<String, String> newJSON = WebSocketTools.parseJSON(stringJSON);
		assertArrayEquals(testJSON.keySet().toArray(), newJSON.keySet().toArray());
		assertArrayEquals(testJSON.values().toArray(), newJSON.values().toArray());
	}
}