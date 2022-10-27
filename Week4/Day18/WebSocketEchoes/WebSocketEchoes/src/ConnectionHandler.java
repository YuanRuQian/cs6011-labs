import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class ConnectionHandler implements Runnable {
	private Socket socket;
	
	public ConnectionHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		Request request = new Request(socket);
		request.getRequest();
		if (request.getIsWebSocket()) {
			// if it is a web socket connection, first make handshake, then repeat the listen & send cycle
			WebSocketTools.makeHandshake(socket, request);
			while (true) {
				try {
					InputStream inputStream  = socket.getInputStream();
					DataInputStream dataInputStream = new DataInputStream(inputStream);
					String wsRequest = WebSocketTools.getRequest(dataInputStream);
					System.out.println("Get incoming request: " + wsRequest);
					WebSocketTools.handleResponse(socket, wsRequest);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			// if it is a HTTP request, just send back the response
			HTTPResponse response = new HTTPResponse(socket, request);
			response.handleResponse();
			try {
				socket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
