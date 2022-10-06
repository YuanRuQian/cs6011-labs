package com.example.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class VolumeSliderWidget extends SliderWidgetBase {
	
	VolumeSliderWidget(int volumeMin, int volumeMax, int defaultVolume, String labelText) {
		super(new Slider(volumeMin, volumeMax, defaultVolume), new Label( labelText + " : " + defaultVolume), (newVal) -> {
			GlobalSoundManager.updateVolume(newVal.intValue());
			return null;
		}, (newVal) -> labelText + " : " + newVal.intValue());
	}
}
