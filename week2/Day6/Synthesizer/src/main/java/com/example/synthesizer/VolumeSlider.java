package com.example.synthesizer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class VolumeSlider extends Pane {
	private final Slider slider;
	private final Label label;
	private final DecimalFormat df = new DecimalFormat("0.00");
	
	VolumeSlider()
	{
		VBox widget = new VBox();
		slider = new Slider(0, 200, 100);
		slider.setOnMouseDragged(mouseEvent -> onVolumeChange());
		disableSlider();
		label = new Label(getCurrentVolumeLabelText());
		widget.getChildren().add(label);
		widget.getChildren().add(slider);
		widget.setAlignment(Pos.CENTER);
		this.getChildren().add(widget);
	}
	
	private void onVolumeChange() {
		label.setText(getCurrentVolumeLabelText());
		SpeakerWidget.updateVolumeScale(slider.getValue()/100);
	}
	
	private String getCurrentVolumeLabelText()
	{
		return "Volume: " + df.format(slider.getValue()) + "%";
	}
	
	public void enableSlider()
	{
		slider.setDisable(false);
	}
	
	public void disableSlider()
	{
		slider.setDisable(true);
	}
}
