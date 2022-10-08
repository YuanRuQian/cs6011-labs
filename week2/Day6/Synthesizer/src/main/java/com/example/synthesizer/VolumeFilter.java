package com.example.synthesizer;

public class VolumeFilter implements AudioComponent {
	VolumeFilter(AudioComponent audioComponent, double scale)
	{
		input_ = audioComponent;
		scale_ = scale;
	}
	public AudioClip getClip() {
		AudioClip original = input_.getClip();
		AudioClip result =  new AudioClip(original);
		// adjusts the input clip's volume
		for (int index = 0; index < result.getData().length/2; index++) {
			short modifiedSample = ShortClampingHelper.getClampedShort(scale_*result.getSample(index));
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
	
	private final AudioComponent input_;
	private final double scale_;
}
