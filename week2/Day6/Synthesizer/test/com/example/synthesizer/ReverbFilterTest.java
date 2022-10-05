package com.example.synthesizer;

import static org.junit.jupiter.api.Assertions.*;

class ReverbFilterTest {
	public static void main(String[] args) {
		hearAReverbFilteredSineWave();
	}
	
	static void hearAReverbFilteredSineWave() {
		int frequency = 420;
		AudioComponent gen = new SineWave(frequency); // Your code
		
		AudioClip clip = gen.getClip();
		PlayAudioClip.play(clip, "unfiltered clip", 1);
		
		AudioClip filteredClip = new ReverbFilter(gen, 1.42, (short) 500, true).getClip();
		PlayAudioClip.play(filteredClip, "filtered clip", 1);
	}
}