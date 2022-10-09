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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SynthesizeApplication extends Application {
	
	
	
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
		stage.setScene(scene);
		stage.setTitle("Lydia's Synthesizer");
		setTaskBarIcon();
		stage.show();
	}
	
	private static void setTaskBarIcon()
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("src/main/java/com/example/synthesizer/dockAppIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Taskbar taskbar = Taskbar.getTaskbar();
		try {
			taskbar.setIconImage(img);
		} catch (final UnsupportedOperationException e) {
			System.out.println("The os does not support: 'taskbar.setIconImage'");
		} catch (final SecurityException e) {
			System.out.println("There was a security exception for: 'taskbar.setIconImage'");
		}
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
	
	private static AnchorPane mainCanvas__ = new AnchorPane();
	
}