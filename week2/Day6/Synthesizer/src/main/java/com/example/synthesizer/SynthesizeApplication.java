package com.example.synthesizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SynthesizeApplication extends Application {
	
	@Override
	public void start(Stage stage) {
		
		PlaySoundButton playSoundButton = new PlaySoundButton("Press to play the audio");
		SineWaveWidget sineWaveWidget = new SineWaveWidget(0, 1000, 500);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(sineWaveWidget.getWidget());
		borderPane.setBottom(playSoundButton.getButton());
		borderPane.getBottom().setStyle("-fx-alignment: center");
		borderPane.setMinSize(800, 600);
		borderPane.setStyle("-fx-fit-to-width: true; -fx-fit-to-height: true");
		
		Scene scene = new Scene(borderPane);
		stage.setTitle("Lydia's Synthesizer");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
}