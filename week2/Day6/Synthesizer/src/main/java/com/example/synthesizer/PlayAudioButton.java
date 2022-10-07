package com.example.synthesizer;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PlayAudioButton extends Pane {
	private static Button button = new Button();
	private static final String textWhenDisabled = "Connect widgets to the speaker to play the audio";
	private static final String textBeforePressed = "Press to play the audio";
	private final String textAfterPressed = "Release to end the audio";
	
	PlayAudioButton() {
		
		button = new Button(textBeforePressed);
		button.setOnMousePressed(mouseEvent -> {
			playAudio();
			button.setText(textAfterPressed);
		});
		button.setOnMouseReleased(mouseEvent -> {
			endAudio();
			button.setText(textBeforePressed);
		});
		setButtonToDisabled();
		this.getChildren().add(button);
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
		button.setDisable(true);
		button.setText(textWhenDisabled);
	}
	
	public static void setButtonToActive() {
		button.setDisable(false);
		button.setText(textBeforePressed);
	}
}
