// Name: Ruqian Yuan
package Rooms;

import Game.Adventure;
import Heartbeat.TimerTask;
import Items.Item;
// Written by Ruqian Yuan
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StrangeRoom extends Room {
	
	public StrangeRoom(ArrayList<Room> originallyConnectedRooms) {
		super("Strange Room", "Welcome to the strange world! The doors connected are changing all the time so you might not get to where you want to go, be careful!");
		TimerTask magicalPortalDestinationUpdater = new TimerTask((v) -> {
			heartbeat();
			return null;
		});
		originallyConnectedRooms.forEach(this::addConnection);
		Timer timer = new Timer();
		timer.schedule(magicalPortalDestinationUpdater, 4200, 4200);
		Item cabinet = new Item("Cabinet", "A locked cabinet");
		addItem(cabinet);
		Item keyToCabinet = new Item("Cabinet Key", "A rusty key to the cabinet. Yet it is so rusty that it works almost once in three times...");
		addItem(keyToCabinet);
		Item computer = new Item("Computer", "A computer connected to the Internet.");
		addItem(computer);
	}
	
	@Override
	public void heartbeat() {
		ArrayList<Room> roomList = getRoomList();
		Random random = new Random(System.currentTimeMillis());
		for (Room room : roomList) {
			if (random.nextBoolean()) {
				addConnection(room);
			} else {
				removeConnection(room);
			}
		}
	}
	
	@Override
	public boolean handleCommand(String[] subcommands) {
		if( subcommands.length <= 1 ) {
			return false;
		}
		String cmd  = subcommands[0];
		String attr = subcommands[1];
		
		if( cmd.equals( "unlock" ) && attr.equals( "Cabinet")) {
			handleUnlockCabinet();
			return true;
		}
		else if(cmd.equals("read") && attr.equals("Diary")) {
			handleReadDiary();
			return true;
		}
		else if(cmd.equals("decode") && attr.equals("Diary"))
		{
			handleDecodeDiary();
			return true;
		}
		return false;
	}
	
	private String getDiaryMessage()
	{
		boolean hasDiary = hasDiary();
		if(!hasDiary)
		{
			System.out.println("Without the diary at hand, you can't read the content.");
			return "";
		}
		String messageToEncrypt = """
				To the left to right I sway all night
				To the left to right I feel the vibe
				To the left to right I come alive""";
		return encryptedStringByBase64(messageToEncrypt);
	}
	
	
	private String encryptedStringByBase64(String originalMessage)
	{
		return Base64.getEncoder().encodeToString(originalMessage.getBytes());
	}
	
	private String decodeBase64EncryptedString(String encodedMessage) {
		return new String(Base64.getDecoder().decode(encodedMessage), StandardCharsets.UTF_8);
	}
	
	private void handleDecodeDiary() {
		boolean hasDiary = hasDiary();
		if(!hasDiary)
		{
			System.out.println("You can't remember the encrypted content in the diary cuz it's too long. Maybe pick it up and try again?");
		}
		else
		{
			System.out.println("You turn on the computer. Yes...... The power of the Internet!");
			System.out.println("You find an online decoding tool and get the decoded content:");
			System.out.println(decodeBase64EncryptedString(getDiaryMessage()));
		}
	}
	
	private void handleReadDiary() {
		String diaryMessage = getDiaryMessage();
		if(diaryMessage.isEmpty())
		{
			return;
		}
		System.out.println("The diary says:");
		System.out.println(getDiaryMessage());
		System.out.println("It seems like someone encrypted the message with base 64 algorithm!");
	}
	
	private boolean hasDiary() {
		boolean has = false;
		for( Item item : Adventure.inventory ) {
			if( item.getName().equals( "Diary" ) ) {
				has = true;
				break;
			}
		}
		return has;
	}
	
	private void handleUnlockCabinet() {
		boolean hasCabinetKey = hasCabinetKey();
		if( hasCabinetKey ) {
			Random random = new Random(System.currentTimeMillis());
			int randomizedInt = random.nextInt(3);
			if( randomizedInt >= 1)
			{
				System.out.println("You just unlocked the cabinet. There is a diary inside.");
				Item diary = new Item("Diary", "A secret diary encrypted with base 64");
				addItem(diary);
			}
			else {
				System.out.println("Oops the key is too rusty please try again");
			}
		}
		else {
			System.out.println( "You don't have a key to the cabinet." );
		}
	}
	
	private boolean hasCabinetKey() {
		boolean hasKey = false;
		for( Item item : Adventure.inventory ) {
			if( item.getName().equals( "Cabinet Key" ) ) {
				hasKey = true;
				break;
			}
		}
		return hasKey;
	}
	
	@Override
	public Item getItem(String name) {
		if(name.equals("Computer") || name.equals("Cabinet"))
		{
			System.out.println("Oops... You can't pick up the " + name + ", just leave it there.");
			return null;
		}
		return super.getItem(name);
	}
	
	
}
