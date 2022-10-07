package com.example.synthesizer;

class WhiteNoiseTest {
	
	public static void main(String[] args) {
		hearAWhiteNoise();
	}
	
	static void hearAWhiteNoise() {
		AudioComponent gen = new WhiteNoise();
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "white noise clip", 1);
	}
}