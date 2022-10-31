package Rooms;
// Name: Lauryn C. Hansen
// File: Laboratory.java

import Game.Adventure;
import Items.Item;

public class Laboratory extends Room {
	
	private boolean locked_ = true;
	//Constructor
	public Laboratory() {
		super( "Laboratory", "A magical laboratory with a mind altering potion & a refreshing brew." );
		//Create items to be in the laboratory
		Item potion = new Item( "Potion", "A sour shot juiced after of a day of foraging in the forest...drink to unlock your wildest dreams.");
		items_.add( potion );
		Item brew = new Item("Brew", "A refreshing fall themed stout to quench your thirst for further adventuring.");
		items_.add( brew );
	}
	
	@Override
	public Rooms.Room goThroughDoor(int doorNum) {
		
		if( locked_ ) {
			System.out.println( "You are parched,to survive your adventure enjoy a potion or brew!" );
			return null;
		}
		else {
			return super.goThroughDoor( doorNum );
		}
	}
	
	@Override
	public void playerEntered() {
		if( locked_ ) {
			System.out.println( "Let the magic begin, try a potion or brew..." );
		}
	}
	
	@Override
	public boolean handleCommand( String[] subcommands ) {
		
		if( subcommands.length <= 1 ) {
			return false;
		}
		String cmd  = subcommands[0];
		String attr = subcommands[1];
		
		// unlock, use
		if( cmd.equals( "unlock" ) && attr.equals( "door") ) {
			
			boolean hasQuenchedThirst = false;
			for( Item item : Adventure.inventory ) {
				if( item.getName().equals( "Potion")) {
					hasQuenchedThirst = true;
					break;
				}
				else if ( item.getName().equals( "Brew")) {
					hasQuenchedThirst = true;
					break;
				}
			}
			if( hasQuenchedThirst ) {
				System.out.println( "You unlocked the door by quenching your thirst.");
				locked_ = false;
			}
			else {
				System.out.println( "You haven't had a potion or brew." );
			}
			return true;
		}
		return false;
	}
}