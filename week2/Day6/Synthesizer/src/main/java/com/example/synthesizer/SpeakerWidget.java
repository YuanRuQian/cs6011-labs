package com.example.synthesizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;

public class SpeakerWidget extends Pane {
	
	private static ArrayList<AudioComponentWidget> connectedWidgets;
	private static Clip clip;
	private static Circle speaker;
	private static Button showGraphButton;
	private static double volumeScale;
	private static VolumeSlider volumeSlider;
	public static double circleWidgetRadius = 20.0;
	
	private static Polyline polyline;
	private static HBox canvas;
	private static Stage stage;
	
	SpeakerWidget() {
		volumeScale = 1;
		volumeSlider = new VolumeSlider();
		connectedWidgets = new ArrayList<>();
		VBox widget = new VBox();
		speaker = new Circle(circleWidgetRadius);
		speaker.setFill(Color.BLACK);
		Label label = new Label("speaker");
		showGraphButton = new Button("Show Graph");
		showGraphButton.setOnMouseClicked(mouseEvent -> showGraph());
		disableShowGraphButton();
		widget.getChildren().add(speaker);
		widget.getChildren().add(label);
		widget.getChildren().add(volumeSlider);
		widget.getChildren().add(showGraphButton);
		widget.setSpacing(20.0);
		widget.setAlignment(Pos.CENTER);
		this.getChildren().add(widget);
		this.setLayoutX(400.0);
		this.setLayoutY(200.0);
		
		// draw graph
		AnchorPane pane = new AnchorPane();
		canvas = new HBox();
		
		polyline = new Polyline();
		polyline.setStrokeWidth(1.0);
		canvas.setPadding(new Insets(20.0));
		canvas.getChildren().add(polyline);
		
		pane.getChildren().addAll(canvas);
		stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setTitle("Graph of Speaker's Filtered & Mixed Results");
		stage.setScene(scene);
	}
	
	public static void updatePolyLine() {
		System.out.println("update poly line");
		if (polyline != null) {
			canvas.getChildren().remove(polyline);
		}
		
		polyline = new Polyline();
		polyline.setStrokeWidth(1.0);
		
		AudioClip resultClip = getCurrentFilteredResult().getClip();
		for (int i = 0; i < AudioClip.getRate() / AudioComponentWidget.getDefaultFrequency() * 4; i++) {
			polyline.getPoints().addAll(
					(double) i, (double) resultClip.getSample(i) / Short.MAX_VALUE * 200);
		}
		canvas.getChildren().add(polyline);
		System.out.println("end poly line");
	}
	
	private void showGraph() {
		updatePolyLine();
		stage.show();
	}
	
	public static Circle getSpeaker() {
		return speaker;
	}
	
	public static void addWidget(AudioComponentWidget newWidget) {
		connectedWidgets.add(newWidget);
		volumeSlider.enableSlider();
		PlayAudioButton.setButtonToActive();
		enableShowGraphButton();
	}
	
	public static void removeWidget(AudioComponentWidget oldWidget) {
		connectedWidgets.remove(oldWidget);
		if (connectedWidgets.isEmpty()) {
			PlayAudioButton.setButtonToDisabled();
			disableShowGraphButton();
			volumeSlider.disableSlider();
		} else {
			PlayAudioButton.setButtonToActive();
		}
	}
	
	private static void playAudioClipFrom(AudioClip audioClip) {
		clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
		try {
			clip.open(format16, audioClip.getData(), 0, audioClip.getData().length);
		} catch (LineUnavailableException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		clip.start();
	}
	
	private static void endPlayingAudioClip() {
		clip.close();
	}
	
	private static void disableShowGraphButton() {
		showGraphButton.setDisable(true);
	}
	
	private static void enableShowGraphButton() {
		showGraphButton.setDisable(false);
	}
	
	private static VolumeFilter getCurrentFilteredResult() {
		// mix all connected audio clips
		AdditionMixer additionMixer = new AdditionMixer();
		for (AudioComponentWidget widget : connectedWidgets) {
			AudioComponent newInput = widget.getAudioComponent();
			additionMixer.connectInput(newInput);
		}
		// adjust the volume
		return new VolumeFilter(additionMixer, volumeScale);
	}
	
	
	public static void play() {
		playAudioClipFrom(getCurrentFilteredResult().getClip());
	}
	
	public static void end() {
		endPlayingAudioClip();
	}
	
	public static void updateVolumeScale(double newVolumeScale) {
		volumeScale = newVolumeScale;
		updatePolyLine();
	}
	
}
