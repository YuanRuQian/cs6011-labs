package com.example.synthesizer;

public class ReverbFilter implements AudioComponent {
	private AudioComponent input;
	private double scale;
	private short delay;
	private boolean clamping;
	
	public ReverbFilter(AudioComponent audioComponent, double scale, short delay, boolean clamping) {
		this.input = audioComponent;
		this.scale = scale;
		this.delay = delay;
		this.clamping = clamping;
	}
	
	public AudioClip getClip() {
		AudioClip original = input.getClip();
		AudioClip result = new AudioClip(original);
		// adjusts the input clip's volume
		for (int index = 0; index < result.getData().length / 2; index++) {
			double doubleSample = scale * original.getSample(index);
			short sample = (short) doubleSample;
			if (clamping) {
				if (doubleSample > Short.MAX_VALUE) {
					sample = Short.MAX_VALUE;
				} else if (doubleSample < Short.MIN_VALUE) {
					sample = Short.MIN_VALUE;
				}
			}
			result.setSample((index + delay) % AudioClip.TOTAL_SAMPLES, sample);
		}
		return result;
	}
	
	@Override
	public boolean hasInput() {
		return true;
	}
	
	@Override
	public void connectInput(AudioComponent input) {
	
	}
}
