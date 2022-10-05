package com.example.synthesizer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import static javax.sound.sampled.AudioSystem.getClip;

public class PlaySoundButton {
	private final Button button;
	
	PlaySoundButton(String buttonLabel) {
		button = new Button(buttonLabel);
		button.setEffect(new DropShadow(2d, 2d, +2d, Color.BLACK));
		button.addEventHandler(MouseEvent.MOUSE_PRESSED, createPlaySoundButtonHandler());
		button.addEventHandler(MouseEvent.MOUSE_RELEASED, createEndSoundButtonHandler());
	}
	
	public EventHandler<Event> createPlaySoundButtonHandler() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				System.out.println("play an audio clip with frequency: " + GlobalSoundManager.frequency);
				GlobalSoundManager.globalClip = null;
				try {
					GlobalSoundManager.globalClip = getClip();
				} catch (LineUnavailableException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
				try {
					GlobalSoundManager.globalClip.open(format16, GlobalSoundManager.audioClip.getData(), 0, GlobalSoundManager.audioClip.getData().length);
				} catch (LineUnavailableException | NullPointerException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				System.out.println("About to play an audio clip");
				GlobalSoundManager.globalClip.start();
				GlobalSoundManager.globalClip.loop(1);
			}
		};
	}
	
	public EventHandler<Event> createEndSoundButtonHandler() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				GlobalSoundManager.globalClip.close();
				System.out.println("End of clip");
			}
		};
	}
	
	public Button getButton() {
		return button;
	}
}
