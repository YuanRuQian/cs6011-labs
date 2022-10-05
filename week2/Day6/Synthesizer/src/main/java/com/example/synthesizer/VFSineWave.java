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
		float phase = 0;
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			phase += 2 * Math.PI * floats[ i ] / AudioClip.rate;
			double sample =  Short.MAX_VALUE * Math.sin( phase);
			if(clamping)
			{
				if(sample > Short.MAX_VALUE)
				{
					sample = Short.MAX_VALUE;
				} else if (sample < Short.MIN_VALUE) {
					sample = Short.MIN_VALUE;
				}
			}
			audioClip.setSample(i, (short) sample);
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
