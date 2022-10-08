package com.example.synthesizer;

public class VFSineWave implements AudioComponent {
	VFSineWave()
	{
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
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			phase += 2.0 * Math.PI * inputAudioClip.getSample(i) / AudioClip.getRate();
			audioClip_.setSample(i, (short) (Short.MAX_VALUE * Math.sin( phase )));
		}
	}
	
	private final AudioClip audioClip_;
}
