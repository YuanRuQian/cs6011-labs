package com.example.synthesizer;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.function.Function;

public abstract class AddAudioComponentWidgetButton extends Pane {
	
	
	AddAudioComponentWidgetButton(String widgetName, Function<Void, AudioComponentWidget> getNewWidget) {
		HBox widget = new HBox();
		Button button = new Button("Add new " + widgetName);
		getNewWidget_ = getNewWidget;
		button.setOnMouseClicked(mouseEvent -> addNewComponentWidget());
		widget.getChildren().add(button);
		this.getChildren().add(widget);
	}
	
	private void addNewComponentWidget() {
		AudioComponentWidget newWidget = getNewWidget_.apply(null);
		// I do not dynamically display new widgets cuz there will be too much work on keeping track of the existing widgets
		newWidget.setLayoutX(200);
		newWidget.setLayoutY(200);
		SynthesizeApplication.addWidgetFromMainCanvas(newWidget);
	}
	
	private final Function<Void, AudioComponentWidget> getNewWidget_;
}
