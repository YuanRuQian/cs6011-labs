package com.example.synthesizer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.function.Function;

public class AudioComponentWidgetBase {
	public VBox widget;
	private final Label frequencyLabel;
	private final Label volumeLabel;
	private AudioComponent audioComponent;
	Function<Void, AudioComponent> getUpdatedCurrentInput;
	
	AudioComponentWidgetBase(AudioComponent audioComponent, String componentNameText, Function<Void, AudioComponent> getUpdatedCurrentInput) {
		this.audioComponent = audioComponent;
		widget = new VBox();
		widget.setStyle("-fx-background-color: azure; -fx-max-height: 60; -fx-max-width: 200; -fx-alignment: center;");
		widget.setEffect(new DropShadow(2d, 2d, +2d, Color.BLACK));
		widget.setPadding(new Insets(10d));
		frequencyLabel = new Label(getFrequencyLabelText());
		volumeLabel = new Label(getVolumeLabelText());
		this.getUpdatedCurrentInput = getUpdatedCurrentInput;
		Label componentName = new Label(componentNameText + "\n");
		widget.getChildren().add(frequencyLabel);
		widget.getChildren().add(volumeLabel);
		widget.getChildren().add(componentName);
	}
	
	private String getFrequencyLabelText() {
		return "Frequency: " + GlobalSoundManager.getFrequency();
	}
	
	private String getVolumeLabelText() {
		return "Volume: " + GlobalSoundManager.getVolume();
	}
	
	public void updateFrequencyText() {
		frequencyLabel.setText(getFrequencyLabelText());
	}
	
	public void updateVolumeText() {
		volumeLabel.setText(getVolumeLabelText());
	}
	
	public VBox getWidget() {
		return widget;
	}
	
	public AudioComponent getAudioComponent()
	{
		return audioComponent;
	}
}
