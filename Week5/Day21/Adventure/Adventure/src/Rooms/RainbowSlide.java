// Name: Lauryn C. Hansen
// File: RainbowSlide.java

package Rooms;

import Game.Adventure;
import Items.Item;

public class RainbowSlide extends Room {
	
	private boolean stuck_ = true;
	//Constructor
	public RainbowSlide() {
		super( "Rainbow Slide", "A joyful ride to the center of the sunflower maze in the garden." );
		//Create an item to use on the rainbow slide
		Item magicGlitter = new Item( "MagicGlitter", "Glitter to light path down slide and for lol's...");
		items_.add( magicGlitter );
	}
	
	@Override
	public Rooms.Room goThroughDoor(int doorNum) {
		//If stuck on the slide, magic glitter will light the way to get down it
		if( stuck_ ) {
			System.out.println( "You're riding down the rainbow slide!" );
			return null;
		}
		else {
			return super.goThroughDoor( doorNum );
		}
	}
	
	@Override
	public void playerEntered() {
		if( stuck_ ) {
			System.out.println( "You need the magic glitter to light your path down the slide..." );
		}
	}
	
	@Override
	public boolean handleCommand( String[] subcommands ) {
		
		if( subcommands.length <= 1 ) {
			return false;
		}
		String cmd  = subcommands[0];
		String attr = subcommands[1];
		
		// Use the magic glitter to slide down the rainbow slide
		if( cmd.equals( "release" ) && attr.equals( "slide") ) {
			
			boolean hasGlitter = false;
			for( Item item : Adventure.inventory ) {
				if( item.getName().equals( "MagicGlitter" ) ) {
					hasGlitter = true;
					break;
				}
			}
			if( hasGlitter ) {
				System.out.println( "You've lit the way down the rainbow slide.");
				stuck_ = false;
			}
			else {
				System.out.println( "You don't have the magic glitter." );
			}
			return true;
		}
		return false;
	}
}