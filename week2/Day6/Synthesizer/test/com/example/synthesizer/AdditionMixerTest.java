package com.example.synthesizer;

import static javax.sound.sampled.AudioSystem.getClip;

class AdditionMixerTest {
	public static void main(String[] args) {
		hearAnAdditionMixedSineWave();
	}
	
	static void hearAnAdditionMixedSineWave() {
		int frequency1 = 220;
		AudioComponent gen1 = new SineWave(frequency1);
		AudioClip clip1 = gen1.getClip();
		PlayAudioClip.play(clip1, "clip1" ,frequency1, 1);
		
		
		int frequency2 = 400;
		AudioComponent gen2 = new SineWave(frequency2);
		AudioClip clip2 = gen2.getClip();
		PlayAudioClip.play(clip2, "clip2" ,frequency2, 1);
		
		AudioComponent mixer = new AdditionMixer();
		
		mixer.connectInput(gen1);
		mixer.connectInput(gen2);
		AudioClip mixedClip = mixer.getClip();
		PlayAudioClip.play(mixedClip, "addition mixed clip", 1);
	}
}