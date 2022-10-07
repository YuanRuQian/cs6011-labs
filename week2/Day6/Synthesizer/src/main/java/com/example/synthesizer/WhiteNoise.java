package com.example.synthesizer;

import java.util.Random;

public class WhiteNoise implements AudioComponent {
	
	private final AudioClip audioClip;
	
	public WhiteNoise() {
		audioClip = new AudioClip();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = (short) random.nextInt(Short.MAX_VALUE);
			audioClip.setSample(i, sample);
		}
	}
	
	@Override
	public AudioClip getClip() {
		return audioClip;
	}
	
	@Override
	public boolean hasInput() {
		return false;
	}
	
	@Override
	public void connectInput(AudioComponent input) {
	
	}
}
