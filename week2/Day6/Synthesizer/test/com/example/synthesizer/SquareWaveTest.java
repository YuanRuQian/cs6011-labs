package com.example.synthesizer;

import static org.junit.jupiter.api.Assertions.*;

class SquareWaveTest {
	public static void main(String[] args) {
		hearASquareWave();
	}
	
	static void hearASquareWave() {
		int frequency = 420;
		AudioComponent gen = new SquareWave(frequency, Short.MIN_VALUE);
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "square wave clip", frequency, 1);
	}
}