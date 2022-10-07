package com.example.synthesizer;

public class VolumeFilter implements AudioComponent {
	private final AudioComponent input;
	private final double scale;
	private final boolean clamping;
	
	public VolumeFilter(AudioComponent audioComponent, double scale, boolean clamping)
	{
		this.input = audioComponent;
		this.scale = scale;
		this.clamping = clamping;
	}
	public AudioClip getClip() {
		AudioClip original = input.getClip();
		AudioClip result =  new AudioClip(original);
		// adjusts the input clip's volume
		for (int index = 0; index < result.getData().length/2; index++) {
			short modifiedSample = (short) (scale*result.getSample(index));
			if(clamping)
			{
				if(scale*result.getSample(index) > Short.MAX_VALUE)
				{
					modifiedSample = Short.MAX_VALUE;
				} else if (scale*result.getSample(index) < Short.MIN_VALUE) {
					modifiedSample = Short.MIN_VALUE;
				}
			}
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
