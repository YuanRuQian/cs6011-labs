package com.example.synthesizer;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PlayAudioButton extends Pane {
	
	PlayAudioButton() {
		
		button_ = new Button(textBeforePressed);
		button_.setOnMousePressed(mouseEvent -> {
			playAudio();
			button_.setText(textAfterPressed);
		});
		button_.setOnMouseReleased(mouseEvent -> {
			endAudio();
			button_.setText(textBeforePressed);
		});
		setButtonToDisabled();
		this.getChildren().add(button_);
		this.setLayoutX(500);
		this.setLayoutY(700);
	}
	
	private void endAudio() {
		SpeakerWidget.end();
	}
	
	private void playAudio() {
		SpeakerWidget.play();
	}
	
	public static void setButtonToDisabled() {
		button_.setDisable(true);
		button_.setText(textWhenDisabled);
	}
	
	public static void setButtonToActive() {
		button_.setDisable(false);
		button_.setText(textBeforePressed);
	}
	
	private static Button button_ = new Button();
	private static final String textWhenDisabled = "Connect widgets to the speaker to play the audio";
	private static final String textBeforePressed = "Press to play the audio";
	private final String textAfterPressed = "Release to end the audio";
}
