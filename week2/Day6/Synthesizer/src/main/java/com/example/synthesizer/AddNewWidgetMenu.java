package com.example.synthesizer;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.function.Function;

public class AddNewWidgetMenu extends Pane {
	AddNewWidgetMenu()
	{
		Menu menu = new Menu("Add New Widget");
		MenuItem addSineWave = getAddNewWidgetMenuItem("Sine Wave", (unused -> new AudioComponentWidget(new SineWave(), "Sine Wave", (SineWave::new), Color.CORNSILK)));
		MenuItem addCosineWave = getAddNewWidgetMenuItem("Cosine Wave", (unused -> new AudioComponentWidget(new CosineWave(), "Cosine Wave", (CosineWave::new), Color.LEMONCHIFFON)));
		MenuItem addSquareWave = getAddNewWidgetMenuItem("Square Wave", (unused -> new AudioComponentWidget(new SquareWave(), "Square Wave", (newFrequency -> new SquareWave(newFrequency, Short.MAX_VALUE)), Color.LIGHTCYAN)));
		MenuItem addSawToothWave = getAddNewWidgetMenuItem("Saw Tooth Wave", (unused -> new AudioComponentWidget(new SawToothWave(), "Saw Tooth Wave", (SawToothWave::new), Color.LAVENDERBLUSH)));
		MenuItem addWhiteNoise = getAddNewWidgetMenuItem("White Noise", (unused -> new AudioComponentWidget(new WhiteNoise(), "White Noise", (newFrequency -> new WhiteNoise()), Color.LIGHTGRAY)));
		
		menu.getItems().add(addSineWave);
		menu.getItems().add(addCosineWave);
		menu.getItems().add(addSquareWave);
		menu.getItems().add(addSawToothWave);
		menu.getItems().add(addWhiteNoise);
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		
		this.getChildren().add(menuBar);
	}
	
	private void addNewWidgetToCanvas(Function<Void, AudioComponentWidget> createNewWidget)
	{
		AudioComponentWidget audioComponentWidget = createNewWidget.apply(null);
		audioComponentWidget.setLayoutX(200);
		audioComponentWidget.setLayoutY(200);
		SynthesizeApplication.addWidgetFromMainCanvas(audioComponentWidget);
	}
	
	
	private MenuItem getAddNewWidgetMenuItem(String widgetName, Function<Void, AudioComponentWidget> createNewWidget)
	{
		MenuItem menuItem = new MenuItem(widgetName);
		menuItem.setOnAction(actionEvent -> addNewWidgetToCanvas(createNewWidget));
		return menuItem;
	}
	
}
