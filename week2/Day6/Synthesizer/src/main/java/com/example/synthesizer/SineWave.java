package com.example.synthesizer;

public class SineWave implements AudioComponent {
	
	SineWave() {
		audioClip_ = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sampleData = (short) (AudioClip.maxValue * Math.sin(2 * Math.PI * AudioComponentWidget.getDefaultFrequency() * i / AudioClip.getRate()));
			audioClip_.setSample(i, sampleData);
		}
	}
	public SineWave(int frequency) {
		audioClip_ = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sampleData = (short) (AudioClip.maxValue * Math.sin(2 * Math.PI * frequency * i / AudioClip.getRate()));
			audioClip_.setSample(i, sampleData);
		}
	}
	
	
	@Override
	public AudioClip getClip() {
		return audioClip_;
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
	
	private final AudioClip audioClip_;
}
