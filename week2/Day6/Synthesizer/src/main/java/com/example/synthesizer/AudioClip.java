package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {
	AudioClip() {
		data_ = new byte[(int) (duration * rate * 2)];
		Arrays.fill(data_, (byte) 0);
	}
	
	AudioClip(AudioClip rhs) {
		data_ = Arrays.copyOf(rhs.getData(), (int) (duration * rate * 2));
	}
	
	// The values should be stored in Little Endian order.
	// In other words, for the value of sample i
	// the lower 8 bits should be stored at array[ 2*i ]
	// the upper 8 bits should be stored at array[ (2*i) + 1 ].
	public short getSample(int index) {
		byte lower8bits = data_[2 * index];
		byte higher8bits = data_[2 * index + 1];
		return (short) ((higher8bits << 8) | (lower8bits & 0xff));
	}
	
	public void setSample(int index, Short value) {
		byte higher8bits = (byte) (value >> 8);
		byte lower8Bits = (byte) (value & (0xff));
		data_[2 * index] = lower8Bits;
		data_[2 * index + 1] = higher8bits;
	}
	
	public static int getRate() {
		return rate;
	}
	
	byte[] getData() {
		return Arrays.copyOf(data_, (int) (duration * rate * 2));
	}
	
	// same duration 2.0s
	public static Double duration = 2.0;
	// sample rate 44.1 KHz
	private static final int rate = 44100;
	private final byte[] data_;
	public static short maxValue = Short.MAX_VALUE;
	public static final int TOTAL_SAMPLES = (int) (duration * rate);
}
