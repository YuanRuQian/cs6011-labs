import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
	private final Socket socket;
	
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
					if(wsRequest.equals("close"))
					{
						socket.close();
						break;
					}
					System.out.println("Get incoming request: " + wsRequest);
					WebSocketTools.handleResponse(socket, wsRequest);
				} catch (IOException e) {
					// this will happen if the browser closes...
					throw new RuntimeException(e);
				}
			}
		} else {
			// if it is an HTTP request, just send back the response
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
