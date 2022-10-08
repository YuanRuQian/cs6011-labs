package com.example.synthesizer;

public class ReverbFilter implements AudioComponent {
	private final AudioComponent input;
	private final double scale;
	private final short delay;
	
	public ReverbFilter(AudioComponent audioComponent, double scale, short delay) {
		this.input = audioComponent;
		this.scale = scale;
		this.delay = delay;
	}
	
	public AudioClip getClip() {
		AudioClip original = input.getClip();
		AudioClip result = new AudioClip(original);
		// adjusts the input clip's volume
		for (int index = 0; index < result.getData().length / 2; index++) {
			short sample = ShortClampingHelper.getClampedShort(scale * original.getSample(index));
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
