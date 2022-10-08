package com.example.synthesizer;

public class LinearRamp implements AudioComponent {
	public float start;
	public float end;
	private final AudioClip audioClip;
	
	LinearRamp(float start, float end) {
		this.start = start;
		this.end = end;
		this.audioClip = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = ShortClampingHelper.getClampedShort((start * (AudioClip.TOTAL_SAMPLES - i) + end * i) / AudioClip.TOTAL_SAMPLES);
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
		assert hasInput();
	}
}
