// Updated by Ruqian Yuan
package Game;

import Items.Item;
import Rooms.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Adventure {

    public static ArrayList<Item> inventory = new ArrayList<>();
    private static Room currentRoom = null;
    
    private static boolean done = false;
    public static void main( String[] args ) {

        System.out.println( "Server running in directory: " + System.getProperty( "user.dir" ) );

        /////////////////////////////
        // Create the house...

        Room outside  = new Room( "Outside","The front of a large mansion." );
        Room entrance = new Room( "Entrance", "A small dusty foyer." );
        Room hall     = new Room( "Grand Hall", "A large hall made for dancing." );
        Room kitchen  = new Room( "Kitchen", "An old fashioned kitchen." );
        Room stairs   = new Room( "Stairs", "The main staircase." );
        Room bedroom  = new Room( "Master Bedroom", "A large and ornate bedroom." );
        Room balcony  = new Room( "Balcony", "A balcony overlooking lush gardens." );
        currentRoom = entrance;
        Room cell = new Cell();
        ArrayList<Room> roomsOriginallyConnectedToStrangeRoom = new ArrayList<>();
        roomsOriginallyConnectedToStrangeRoom.add(entrance);
        roomsOriginallyConnectedToStrangeRoom.add(hall);
        Room strangeRoom = new StrangeRoom(roomsOriginallyConnectedToStrangeRoom);
        Room laboratory = new Laboratory();
        Room rainbowSlide = new RainbowSlide();
        Room sunflowerMaze = new SunflowerMaze();
        Room yellowBrickRoad = new YellowBrickRoad();
        entrance.addConnection(sunflowerMaze);
        entrance.addConnection(rainbowSlide);
        entrance.addConnection( cell );
        entrance.addConnection(laboratory);
        entrance.addConnection( outside );
        entrance.addConnection( hall );
        entrance.addConnection(yellowBrickRoad);
        hall.addConnection( kitchen );
        hall.addConnection( stairs );
        stairs.addConnection( bedroom );
        bedroom.addConnection( balcony );

        /////////////////////////////
        // Create Items in the house...

        Item key = new Item( "Key", "A shiny key" );
        entrance.addItem( key );

        //////////////////////////////////////////////////////////////
        // Start the game....

        System.out.println( "Welcome to Game.Adventure 2022" );
        System.out.println( "What would you like to do?" );

        Scanner sc = new Scanner( System.in );
        
        while( !done ) {

            String command = sc.nextLine();

            String [] subcommands = command.split( " ", 2 );

            if( command.equals("") || command.equals( "?" ) || command.equals( "help" ) ) {
                System.out.println("""
                        Available commands:
        
                            ?, help - get this help.
                            l, look - look around.
                            i, inventory - display inventory.
                            get <item> - pick up an item.
                            unlock <item> - unlock an item like a cabinet
                            read <item> - read the content of an item
                            decode <item> - decode the content of an item
                        """);
            }
            else if( Character.isDigit( command.charAt(0) ) ){
                int doorNum = Integer.parseInt( command );
                Room newRoom = currentRoom.goThroughDoor( doorNum );
                if( newRoom != null ) {
                    currentRoom.playerLeft();
                    newRoom.playerEntered();
                    currentRoom = newRoom;
                }
            }
            else if( command.equals( "l" ) || command.equals( "look" ) ) {
                currentRoom.print();
            }
            else if( command.equals( "i" ) || command.equals( "inventory" ) ) {
                if( inventory.size() > 0 ) {
                    System.out.println( "You are carrying:" );
                    for( Item item : inventory ) {
                        System.out.println( "    " + item );
                    }
                }
                else {
                    System.out.println( "You are not carrying anything." );
                }
            }
            else if( subcommands.length >= 2 && subcommands[0].equals( "get") ) {
                String itemName = subcommands[1];
                Item item = currentRoom.getItem( itemName );
                if( item != null ) {
                    System.out.println( "You pick up the " + itemName );
                    inventory.add( item );
                }
            }
            else if(currentRoom.handleCommand(subcommands)){

            }
            else {
                System.out.println( "Don't know how to '" + command + "'" );
            }
        }
        
        System.out.println("Oops...... You died");
    }
    
    public static void markTheGameAsDone()
    {
        done = true;
    }
    
    public static boolean getDone() {
        return done;
    }
}
