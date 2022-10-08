package com.example.synthesizer;

public class VFSquareWave implements AudioComponent {
	VFSquareWave() {
		audioClip_ = new AudioClip();
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
		double phase = 0.0;
		AudioClip inputAudioClip = input.getClip();
		double[] phases = new double[AudioClip.TOTAL_SAMPLES];
		double maxPhase = Double.MIN_VALUE, minPhase = Double.MAX_VALUE;
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			phase += 2.0 * Math.PI * inputAudioClip.getSample(i) / AudioClip.getRate();
			phases[i] = phase;
			maxPhase = Math.max(maxPhase, phase);
			minPhase = Math.min(minPhase, phase);
		}
		double midPhase = minPhase + (maxPhase - minPhase) / 2;
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = phases[i] > midPhase ? Short.MAX_VALUE : Short.MIN_VALUE;
			audioClip_.setSample(i, sample);
		}
	}
	
	private final AudioClip audioClip_;
}
