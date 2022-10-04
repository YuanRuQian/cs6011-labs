package com.example.synthesizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import static javax.sound.sampled.AudioSystem.getClip;


class SineWaveTest {
	
	public static void main(String[] args) {
		hearASineWave();
	}
	
	static void hearASineWave() {
		int frequency = 420;
		AudioComponent gen = new SineWave(frequency);
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "sine wave clip", frequency, 1);
	}
}