package com.example.synthesizer;

import java.util.Random;

public class WhiteNoise implements AudioComponent {
	
	WhiteNoise() {
		audioClip_ = new AudioClip();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = (short) random.nextInt(Short.MAX_VALUE);
			audioClip_.setSample(i, sample);
		}
	}
	
	@Override
	public AudioClip getClip() {
		return audioClip_;
	}
	
	@Override
	public boolean hasInput() {
		return false;
	}
	
	@Override
	public void connectInput(AudioComponent input) {
		assert !hasInput();
	}
	
	private final AudioClip audioClip_;
}
