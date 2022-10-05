package com.example.synthesizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SynthesizeApplication extends Application {
	
	@Override
	public void start(Stage stage) {
		
		PlaySoundButton playSoundButton = new PlaySoundButton("Press the button to play the audio");
		FrequencySlider frequencySlider = new FrequencySlider(0, 1000, 500);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(playSoundButton.getButton());
		
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(1000, 618);
		gridPane.setPadding(new Insets(50));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(frequencySlider.getFrequencySliderLabel(), 1, 1);
		gridPane.add(frequencySlider.getFrequencySlider(), 2, 1);
		gridPane.add(frequencySlider.getFrequencyValueLabel(), 3, 1);
		
		borderPane.setCenter(gridPane);
		
		BorderPane.setAlignment(playSoundButton.getButton(), Pos.CENTER);
		Scene scene = new Scene(borderPane);
		stage.setTitle("Lydia's Synthesizer");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
}