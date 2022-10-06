package com.example.synthesizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SynthesizeApplication extends Application {
	
	@Override
	public void start(Stage stage) {
		
		VolumeSliderWidget volumeSliderWidget = new VolumeSliderWidget(0, 200, 100, "Volume");
		FrequencySliderWidget frequencySliderWidget = new FrequencySliderWidget(0, 1000, 500, "Frequency");
		SineWaveWidget sineWaveWidget = new SineWaveWidget();
		WhiteNoiseWidget whiteNoiseWidget = new WhiteNoiseWidget();
		Speaker speaker = new Speaker();
		GlobalSoundManager.updateCurrentActiveAudioComponent(whiteNoiseWidget);
		
		AnchorPane root = new AnchorPane();
		root.setStyle("-fx-min-width: 1000; -fx-min-height: 618");
		
		AnchorPane.setLeftAnchor(frequencySliderWidget.getWidget(), 100d);
		AnchorPane.setTopAnchor(frequencySliderWidget.getWidget(), 100d);
		
		AnchorPane.setRightAnchor(volumeSliderWidget.getWidget(), 100d);
		AnchorPane.setTopAnchor(volumeSliderWidget.getWidget(), 100d);
		
		AnchorPane.setBottomAnchor(sineWaveWidget.getWidget(), 200d);
		AnchorPane.setLeftAnchor(sineWaveWidget.getWidget(), 100d);
		
		AnchorPane.setBottomAnchor(whiteNoiseWidget.getWidget(), 200d);
		AnchorPane.setLeftAnchor(whiteNoiseWidget.getWidget(), 600d);
		
		AnchorPane.setBottomAnchor(speaker.getWidget(), 200d);
		AnchorPane.setRightAnchor(speaker.getWidget(), 100d);
		
		
		
		root.getChildren().add(frequencySliderWidget.getWidget());
		root.getChildren().add(volumeSliderWidget.getWidget());
		root.getChildren().add(sineWaveWidget.getWidget());
		root.getChildren().add(whiteNoiseWidget.getWidget());
		root.getChildren().add(speaker.getWidget());
		
		Scene scene = new Scene(root);
		stage.setTitle("Lydia's Synthesizer");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
}