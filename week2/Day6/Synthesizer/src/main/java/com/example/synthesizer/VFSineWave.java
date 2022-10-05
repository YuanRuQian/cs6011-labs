package com.example.synthesizer;

public class VFSineWave extends LinearRamp implements AudioComponent {
	
	private AudioClip audioClip;
	
	private boolean clamping;
	
	VFSineWave(boolean clamping)
	{
		this.audioClip = new AudioClip();
		this.clamping = clamping;
		float[] floats = getSamples();
		setUpAudioClip(floats);
	}
	
	
	VFSineWave(float start, float end, boolean clamping)
	{
		this.start = start;
		this.end = end;
		this.audioClip = new AudioClip();
		this.clamping = clamping;
		float[] floats = getSamples();
		setUpAudioClip(floats);
	}
	
	private void setUpAudioClip(float[] floats)
	{
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			short current = (short) floats[i];
			if(clamping)
			{
				if(floats[i] > Short.MAX_VALUE)
				{
					current = Short.MAX_VALUE;
				} else if (floats[i] < Short.MIN_VALUE) {
					current = Short.MIN_VALUE;
				}
			}
			audioClip.setSample(i, current);
		}
	}
	
	@Override
	public AudioClip getClip() {
		return audioClip;
	}
	
	@Override
	public boolean hasInput() {
		return true;
	}
	
	@Override
	public void connectInput(AudioComponent input) {
	
	}
}
