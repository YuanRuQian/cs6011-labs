package com.example.synthesizer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class FrequencySlider {
	private int frequencyMin = 0;
	private int frequencyMax = 1000;
	private int defaultValue = frequencyMax / 2;
	private Label frequencySliderLabel;
	private Slider frequencySlider;
	private Label frequencyValueLabel;
	
	FrequencySlider(int frequencyMin, int frequencyMax, int defaultValue) {
		this.frequencyMin = frequencyMin;
		this.frequencyMax = frequencyMax;
		this.defaultValue = defaultValue;
		
		frequencySliderLabel = new Label("Slide to set the frequency: ");
		frequencySlider = new Slider(frequencyMin, frequencyMax, defaultValue);
		frequencyValueLabel = new Label(Integer.toString((int) frequencySlider.getValue()));
		
		frequencySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
			                    Number oldVal, Number newVal) {
				frequencyValueLabel.setText(String.valueOf(newVal.intValue()));
				PlaySoundButton.currentFrequency = newVal.intValue();
			}
		});
	}
	
	public Label getFrequencySliderLabel() {
		return frequencySliderLabel;
	}
	
	public Slider getFrequencySlider() {
		return frequencySlider;
	}
	
	public Label getFrequencyValueLabel() {
		return frequencyValueLabel;
	}
}
