// Updated by Ruqian Yuan
package Rooms;

import Items.Item;

import java.util.ArrayList;

public class Room {

    private final String name_;
    private final String description_;
   
    protected ArrayList<Item> items_ = new ArrayList<>();
    ArrayList<Room> doors_ = new ArrayList<>();
    
    protected static ArrayList<Room> roomList_ = new ArrayList<>();

    public Room( String name, String description ) {
        name_ = name;
        description_ = description;
        roomList_.add(this);
    }
    
    private void printDoorList()
    {
        int num = 1;
        for( Room r : doors_ ) {
            System.out.println( "   " + num + " - " + r.name_ );
            num++;
        }
    }

    public void print() {
        System.out.println( "You are in the " + name_ + ", " + description_ + "\n" );
        System.out.println( "You see the following doors:" );
        printDoorList();
        if( items_.size() > 0 ) {
            System.out.println( "You see the following items: " );
            for( Item item : items_ ) {
                System.out.println( "   " + item + ": "  + item.getDescription() );
            }
        }
    }

    public void addConnection( Room room ) {
        if( !doors_.contains( room )) {
            doors_.add( room );
        }
        if( !room.doors_.contains( this ) ) {
            room.doors_.add( this );
        }
    }
    
    public void removeConnection( Room room ) {
        doors_.remove( room );
        room.doors_.remove( this );
    }

    public Room goThroughDoor( int doorNum ) {
        // doorNum comes in 1-based
        // doors_ is 0-based
        if(isConnectedRoomsStable(this))
        {
            System.out.println("This room is connected to the Strange Room, which is not stable.");
            System.out.println("So make sure you are toying with the latest map");
            printDoorList();
        }
        
        doorNum = doorNum - 1;

        if( doorNum >= doors_.size() || doorNum < 0 ) {
            System.out.println( "There is no such door." );
            return null;
        }
        else {
            Room newRoom = doors_.get( doorNum );
            System.out.println( "You enter the " + newRoom.name_ );
            return newRoom;
        }
    }

    public void addItem( Item item ) {
        items_.add( item );
    }

    public String getName() { return name_; }

    // Override this method if you want your room to be notified each time a second passes.
    public void heartbeat() {}

    // For most rooms, nothing happens when a player leaves...
    public void playerLeft() {}

    // For most rooms, nothing happens when a player enters...
    public void playerEntered() {
    }

    public Item getItem( String name ) {
        for( Item item : items_ ) {
            if( item.getName().equals( name ) ) {
                items_.remove( item );
                return item;
            }
        }
        System.out.println( "There is no " + name + " here." );
        return null;
    }
    public boolean handleCommand(String[] subcommands) {
        return false;
    }
    
    public ArrayList<Room> getRoomList()
    {
        return roomList_;
    }
    
    public boolean isConnectedTo(String roomName) {
        boolean isConnectedToRoom = false;
        for(Room room: doors_)
        {
            if(room.getName().equals(roomName))
            {
                isConnectedToRoom = true;
                break;
            }
        }
        return isConnectedToRoom;
    }
    
    public static boolean isConnectedRoomsStable(Room room)
    {
        if(room.getName().equals("Strange Room"))
        {
            return true;
        }
        return room.isConnectedTo("Strange Room");
    }
}
