package com.example.synthesizer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import static javax.sound.sampled.AudioSystem.getClip;

public class PlaySoundButton {
	static public int currentFrequency = 500;
	static public AudioClip currentAudioClip = new AudioClip();
	private final Button button;
	
	PlaySoundButton(String buttonLabel) {
		button = new Button(buttonLabel);
		button.addEventHandler(MouseEvent.MOUSE_PRESSED, createPlaySoundButtonHandler());
		button.addEventHandler(MouseEvent.MOUSE_RELEASED, createEndSoundButtonHandler());
	}
	
	public EventHandler<Event> createPlaySoundButtonHandler() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				AudioComponent gen = new SineWave(currentFrequency);
				currentAudioClip = gen.getClip();
				System.out.println("play an audio clip with frequency: " + currentFrequency);
				GlobalSoundManager.globalClip = null;
				try {
					GlobalSoundManager.globalClip = getClip();
				} catch (LineUnavailableException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
				try {
					GlobalSoundManager.globalClip.open(format16, currentAudioClip.getData(), 0, currentAudioClip.getData().length);
				} catch (LineUnavailableException | NullPointerException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				System.out.println("About to play an audio clip");
				GlobalSoundManager.globalClip.start(); // Plays it.
				GlobalSoundManager.globalClip.loop(1);
			}
		};
	}
	
	public EventHandler<Event> createEndSoundButtonHandler() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				GlobalSoundManager.globalClip.close();
			}
		};
	}
	
	public Button getButton() {
		return button;
	}
}
