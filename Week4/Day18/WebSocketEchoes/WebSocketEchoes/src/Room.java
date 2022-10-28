import java.net.Socket;
import java.util.*;

public class Room {
	private final String roomName;
	private final Set<String> activeUsers;
	private final Set<Socket> activeSockets;
	private static final Set<Room> roomSet = new HashSet<>();
	private final ArrayList<Map<String, String>> messageQueue;
	
	Room(String roomName) {
		this.roomName = roomName;
		activeUsers = new HashSet<>();
		activeSockets = new HashSet<>();
		messageQueue = new ArrayList<>();
	}
	
	
	public String getRoomName() {
		return roomName;
	}
	
	private static synchronized boolean hasRoom(String roomName) {
		for (Room room : roomSet) {
			String currentRoomName = room.getRoomName();
			if (currentRoomName.equals(roomName)) {
				return true;
			}
		}
		return false;
	}
	
	public static synchronized Room getRoom(String roomName) {
		if(hasRoom(roomName))
		{
			for (Room room : roomSet) {
				if (room.getRoomName().equals(roomName)) {
					return room;
				}
			}
		}
		System.out.println("add room : " + roomName);
		Room newRoom = new Room(roomName);
		roomSet.add(newRoom);
		return newRoom;
	}
	
	public synchronized void addUser(String user)
	{
		System.out.println("add user: " + user);
		activeUsers.add(user);
	}
	
	public synchronized void removeUser(String user)
	{
		System.out.println("remove user: " + user);
		activeUsers.remove(user);
	}
	
	public synchronized void addSocket(Socket socket)
	{
		System.out.println("add socket: " + socket);
		activeSockets.add(socket);
	}
	
	public synchronized void removeSocket(Socket socket)
	{
		System.out.println("remove socket: " + socket);
		activeSockets.remove(socket);
	}
	
	public synchronized Set<Socket> getActiveSockets()
	{
		return activeSockets;
	}
	
	private boolean isUserInRoom(String user)
	{
		return activeUsers.contains(user);
	}
	
	public static synchronized Room getRoomByUser(String user)
	{
		for (Room room: roomSet) {
			if(room.isUserInRoom(user))
			{
				return room;
			}
		}
		throw new RuntimeException("User " + user +" is not in any room!");
	}
	
	// TODO: handle new line in message correctly so client could parse the JSON
	// TODO: fix long length exception
	public synchronized void addMessage(Map<String, String> message)
	{
		System.out.println("add message: " + WebSocketTools.stringifyJSON(message));
		messageQueue.add(message);
	}
	
	public synchronized ArrayList<Map<String, String>> getMessageQueue()
	{
		return messageQueue;
	}
}
