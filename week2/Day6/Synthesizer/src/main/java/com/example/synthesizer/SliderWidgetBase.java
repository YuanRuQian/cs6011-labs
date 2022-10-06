package com.example.synthesizer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.function.Function;

public class SliderWidgetBase {
	private VBox widget;
	private Label label;
	private Slider slider;
	private Function<Number, Void> handleSliderValueChange;
	private Function<Number, String> labelTextUpdater;
	
	SliderWidgetBase() {
		widget = new VBox();
		label = new Label();
		slider = new Slider();
		handleSliderValueChange = (number) -> {
			return null;
		};
		labelTextUpdater = (number) -> {
			label.setText(number.toString());
			return null;
		};
	}
	
	
	SliderWidgetBase(Slider slider, Label label, Function<Number, Void> handleSliderValueChange, Function<Number, String> labelTextUpdater) {
		widget = new VBox();
		widget.setStyle("-fx-background-color: wheat; -fx-max-height: 60; -fx-max-width: 200; -fx-alignment: center;");
		widget.setEffect(new DropShadow(2d, 2d, +2d, Color.BLACK));
		this.slider = slider;
		this.label = label;
		this.handleSliderValueChange = handleSliderValueChange;
		this.labelTextUpdater = labelTextUpdater;
		widget.getChildren().add(label);
		widget.getChildren().add(slider);
		handleChange(slider.getValue());
		slider.valueProperty().addListener(createSliderValueChangeListener());
	}
	
	private ChangeListener<Number> createSliderValueChangeListener() {
		return new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
			                    Number oldVal, Number newVal) {
				handleChange(newVal);
			}
		};
	}
	
	private void handleChange(Number newVal)
	{
		handleSliderValueChange.apply(newVal);
		label.setText(labelTextUpdater.apply(newVal));
	}
	
	public VBox getWidget() {
		return widget;
	}
}
