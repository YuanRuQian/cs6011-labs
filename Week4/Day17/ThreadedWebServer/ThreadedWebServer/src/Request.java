import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Request {
	private final Socket socket;
	private final Map<String, String> requestHeader = new HashMap<>();
	private String fileName = "";
	private Boolean isFileValid;
	private String httpVersion = "";
	
	public Request(Socket s) {
		socket = s;
	}
	
	public String getFilePath(String fn) {
		return "src" + fn;
	}
	
	private void checkIfFileExists() {
		File file = new File(getFilePath(fileName));
		isFileValid = file.exists() && !file.isDirectory();
	}
	
	private void printKeyValueMap() {
		requestHeader.forEach((key, value) -> System.out.println(key + " : " + value));
	}
	
	private void readRequestHeader(Scanner requestHeaderScanner) {
		// read the first line for the fileName
		// e.g. GET /tutorials/other/top-20-mysql-best-practices/ HTTP/1.1
		String GETInfo = requestHeaderScanner.nextLine();
		System.out.println(GETInfo + " thread: " + Thread.currentThread().threadId());
		String[] GETInfoArray = GETInfo.split("\s");
		fileName = GETInfoArray[1];
		checkIfFileExists();
		httpVersion = GETInfoArray[2];
		// keep reading the following key-value pairs and store them in requestHeader
		while (requestHeaderScanner.hasNextLine()) {
			// e.g. Host: localhost
			String keyValueString = requestHeaderScanner.nextLine();
			// end at the blank line
			if (keyValueString.isEmpty()) {
				break;
			}
			String[] keyValuePair = keyValueString.split(": ");
			String key = keyValuePair[0];
			String value = keyValuePair[1];
			requestHeader.put(key, value);
		}
		System.out.println("The request header map:");
		printKeyValueMap();
	}
	
	public Boolean getIsFileValid() {
		return isFileValid;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getHttpVersion() {
		return httpVersion;
	}
	
	public void getRequest() throws IOException {
		InputStream requestStream = null;
		try {
			requestStream = socket.getInputStream();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			if (requestStream != null) {
				Scanner requestHeaderScanner = new Scanner(requestStream);
				if (requestHeaderScanner.hasNextLine()) {
					readRequestHeader(requestHeaderScanner);
				} else {
					throw new IOException("request header no content");
				}
			} else {
				throw new IOException("the request stream is null");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
