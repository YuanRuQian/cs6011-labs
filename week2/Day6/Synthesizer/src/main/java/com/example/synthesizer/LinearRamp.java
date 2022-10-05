package com.example.synthesizer;

import java.util.Arrays;

public class LinearRamp {
	public float start;
	public float end;
	private float[] samples = new float[AudioClip.TOTAL_SAMPLES];
	
	LinearRamp() {
		this.start = 50;
		this.end = 2000;
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			samples[i] = (start * (AudioClip.TOTAL_SAMPLES - i) + end * i) / AudioClip.TOTAL_SAMPLES;
		}
	}
	
	LinearRamp(float start, float end) {
		this.start = start;
		this.end = end;
		for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
			samples[i] = (start * (AudioClip.TOTAL_SAMPLES - i) + end * i) / AudioClip.TOTAL_SAMPLES;
		}
	}
	
	float[] getSamples() {
		return Arrays.copyOf(samples, AudioClip.TOTAL_SAMPLES);
	}
}
