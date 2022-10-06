package com.example.synthesizer;

public class VFSineWave implements AudioComponent {
	private AudioClip audioClip;
	
	VFSineWave()
	{
		audioClip = new AudioClip();
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
		double phase = 0.0;
		AudioClip inputAudioClip = input.getClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			phase += 2.0 * Math.PI * inputAudioClip.getSample(i) / AudioClip.rate;
			audioClip.setSample(i, (short) (Short.MAX_VALUE * Math.sin( phase )));
		}
	}
}
