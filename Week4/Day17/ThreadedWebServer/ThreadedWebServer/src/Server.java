import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;
	
	public Server(ServerSocket ss) {
		serverSocket = ss;
	}
	
	public void runServer() {
		while (true) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				Thread thread = new Thread(new ServerThread(socket));
				thread.start();
				// thread.join();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
}

