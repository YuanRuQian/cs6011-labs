package com.example.synthesizer;

import javafx.scene.paint.Color;

public class AddSineWaveWidgetButton extends AddAudioComponentWidgetButton {
	AddSineWaveWidgetButton()
	{
		super("Sine Wave", (unused -> new AudioComponentWidget(new SineWave(), SynthesizeApplication.getMainCanvas(), "Sine Wave", (SineWave::new), Color.CORNSILK)));
	}
}
