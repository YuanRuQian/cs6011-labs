package com.example.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class FrequencySliderWidget extends SliderWidgetBase {
	FrequencySliderWidget(int frequencyMin, int frequencyMax, int defaultFrequency, String labelText) {
		super(new Slider(frequencyMin, frequencyMax, defaultFrequency), new Label( labelText + " : " + defaultFrequency), (newVal) -> {
			GlobalSoundManager.updateFrequency(newVal.intValue());
			return null;
		}, (newVal) -> labelText + " : " + newVal.intValue());
	}
}
