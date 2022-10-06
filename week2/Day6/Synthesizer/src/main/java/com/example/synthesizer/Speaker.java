package com.example.synthesizer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import static javax.sound.sampled.AudioSystem.getClip;

public class Speaker extends GlobalSoundManager {
	private VBox widget;
	private Button button;
	private Label label;
	private String playTip = "Press to play the audio";
	private String endTip = "Release to end the audio";
	
	Speaker() {
		super();
		widget = new VBox();
		button = new Button(playTip);
		Label label = new Label("Speaker");
		widget.setStyle("-fx-background-color: cornsilk; -fx-max-height: 60; -fx-max-width: 200; -fx-alignment: center;");
		widget.setEffect(new DropShadow(2d, 2d, +2d, Color.BLACK));
		widget.setPadding(new Insets(10d));
		widget.getChildren().add(label);
		widget.getChildren().add(button);
		button.setOnMousePressed(mouseEvent -> play());
		button.setOnMouseReleased(mouseEvent -> end());
	}
	
	private void play() {
		button.setText(endTip);
		globalClip = null;
		try {
			globalClip = getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
		try {
			AudioClip currentActiveAudioClip = getCurrentActiveAudioComponent().getAudioComponent().getClip();
			globalClip.open(format16, currentActiveAudioClip.getData(), 0, currentActiveAudioClip.getData().length);
		} catch (LineUnavailableException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("About to play an audio clip");
		globalClip.start();
		globalClip.loop(1);
	}
	
	private void end() {
		globalClip.close();
		button.setText(playTip);
		System.out.println("End of clip");
	}
	
	public VBox getWidget() {
		return widget;
	}
}
