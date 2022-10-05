package com.example.synthesizer;

class SawToothWaveTest {
	public static void main(String[] args) {
		hearASawToothWave();
	}
	
	static void hearASawToothWave() {
		int frequency = 420;
		AudioComponent gen = new SawToothWave(frequency);
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "saw tooth wave clip", frequency, 1);
	}
}