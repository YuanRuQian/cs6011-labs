package com.example.synthesizer;

public class SineWave implements AudioComponent {
	private int frequency;
	private AudioClip audioClip;
	
	public SineWave(int frequency) {
		this.frequency = frequency;
		audioClip = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sampleData = (short) (AudioClip.maxValue * Math.sin(2 * Math.PI * frequency * i / AudioClip.rate));
			audioClip.setSample(i, sampleData);
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
		System.out.println("Sine wave connect input");
	}
}
