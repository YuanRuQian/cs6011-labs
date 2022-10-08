package com.example.synthesizer;

public class SawToothWave implements AudioComponent {
	SawToothWave() {
		frequency_ = (int) AudioComponentWidget.getDefaultFrequency();
		audioClip_ = new AudioClip();
		short[] samples = generateWaveWithFrequency();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			audioClip_.setSample(i, samples[i]);
		}
	}
	
	public SawToothWave(int frequency) {
		frequency_ = frequency;
		audioClip_ = new AudioClip();
		short[] samples = generateWaveWithFrequency();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			audioClip_.setSample(i, samples[i]);
		}
	}
	
	public short[] generateWaveWithFrequency() {
		short[] samples = new short[AudioClip.TOTAL_SAMPLES];
		double phase = 0.0;
		double phaseDelta = (double) frequency_ / AudioClip.getRate();
		// phase : [0.0, 1.0]
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			phase += phaseDelta;
			if (phase > 1.0)
				phase -= 1.0;
			samples[i] = ShortClampingHelper.getClampedShort((1 - phase) * Short.MAX_VALUE);
		}
		return samples;
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
	private final int frequency_;
}
