package Rooms;
// Name: Lauryn C. Hansen
// File: SunflowerMaze.java

import Game.Adventure;
import Items.Item;

public class SunflowerMaze extends Rooms.Room {
	
	private boolean lost_ = true;
	//Constructor
	public SunflowerMaze() {
		super( "Sunflower Maze", "A sunflower maze for all ages in the garden." );
		//Create items to use in the sunflower maze
		Item sunflower = new Item( "Sunflower", "A sunflower for good vibes, adding flower power to your adventure.");
		items_.add( sunflower );
		Item map = new Item( "Map", "A map showing the sunflower maze exit...");
		items_.add( map );
	}
	
	@Override
	public Rooms.Room goThroughDoor(int doorNum) {
		if( lost_ ) {
			System.out.println( "You're lost in the sunflower maze!" );
			return null;
		}
		else {
			return super.goThroughDoor( doorNum );
		}
	}
	
	@Override
	public void playerEntered() {
		if( lost_ ) {
			System.out.println( "You see a butterfly up ahead...get the map and follow it to the exit." );
		}
	}
	
	@Override
	public boolean handleCommand( String[] subcommands ) {
		
		if( subcommands.length <= 1 ) {
			return false;
		}
		String cmd  = subcommands[0];
		String attr = subcommands[1];
		
		// Use the map to exit the sunflower maze
		if( cmd.equals( "exit" ) && attr.equals( "maze") ) {
			
			boolean hasMap = false;
			for( Item item : Adventure.inventory ) {
				if( item.getName().equals( "Map" ) ) {
					hasMap = true;
					break;
				}
			}
			if( hasMap ) {
				System.out.println( "You found the exit with your map.");
				lost_ = false;
			}
			else {
				System.out.println( "You don't have the map." );
			}
			return true;
		}
		return false;
	}
}