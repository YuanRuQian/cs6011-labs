package com.example.synthesizer;

public class AdditionMixer implements AudioComponent {
	private final AudioClip result;
	
	public AdditionMixer() {
		this.result = new AudioClip();
	}
	
	@Override
	public AudioClip getClip() {
		return result;
	}
	
	@Override
	public boolean hasInput() {
		return true;
	}
	
	@Override
	public void connectInput(AudioComponent input) {
		//  adds new input signal to update the output
		AudioClip audioClip = input.getClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short currentSum = ShortClampingHelper.getClampedShort(audioClip.getSample(i) + result.getSample(i));
			result.setSample(i, currentSum);
		}
	}
}
