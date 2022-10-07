package com.example.synthesizer;

public class LinearRamp implements AudioComponent {
	public float start;
	public float end;
	private final boolean clamping;
	private final AudioClip audioClip;
	
	LinearRamp(float start, float end, boolean clamping) {
		this.start = start;
		this.end = end;
		this.clamping = clamping;
		this.audioClip = new AudioClip();
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short sample = getProcessedSample((start * (AudioClip.TOTAL_SAMPLES - i) + end * i) / AudioClip.TOTAL_SAMPLES);
			audioClip.setSample(i, sample);
		}
	}
	
	private short getProcessedSample(float num)
	{
		float sample = num;
		if(clamping)
		{
			if(sample > Short.MAX_VALUE)
			{
				sample = Short.MAX_VALUE;
			} else if (sample < Short.MIN_VALUE) {
				sample = Short.MIN_VALUE;
			}
		}
		return (short) sample;
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
