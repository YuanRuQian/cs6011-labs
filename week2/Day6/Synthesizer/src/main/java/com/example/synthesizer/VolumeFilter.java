package com.example.synthesizer;

public class VolumeFilter implements AudioComponent {
	private final AudioComponent input;
	private final double scale;
	
	public VolumeFilter(AudioComponent audioComponent, double scale)
	{
		this.input = audioComponent;
		this.scale = scale;
	}
	public AudioClip getClip() {
		AudioClip original = input.getClip();
		AudioClip result =  new AudioClip(original);
		// adjusts the input clip's volume
		for (int index = 0; index < result.getData().length/2; index++) {
			short modifiedSample = ShortClampingHelper.getClampedShort(scale*result.getSample(index));
			result.setSample(index, modifiedSample);
		}
		return result;
	}
	
	@Override
	public boolean hasInput() {
		return true;
	}
	
	@Override
	public void connectInput(AudioComponent input) {
		assert !hasInput();
	}
}
