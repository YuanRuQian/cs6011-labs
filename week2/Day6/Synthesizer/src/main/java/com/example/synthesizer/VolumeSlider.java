package com.example.synthesizer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class VolumeSlider extends Pane {
	VolumeSlider()
	{
		VBox widget = new VBox();
		slider_ = new Slider(0, 200, 100);
		slider_.setOnMouseDragged(mouseEvent -> onVolumeChange());
		disableSlider();
		label_ = new Label(getCurrentVolumeLabelText());
		widget.getChildren().add(label_);
		widget.getChildren().add(slider_);
		widget.setAlignment(Pos.CENTER);
		this.getChildren().add(widget);
	}
	
	private void onVolumeChange() {
		label_.setText(getCurrentVolumeLabelText());
		SpeakerWidget.updateVolumeScale(slider_.getValue()/100);
	}
	
	private String getCurrentVolumeLabelText()
	{
		return "Volume: " + df.format(slider_.getValue()) + "%";
	}
	
	public void enableSlider()
	{
		slider_.setDisable(false);
	}
	
	public void disableSlider()
	{
		slider_.setDisable(true);
	}
	
	private final Slider slider_;
	private final Label label_;
	private final DecimalFormat df = new DecimalFormat("0.00");
	
}
