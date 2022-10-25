import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable {
	private final Socket socket;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		Request request = new Request(socket);
		try {
			request.getRequest();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		Response response = new Response(socket, request);
		response.handleResponse();
	}
}
