package com.example.synthesizer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class FrequencySlider extends Pane {
	FrequencySlider(String name, AudioComponentWidget audioComponentWidget) {
		name_ = name;
		audioComponentWidget_ = audioComponentWidget;
		VBox widget = new VBox();
		slider_ = new Slider(200, 800, defaultFrequency);
		slider_.setOnMouseDragged(mouseEvent -> onFrequencyChange());
		label_ = new Label(onCurrentFrequencyLabelText());
		// make sure the event is captured by the leftPane rather than the nameLabel / slider
		label_.setMouseTransparent(true);
		widget.getChildren().add(label_);
		widget.getChildren().add(slider_);
		widget.setAlignment(Pos.CENTER);
		this.getChildren().add(widget);
	}
	
	private void onFrequencyChange() {
		label_.setText(onCurrentFrequencyLabelText());
		audioComponentWidget_.updateAudioComponent(audioComponentWidget_.getAudioComponentUpdater().apply((int) getValue()));
		audioComponentWidget_.getAudioComponentGraph().updatePolyLine();
		SpeakerWidget.updatePolyLine();
	}
	
	private String onCurrentFrequencyLabelText() {
		return name_ + " (" + (int) getValue() + " Hz)";
	}
	
	public double getValue() {
		return slider_.getValue();
	}
	
	private final Slider slider_;
	private final Label label_;
	private final String name_;
	private static final double defaultFrequency = 500;
	private final AudioComponentWidget audioComponentWidget_;
}
