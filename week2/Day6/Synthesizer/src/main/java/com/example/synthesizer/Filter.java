package com.example.synthesizer;

public class Filter implements AudioComponent {
	private AudioComponent input;
	private double scale;
	private boolean clamping;
	
	public Filter(AudioComponent audioComponent, double scale, boolean clamping)
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
	
	}
}
