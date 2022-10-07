package com.example.synthesizer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class AudioComponentGraphWidget extends Pane {
	private final AudioComponentWidget audioComponentWidget;
	private Polyline polyline;
	private AnchorPane pane;
	private HBox canvas;
	private Stage stage;
	private Scene scene;
	
	AudioComponentGraphWidget(AudioComponentWidget audioComponentWidget) {
		this.audioComponentWidget = audioComponentWidget;
		pane = new AnchorPane();
		canvas = new HBox();
		
		polyline = new Polyline();
		polyline.setStrokeWidth(1.0);
		canvas.setPadding(new Insets(20.0));
		canvas.getChildren().add(polyline);
		
		pane.getChildren().addAll(canvas);
		stage = new Stage();
		scene = new Scene(pane);
		stage.setTitle("Graph of " + audioComponentWidget.getName());
		stage.setScene(scene);
	}
	
	public void showGraph() {
		updatePolyLine();
		stage.show();
	}
	
	
	public void updatePolyLine() {
		if (polyline != null) {
			canvas.getChildren().remove(polyline);
		}
		
		polyline = new Polyline();
		polyline.setStrokeWidth(1.0);
		
		
		for (int i = 0; i < AudioClip.getRate() / AudioComponentWidget.getDefaultFrequency() * 4; i++) {
			polyline.getPoints().addAll(
					(double) i, (double) audioComponentWidget.getAudioComponent().getClip().getSample(i) / Short.MAX_VALUE * 200);
		}
		
		canvas.getChildren().add(polyline);
		
	}
	
	
}
