import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class Response {
	private static Socket socket;
	private static Boolean isFileValid;
	private static String fileName;
	private static String httpVersion;
	private static final String fallback404PageFileName = "/404Page.html";
	
	public Response(Socket s, Boolean ifv, String fn, String hv) {
		socket = s;
		isFileValid = ifv;
		fileName = fn;
		httpVersion = hv;
	}
	
	private static String getFinalFilePath() {
		// if the file does not exist, return 404 fall back page
		return Request.getFilePath(isFileValid ? fileName : fallback404PageFileName);
	}
	private static String getFallBackPageHTML(String errorMessage) {
		return """
				<!doctype html>
				 <html lang="en">
				 <head>
				     <meta charset="utf-8"/>
				     <link rel="icon" href="favicon.webp"/>
				     <meta name="viewport" content="width=device-width,initial-scale=1"/>
				     <meta name="theme-color" content="#000000"/>
				     <meta name="description" content="Lydia Yuan's Profile"/>
				     <title>404 NOT FOUND</title>
				     <link href="style.css" rel="stylesheet">
				 </head>
				 <body>
				 <div id="root">
				     <div class="App"><img src="profile.svg"
				                           class="profile-pic" alt="profile pic">
				         <h2> Hmm... What you want is not here</h2>
				         <h3> Error Message From the Server: </h3>
				         <p>
				          """ +
				errorMessage
				+
				"""
						</p>
						    </div>
						</div>
						</body>
						</html>
						""";
	}
	
	private static void write404fallbackPage() {
		try {
			FileInputStream fileInputStream = new FileInputStream(Request.getFilePath(fileName));
		} catch (FileNotFoundException e) {
			try {
				FileWriter fileWriter = new FileWriter(Request.getFilePath(fallback404PageFileName));
				fileWriter.write(getFallBackPageHTML(e.getMessage()));
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	private static String getStatusCodeInfo() {
		String statusCodeInfo = isFileValid ? "200 OK" : "404 NOT FOUND";
		return httpVersion + " " + statusCodeInfo;
	}
	
	private static String getContentType() {
		File file = new File(getFinalFilePath());
		String fileContentType = "";
		try {
			fileContentType = Files.probeContentType(file.toPath());
		} catch (IOException e) {
			System.out.println("Failed: unknown content of " + fileName);
			e.printStackTrace();
		}
		return "content-type: " + fileContentType;
	}
	
	private static void sendResponseHeader(PrintWriter printWriter) {
		if (!isFileValid) write404fallbackPage();
		printWriter.println(getStatusCodeInfo());
		printWriter.println(getContentType());
		// add blank line to indicate the start of the requested file content
		printWriter.println("");
	}
	
	private static void sendResponseBody(OutputStream socketOutputStream) {
		try {
			InputStream finalFileStream = new FileInputStream(getFinalFilePath());
			finalFileStream.transferTo(socketOutputStream);
			finalFileStream.close();
		} catch (FileNotFoundException e) {
			// if the file does not exist then return a fallback page to display server error message
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void sendResponse(Socket socket) {
		try {
			OutputStream socketOutputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(socketOutputStream, true);
			sendResponseHeader(printWriter);
			sendResponseBody(socketOutputStream);
			printWriter.close();
			socketOutputStream.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void handleResponse() {
		sendResponse(socket);
	}
	
}
