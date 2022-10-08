package com.example.synthesizer;

public class SquareWave implements AudioComponent {
	
	SquareWave() {
		audioClip_ = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample;
			if ((AudioComponentWidget.getDefaultFrequency() * i / AudioClip.getRate()) % 1 > 0.5) {
				sample = Short.MAX_VALUE;
			} else {
				sample = -Short.MAX_VALUE;
			}
			audioClip_.setSample(i, sample);
		}
	}

	public SquareWave(int frequency, short maxVal) {
		// prevent -Short.MIN_VALUE overflow => Short.MIN_VALUE
		short maxVal1 = (short) Math.max(Short.MIN_VALUE + 1, maxVal);
		this.audioClip_ = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample;
			if (((double) frequency * i / AudioClip.getRate()) % 1 > 0.5) {
				sample = maxVal1;
			} else {
				sample = (short) -maxVal1;
			}
			audioClip_.setSample(i, sample);
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
	}
	
	private final AudioClip audioClip_;
}
