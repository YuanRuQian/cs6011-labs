package com.example.synthesizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SynthesizeApplication extends Application {
	
	private static AnchorPane mainCanvas__ = new AnchorPane();
	
	@Override
	public void start(Stage stage) {
		
		SpeakerWidget speakerWidget = new SpeakerWidget();
		AddSineWaveWidgetButton addSineWaveWidgetButton = new AddSineWaveWidgetButton();
		AddCosineWaveWidgetButton addCosineWaveWidgetButton = new AddCosineWaveWidgetButton();
		AddSquareWaveWidgetButton addSquareWaveWidgetButton = new AddSquareWaveWidgetButton();
		AddWhiteNoiseWidgetButton addWhiteNoiseWidgetButton = new AddWhiteNoiseWidgetButton();
		AddSawToothWaveWidgetButton addSawToothWaveWidgetButton = new AddSawToothWaveWidgetButton();
		PlayAudioButton playAudioButton = new PlayAudioButton();
		BorderPane root = new BorderPane();
		
		
		mainCanvas__ = new AnchorPane();
		mainCanvas__.setPadding(new Insets(50d));
		mainCanvas__.getChildren().add(speakerWidget);
		root.setCenter(mainCanvas__);
		
		VBox rightPane = new VBox();
		rightPane.setPadding(new Insets(50d));
		rightPane.getChildren().add(addSineWaveWidgetButton);
		rightPane.getChildren().add(addCosineWaveWidgetButton);
		rightPane.getChildren().add(addSquareWaveWidgetButton);
		rightPane.getChildren().add(addWhiteNoiseWidgetButton);
		rightPane.getChildren().add(addSawToothWaveWidgetButton);
		rightPane.setSpacing(20.0);
		
		root.setRight(rightPane);
		
		HBox bottomPane = new HBox();
		bottomPane.setAlignment(Pos.CENTER);
		bottomPane.getChildren().add(playAudioButton);
		bottomPane.setPadding(new Insets(20));
		root.setBottom(bottomPane);
		
		
		Scene scene = new Scene(root, 1000, 800);
		stage.setTitle("Lydia's Synthesizer");
		stage.setScene(scene);
		stage.show();
	}
	
	public static AnchorPane getMainCanvas()
	{
		return mainCanvas__;
	}
	
	public static void addWidgetFromMainCanvas(AudioComponentWidget newWidget)
	{
		mainCanvas__.getChildren().add(newWidget);
	}
	
	public static void main(String[] args) {
		launch();
	}
	
}