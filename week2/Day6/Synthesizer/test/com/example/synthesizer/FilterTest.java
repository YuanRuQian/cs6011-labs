package com.example.synthesizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import static javax.sound.sampled.AudioSystem.getClip;

class FilterTest {
	public static void main(String[] args) {
		hearAFilteredSineWave();
	}
	
	static void hearAFilteredSineWave() {
		int frequency = 420;
		AudioComponent gen = new SineWave(frequency); // Your code
		
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "unfiltered clip", 1);
		
		AudioClip filteredClip = new Filter(gen, 0.42, true).getClip();
		PlayAudioClip.play(filteredClip, "filtered clip", 1);
	}
}