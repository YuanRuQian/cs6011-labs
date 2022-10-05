package com.example.synthesizer;

public class SawToothWave implements AudioComponent {
	
	private AudioClip audioClip;
	private int frequency;
	
	public SawToothWave(int frequency) {
		this.frequency = frequency;
		audioClip = new AudioClip();
		short[] samples = generateWaveForm(20);
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			audioClip.setSample(i, samples[i]);
			System.out.println(audioClip.getSample(i));
		}
	}
	
	// reference: https://stackoverflow.com/a/59437685
	public short[] generateWaveForm(int N) {
		short[] samples = new short[AudioClip.TOTAL_SAMPLES];
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = (short) ((double) Short.MAX_VALUE * 8.0 / Math.pow(Math.PI, 2.0) * getDataPoint(i, N));
			samples[i] = sample;
		}
		return samples;
	}
	
	private double getDataPoint(int t, int N) {
		double sum = 0;
		for (int i = 0; i <= N - 1; i++) {
			sum += getHarmonicShare(t, i);
		}
		return sum;
	}
	
	private double getHarmonicShare(int t, int i) {
		double n = 2.0 * i + 1.0;
		return Math.pow(-1.0, i) * Math.pow(n, -2.0) * Math.sin(2.0 * Math.PI * frequency * (t / AudioClip.rate) * n);
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
	
	}
}
