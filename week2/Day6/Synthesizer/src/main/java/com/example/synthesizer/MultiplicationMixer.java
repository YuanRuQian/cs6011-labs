package com.example.synthesizer;

public class MultiplicationMixer implements AudioComponent {
	private final AudioClip result;
	private final boolean clamping;
	
	public MultiplicationMixer(boolean clamping) {
		this.result = new AudioClip();
		this.clamping = clamping;
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
			int currentSum = audioClip.getSample(i) * result.getSample(i);
			if (clamping) {
				if (currentSum > Short.MAX_VALUE) {
					currentSum = Short.MAX_VALUE;
				} else if (currentSum < Short.MIN_VALUE) {
					currentSum = Short.MIN_VALUE;
				}
			}
			result.setSample(i, (short) currentSum);
		}
	}
}
