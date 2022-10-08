package com.example.synthesizer;

import static javax.sound.sampled.AudioSystem.getClip;

class VolumeFilterTest {
	public static void main(String[] args) {
		hearAVolumeFilteredSineWave();
	}
	
	static void hearAVolumeFilteredSineWave() {
		int frequency = 420;
		AudioComponent gen = new SineWave(frequency); // Your code
		
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "unfiltered clip", 1);
		
		AudioClip filteredClip = new VolumeFilter(gen, 0.42).getClip();
		PlayAudioClip.play(filteredClip, "filtered clip", 1);
	}
}