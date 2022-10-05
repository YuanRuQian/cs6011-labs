package com.example.synthesizer;

import static org.junit.jupiter.api.Assertions.*;

class WhiteNoiseTest {
	
	public static void main(String[] args) {
		hearAWhiteNoise();
	}
	
	static void hearAWhiteNoise() {
		int frequency = 420;
		AudioComponent gen = new WhiteNoise(frequency);
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "white noise clip", frequency, 1);
	}
}