package com.example.synthesizer;

class CosineWaveTest {
	public static void main(String[] args) {
		hearACosineWave();
	}
	
	static void hearACosineWave() {
		int frequency = 440;
		AudioComponent gen = new CosineWave(frequency);
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "cosine wave clip", frequency, 1);
	}
	
}