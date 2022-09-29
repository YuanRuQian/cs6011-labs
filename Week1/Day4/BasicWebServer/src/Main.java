import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static final Map<String, String> requestHeader = new HashMap<>();
	private static String fileName = "";
	private static Boolean isFileValid = false;
	private static String httpVersion = "";
	private static final String fallback404PageFileName = "/404Page.html";
	
	private static String getFilePath(String fn) {
		return "src" + fn;
	}
	
	private static Boolean checkIfFileExists() {
		File file = new File(getFilePath(fileName));
		return file.exists() && !file.isDirectory();
	}
	
	private static void printKeyValueMap() {
		Main.requestHeader.forEach((key, value) -> System.out.println(key + " : " + value));
	}
	
	private static void readRequestHeader(Scanner requestHeaderScanner) {
		// read the first line for the fileName
		// e.g. GET /tutorials/other/top-20-mysql-best-practices/ HTTP/1.1
		String GETInfo = requestHeaderScanner.nextLine();
		System.out.println(GETInfo);
		String[] GETInfoArray = GETInfo.split(" ");
		fileName = GETInfoArray[1];
		isFileValid = checkIfFileExists();
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
	
	private static String getStatusCodeInfo() {
		String statusCodeInfo = isFileValid ? "200 OK" : "404 NOT FOUND";
		return httpVersion + " " + statusCodeInfo;
	}
	
	private static String getFinalFilePath() {
		// if the file does not exist, return 404 fall back page
		return getFilePath(isFileValid ? fileName : fallback404PageFileName);
	}
	
	private static String getContentType() throws IOException {
		File file = new File(getFinalFilePath());
		String fileContentType = Files.probeContentType(file.toPath());
		return "content-type: " + fileContentType;
	}
	
	private static void sendResponseHeader(PrintWriter printWriter) throws IOException {
		printWriter.println(getStatusCodeInfo());
		printWriter.println(getContentType());
		// add blank line to indicate the start of the requested file content
		printWriter.println("");
	}
	
	public static void main(String[] args) {
		// listen at port 8080
		try (ServerSocket serverSocket = new ServerSocket(8080)) {
			// keep listening to any requests
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream requestStream;
				requestStream = socket.getInputStream();
				Scanner requestHeaderScanner = new Scanner(requestStream);
				readRequestHeader(requestHeaderScanner);
				OutputStream socketOutputStream = socket.getOutputStream();
				PrintWriter printWriter = new PrintWriter(socketOutputStream, true);
				System.out.println("final path: " + getFinalFilePath());
				sendResponseHeader(printWriter);
				InputStream finalFileStream = new FileInputStream(getFinalFilePath());
				finalFileStream.transferTo(socketOutputStream);
				requestHeaderScanner.close();
				finalFileStream.close();
				printWriter.close();
				socketOutputStream.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}