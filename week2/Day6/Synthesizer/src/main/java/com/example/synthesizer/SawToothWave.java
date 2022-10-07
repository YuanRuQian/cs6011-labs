package com.example.synthesizer;

public class SawToothWave implements AudioComponent {
	
	private final AudioClip audioClip;
	private final int frequency;
	
	public SawToothWave() {
		this.frequency = (int) AudioComponentWidget.getDefaultFrequency();
		audioClip = new AudioClip();
		short[] samples = generateWaveWithFrequency();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			audioClip.setSample(i, samples[i]);
		}
	}
	
	public SawToothWave(int frequency) {
		this.frequency = frequency;
		audioClip = new AudioClip();
		short[] samples = generateWaveWithFrequency();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			audioClip.setSample(i, samples[i]);
		}
	}
	
	public short[] generateWaveWithFrequency() {
		short[] samples = new short[AudioClip.TOTAL_SAMPLES];
		double phase = 0.0;
		double phaseDelta = (double) this.frequency / AudioClip.getRate();
		// phase : [0.0, 1.0]
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			phase += phaseDelta;
			if (phase > 1.0)
				phase -= 1.0;
			samples[i] = ShortClampingHelper.getClampedShort(phase * Short.MAX_VALUE);
		}
		return samples;
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
