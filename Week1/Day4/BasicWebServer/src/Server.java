import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	static private ServerSocket serverSocket;
	public Server( ServerSocket ss)
	{
		serverSocket = ss;
	}
	
	public void runServer()
	{
		while (true) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				Request request = new Request(socket);
				request.getRequest();
				Response response = new Response(socket, request.getIsFileValid(), request.getFileName(), request.getHttpVersion());
				response.handleResponse();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
}

