package com.example.synthesizer;

import javafx.application.Application;
import javafx.geometry.Bounds;
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
		AddNewWidgetMenu addNewWidgetMenu = new AddNewWidgetMenu();
		SpeakerWidget speakerWidget = new SpeakerWidget();
		PlayAudioButton playAudioButton = new PlayAudioButton();
		BorderPane root = new BorderPane();
		
		topPane_.setPadding(new Insets(10d));
		topPane_.setAlignment(Pos.CENTER);
		topPane_.getChildren().add(addNewWidgetMenu);
		root.setTop(topPane_);
		
		mainCanvas__ = new AnchorPane();
		mainCanvas__.setPadding(new Insets(50d));
		mainCanvas__.getChildren().add(speakerWidget);
		root.setCenter(mainCanvas__);
		
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
	
	private static HBox topPane_ = new HBox();
	
	public static HBox getTopPane()
	{
		return topPane_;
	}
	
}