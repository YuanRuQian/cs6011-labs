package com.example.synthesizer;

public class LinearRamp implements AudioComponent {
	
	LinearRamp(float start, float end) {
		audioClip_ = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = ShortClampingHelper.getClampedShort((start * (AudioClip.TOTAL_SAMPLES - i) + end * i) / AudioClip.TOTAL_SAMPLES);
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
		assert hasInput();
	}
	
	private final AudioClip audioClip_;
}
