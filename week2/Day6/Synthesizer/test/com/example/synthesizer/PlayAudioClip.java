package com.example.synthesizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import static javax.sound.sampled.AudioSystem.getClip;

public class PlayAudioClip {
	public static void play(AudioClip ac, String audioClipName, int frequency, int loopTimes) {
		Clip c = null;
		try {
			c = getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
		try {
			c.open(format16, ac.getData(), 0, ac.getData().length); // Reads data from our byte array to play it.
		} catch (LineUnavailableException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("About to play " + audioClipName + " , frequency: " + frequency);
		c.start(); // Plays it.
		c.loop(loopTimes);
		
		while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
			// Do nothing while we wait for the note to play.
		}
		
		System.out.println("End " + audioClipName);
		c.close();
	}
	
	public static void play(AudioClip ac, String audioClipName, int loopTimes) {
		Clip c = null;
		try {
			c = getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
		try {
			c.open(format16, ac.getData(), 0, ac.getData().length); // Reads data from our byte array to play it.
		} catch (LineUnavailableException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("About to play " + audioClipName);
		c.start();
		c.loop(loopTimes);
		
		while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
			// Do nothing while we wait for the note to play.
		}
		
		System.out.println("End " + audioClipName);
		c.close();
	}
}
