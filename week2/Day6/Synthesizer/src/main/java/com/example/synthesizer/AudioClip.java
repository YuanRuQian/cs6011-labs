package com.example.synthesizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.Arrays;

import static javax.sound.sampled.AudioSystem.getClip;

public class AudioClip {
	
	// same duration 2.0s
	public static Double duration = 2.0;
	// sample rate 44.1 KHz
	public static int rate = 44100;
	private byte[] data;
	public static short maxValue = Short.MAX_VALUE;
	public static final int TOTAL_SAMPLES = (int) (duration * rate);
	
	public AudioClip() {
		data = new byte[(int) (duration * rate * 2)];
		Arrays.fill(data, (byte) 0);
	}
	
	public AudioClip(AudioClip rhs) {
		data = Arrays.copyOf(rhs.getData(),  (int)(duration * rate * 2));
	}
	
	// The values should be stored in Little Endian order.
	// In other words, for the value of sample i
	// the lower 8 bits should be stored at array[ 2*i ]
	// the upper 8 bits should be stored at array[ (2*i) + 1 ].
	public int getSample(int index) {
		byte lower8bits = data[2 * index];
		byte higher8bits = data[2 * index + 1];
		return (higher8bits << 8) | (lower8bits & 0xff);
	}
	
	public void setSample(int index, Short value) {
		byte higher8bits = (byte) (value >> 8);
		byte lower8Bits = (byte) (value & (0xff));
		data[2 * index] = lower8Bits;
		data[2 * index + 1] = higher8bits;
	}
	
	byte[] getData() {
		return Arrays.copyOf(data, (int) (duration * rate * 2));
	}
	
	public void play()
	{
		Clip c = null; // Note, this is different from our AudioClip class.
		try {
			c = getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		// This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
		AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
		try {
			c.open(format16, getData(), 0, getData().length); // Reads data from our byte array to play it.
		} catch (LineUnavailableException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("About to play an audio clip");
		c.start(); // Plays it.
		c.loop(1);
		
		// Makes sure the program doesn't quit before the sound plays.
		while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
			// Do nothing while we wait for the note to play.
		}
		
		System.out.println("End of the audio clip");
		c.close();
	}
	
}
