package com.example.synthesizer;

public class SquareWave implements AudioComponent {
	
	private AudioClip audioClip;
	private int frequency;
	private short maxVal;
	
	public SquareWave(int frequency, short maxVal) {
		this.frequency = frequency;
		// prevent -Short.MIN_VALUE overflow => Short.MIN_VALUE
		this.maxVal = (short) Math.max(Short.MIN_VALUE + 1, maxVal);
		this.audioClip = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample;
			if (((double) frequency * i / AudioClip.rate) % 1 > 0.5) {
				sample = this.maxVal;
			} else {
				sample = (short) -this.maxVal;
			}
			this.audioClip.setSample(i, sample);
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
