package com.example.synthesizer;

public class CosineWave implements AudioComponent {
	private final AudioClip audioClip;
	
	public CosineWave() {
		audioClip = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sampleData = (short) (AudioClip.maxValue * Math.cos(2 * Math.PI * AudioComponentWidget.getDefaultFrequency() * i / AudioClip.getRate()));
			audioClip.setSample(i, sampleData);
		}
	}
	
	public CosineWave(int frequency) {
		audioClip = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sampleData = (short) (AudioClip.maxValue * Math.cos(2 * Math.PI * frequency * i / AudioClip.getRate()));
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
	}
	
}
