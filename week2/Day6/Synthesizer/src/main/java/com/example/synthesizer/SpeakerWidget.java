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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;

public class SpeakerWidget extends Pane {
	SpeakerWidget() {
		volumeScale_ = 1;
		volumeSlider_ = new VolumeSlider();
		connectedWidgets_ = new ArrayList<>();
		VBox widget = new VBox();
		speaker_ = new Circle(circleWidgetRadius);
		speaker_.setFill(Color.BLACK);
		Label label = new Label("speaker");
		showGraphButton_ = new Button(emptyWidgetsButtonText);
		showGraphButton_.setTextAlignment(TextAlignment.CENTER);
		showGraphButton_.setOnMouseClicked(mouseEvent -> showGraph());
		disableShowGraphButton();
		widget.getChildren().add(speaker_);
		widget.getChildren().add(label);
		widget.getChildren().add(volumeSlider_);
		widget.getChildren().add(showGraphButton_);
		widget.setSpacing(20.0);
		widget.setAlignment(Pos.CENTER);
		this.getChildren().add(widget);
		this.setLayoutX(400.0);
		this.setLayoutY(200.0);
		
		// draw graph
		AnchorPane pane = new AnchorPane();
		canvas_ = new HBox();
		
		polyline_ = new Polyline();
		polyline_.setStrokeWidth(1.0);
		canvas_.setPadding(new Insets(20.0));
		canvas_.getChildren().add(polyline_);
		
		pane.getChildren().addAll(canvas_);
		stage_ = new Stage();
		Scene scene = new Scene(pane);
		stage_.setTitle("Graph of Speaker's Filtered & Mixed Results");
		stage_.setScene(scene);
	}
	
	public static void updatePolyLine() {
		if(connectedWidgets_.isEmpty())
		{
			return;
		}
		if (polyline_ != null) {
			canvas_.getChildren().remove(polyline_);
		}
		
		polyline_ = new Polyline();
		polyline_.setStrokeWidth(1.0);
		
		AudioClip resultClip = getCurrentFilteredResult().getClip();
		for (int i = 0; i < AudioClip.getRate() / AudioComponentWidget.getDefaultFrequency() * 4; i++) {
			polyline_.getPoints().addAll(
					(double) i, (double) resultClip.getSample(i) / Short.MAX_VALUE * 200);
		}
		canvas_.getChildren().add(polyline_);
	}
	
	private void showGraph() {
		updatePolyLine();
		stage_.show();
	}
	
	public static Circle getSpeaker() {
		return speaker_;
	}
	
	public static void addWidget(AudioComponentWidget newWidget) {
		connectedWidgets_.add(newWidget);
		volumeSlider_.enableSlider();
		PlayAudioButton.setButtonToActive();
		enableShowGraphButton();
		showGraphButton_.setText(buttonText);
		updatePolyLine();
	}
	
	public static void removeWidget(AudioComponentWidget oldWidget) {
		connectedWidgets_.remove(oldWidget);
		if (connectedWidgets_.isEmpty()) {
			PlayAudioButton.setButtonToDisabled();
			disableShowGraphButton();
			volumeSlider_.disableSlider();
			showGraphButton_.setText(emptyWidgetsButtonText);
			updatePolyLine();
		} else {
			PlayAudioButton.setButtonToActive();
		}
	}
	
	private static void playAudioClipFrom(AudioClip audioClip) {
		clip_ = null;
		try {
			clip_ = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
		try {
			clip_.open(format16, audioClip.getData(), 0, audioClip.getData().length);
		} catch (LineUnavailableException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		clip_.start();
	}
	
	private static void endPlayingAudioClip() {
		clip_.close();
	}
	
	private static void disableShowGraphButton() {
		showGraphButton_.setDisable(true);
	}
	
	private static void enableShowGraphButton() {
		showGraphButton_.setDisable(false);
	}
	
	private static VolumeFilter getCurrentFilteredResult() {
		// mix all connected audio clips
		AdditionMixer additionMixer = new AdditionMixer();
		for (AudioComponentWidget widget : connectedWidgets_) {
			AudioComponent newInput = widget.getAudioComponent();
			additionMixer.connectInput(newInput);
		}
		// adjust the volume
		return new VolumeFilter(additionMixer, volumeScale_);
	}
	
	
	public static void play() {
		playAudioClipFrom(getCurrentFilteredResult().getClip());
	}
	
	public static void end() {
		endPlayingAudioClip();
	}
	
	public static void updateVolumeScale(double newVolumeScale) {
		volumeScale_ = newVolumeScale;
		updatePolyLine();
	}
	
	private static ArrayList<AudioComponentWidget> connectedWidgets_;
	private static Clip clip_;
	private static Circle speaker_;
	private static Button showGraphButton_;
	private static double volumeScale_;
	private static VolumeSlider volumeSlider_;
	public static double circleWidgetRadius = 20.0;
	
	private static Polyline polyline_;
	private static HBox canvas_;
	private static Stage stage_;
	private static final String emptyWidgetsButtonText = "Connect Widgets to\n See Result Graph";
	private static final String buttonText = "Show Graph";
	
}
