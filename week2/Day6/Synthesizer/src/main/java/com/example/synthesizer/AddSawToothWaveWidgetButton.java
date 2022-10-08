package com.example.synthesizer;

import javafx.scene.paint.Color;

public class AddSawToothWaveWidgetButton extends AddAudioComponentWidgetButton {
	AddSawToothWaveWidgetButton() {
		super("Saw Tooth Wave", (unused -> new AudioComponentWidget(new SawToothWave(), SynthesizeApplication.getMainCanvas(), "Saw Tooth Wave", (SawToothWave::new), Color.LAVENDERBLUSH)));
	}
}
