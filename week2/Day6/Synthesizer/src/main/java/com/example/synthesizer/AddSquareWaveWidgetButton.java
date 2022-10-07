package com.example.synthesizer;

import javafx.scene.paint.Color;

public class AddSquareWaveWidgetButton extends AddAudioComponentWidgetButton {
	AddSquareWaveWidgetButton()
	{
		super("Square Wave", (unused -> new AudioComponentWidget(new SquareWave(), SynthesizeApplication.getMainCanvas(), "Square Wave", (newFrequency -> new SquareWave(newFrequency, Short.MAX_VALUE)), Color.LIGHTCYAN)));
	}
}