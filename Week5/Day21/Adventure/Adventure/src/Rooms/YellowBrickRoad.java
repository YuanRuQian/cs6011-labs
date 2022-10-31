// Name: Lauryn C. Hansen
// File: YellowBrickRoad.java

package Rooms;

import Game.Adventure;
import Items.Item;

public class YellowBrickRoad extends Rooms.Room {
	
	private boolean soreFeet_ = true;
	
	//Constructor
	public YellowBrickRoad() {
		super( "Yellow Brick Road", "A 4 mile wandering path which leads to the the house entrance or sunflower maze." );
		//Create items for use on the yellow brick road
		Item electricScooter = new Item( "Electric scooter", "An electric scooter to ride back to the house entrance, weeee.....");
		items_.add( electricScooter );
		Item unicornHelmet = new Item( "Unicorn helmet", "A cool unicorn helmet to ride safely and in style if you so choose.");
		items_.add( unicornHelmet );
	}
	
	@Override
	public Rooms.Room goThroughDoor(int doorNum) {
		
		if( soreFeet_ ) {
			System.out.println( "Your feet are sore & you have 3.5 miles to go on the yellow brick road!" );
			return null;
		}
		else {
			return super.goThroughDoor( doorNum );
		}
	}
	
	@Override
	public void playerEntered() {
		if( soreFeet_ ) {
			System.out.println( "Get the electric scooter and/or unicorn helmet to ride to your destination" );
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
		if( cmd.equals( "ride" ) && attr.equals( "road") ) {
			
			boolean hasScooter = false;
			for( Item item : Adventure.inventory ) {
				if( item.getName().equals( "Electric scooter" ) ) {
					hasScooter = true;
					break;
				}
			}
			if( hasScooter ) {
				System.out.println( "You rode the scooter to your destination.");
				soreFeet_ = false;
			}
			else {
				System.out.println( "You don't have the electric scooter to ride." );
			}
			return true;
		}
		return false;
	}
}