package com.example.synthesizer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class AudioComponentGraphWidget extends Pane {
	AudioComponentGraphWidget(AudioComponentWidget audioComponentWidget) {
		audioComponentWidget_ = audioComponentWidget;
		AnchorPane pane = new AnchorPane();
		canvas_ = new HBox();
		
		polyline_ = new Polyline();
		polyline_.setStrokeWidth(1.0);
		canvas_.setPadding(new Insets(20.0));
		canvas_.getChildren().add(polyline_);
		
		pane.getChildren().addAll(canvas_);
		stage_ = new Stage();
		Scene scene = new Scene(pane);
		stage_.setTitle("Graph of " + audioComponentWidget_.getName());
		stage_.setScene(scene);
	}
	
	public void showGraph() {
		updatePolyLine();
		stage_.show();
	}
	
	public void updatePolyLine() {
		if (polyline_ != null) {
			canvas_.getChildren().remove(polyline_);
		}
		
		polyline_ = new Polyline();
		polyline_.setStrokeWidth(1.0);
		
		for (int i = 0; i < AudioClip.getRate() / AudioComponentWidget.getDefaultFrequency() * 4; i++) {
			polyline_.getPoints().addAll(
					(double) i, (double) audioComponentWidget_.getAudioComponent().getClip().getSample(i) / Short.MAX_VALUE * 200);
		}
		
		canvas_.getChildren().add(polyline_);
		
	}
	
	private final AudioComponentWidget audioComponentWidget_;
	private Polyline polyline_;
	private final HBox canvas_;
	private final Stage stage_;
	
}
