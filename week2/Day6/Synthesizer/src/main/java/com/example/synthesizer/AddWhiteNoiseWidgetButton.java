package com.example.synthesizer;

import javafx.scene.paint.Color;

public class AddWhiteNoiseWidgetButton extends AddAudioComponentWidgetButton {
	AddWhiteNoiseWidgetButton()
	{
		super("White Noise", (unused -> new AudioComponentWidget(new WhiteNoise(), SynthesizeApplication.getMainCanvas(), "White Noise", (newFrequency -> new WhiteNoise()), Color.LIGHTGRAY)));
	}
}