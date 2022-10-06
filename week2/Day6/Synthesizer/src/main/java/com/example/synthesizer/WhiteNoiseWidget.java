package com.example.synthesizer;

public class WhiteNoiseWidget extends AudioComponentWidgetBase {
	WhiteNoiseWidget() {
		super(new WhiteNoise(GlobalSoundManager.getFrequency()), "White Noise", (unused -> new VolumeFilter(new WhiteNoise(GlobalSoundManager.getFrequency()), (float)GlobalSoundManager.getVolume()/100, true)));
	}
}