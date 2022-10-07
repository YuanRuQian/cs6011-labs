package com.example.synthesizer;

import javafx.scene.paint.Color;

public class AddCosineWaveWidgetButton extends AddAudioComponentWidgetButton {
	AddCosineWaveWidgetButton()
	{
		super("Cosine Wave", (unused -> new AudioComponentWidget(new CosineWave(), SynthesizeApplication.getMainCanvas(), "Cosine Wave", (CosineWave::new), Color.LEMONCHIFFON)));
		
	}
}
