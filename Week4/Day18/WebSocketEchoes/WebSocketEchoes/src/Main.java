import java.io.IOException;
import java.net.ServerSocket;

public class Main {
	public static void main(String[] args) {
		// listen at port 8080
		try (ServerSocket serverSocket = new ServerSocket(8080)) {
			Server server = new Server(serverSocket);
			server.runServer();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}