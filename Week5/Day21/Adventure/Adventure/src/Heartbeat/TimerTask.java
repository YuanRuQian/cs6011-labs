// Written by Ruqian Yuan

package Heartbeat;

import Game.Adventure;

import java.util.function.Function;

public class TimerTask extends java.util.TimerTask {
		private Function<Void, Void> handler;
	
		public TimerTask(Function<Void, Void> handler) {
			this.handler = handler;
		}
		
		public void run() {
			if(Adventure.getDone())
			{
				return;
			}
			handler.apply(null);
		}
		
		
}
