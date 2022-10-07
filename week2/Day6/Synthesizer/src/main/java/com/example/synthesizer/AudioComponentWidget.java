package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Function;

// TODO: group classes with packages
public class AudioComponentWidget extends Pane {
	// TODO: move all member variables to the bottom
	// TODO: add underscore to all private member variables
	private static double defaultFrequency = 440;
	private final String name;
	private AudioComponent audioComponent;
	private final AnchorPane parent;
	private final Slider frequencySlider;
	private Line line;
	private final Circle socket;
	private final Label label;
	private final Function<Integer, AudioComponent> audioComponentUpdater;
	
	private double mouseBeforeDraggingPositionX;
	private double mouseBeforeDraggingPositionY;
	private double widgetBeforeDraggingPositionX;
	private double widgetBeforeDraggingPositionY;
	private AudioComponentGraphWidget audioComponentGraph;
	
	AudioComponentWidget(AudioComponent audioComponent, AnchorPane parent, String name, Function<Integer, AudioComponent> audioComponentUpdater, Paint color) {
		this.audioComponent = audioComponent;
		this.parent = parent;
		HBox widget = new HBox();
		this.name = name;
		this.audioComponentUpdater = audioComponentUpdater;
		this.audioComponentGraph = new AudioComponentGraphWidget(this);
		
		VBox leftPane = new VBox();
		frequencySlider = new Slider(200, 1000, defaultFrequency);
		label = new Label(getCurrentFrequency(frequencySlider.getValue()));
		label.setStyle("-fx-fit-to-width: true");
		frequencySlider.setOnMouseDragged(mouseEvent -> onFrequencyChange());
		Button showGraphButton = new Button("Show Graph");
		showGraphButton.setOnMouseClicked(mouseEvent -> showGraph());
		
		// make sure the event is captured by the leftPane rather than the nameLabel / slider
		label.setMouseTransparent(true);
		
		leftPane.setSpacing(10.0);
		leftPane.getChildren().add(label);
		leftPane.getChildren().add(frequencySlider);
		leftPane.getChildren().add(showGraphButton);
		leftPane.setAlignment(Pos.CENTER);
		
		leftPane.setOnMousePressed(this::onMousePressed);
		leftPane.setOnMouseDragged(this::onMouseDragged);
		
		VBox rightPane = new VBox();
		FileInputStream input = null;
		try {
			input = new FileInputStream("src/main/java/com/example/synthesizer/closeIcon.png");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		assert input != null;
		Image image = new Image(input, 25, 25, true, true);
		ImageView imageView = new ImageView(image);
		Button closeButton = new Button("", imageView);
		closeButton.setOnMouseClicked(mouseEvent -> removeWidget());
		
		socket = new Circle(10.0);
		socket.setOnMousePressed(this::startConnection);
		socket.setOnMouseDragged(this::moveConnection);
		socket.setOnMouseReleased(this::endConnection);
		rightPane.setSpacing(10.0);
		rightPane.getChildren().add(closeButton);
		rightPane.getChildren().add(socket);
		rightPane.setAlignment(Pos.CENTER);
		
		widget.setStyle("-fx-alignment: center;");
		widget.setSpacing(10.0);
		widget.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		widget.setEffect(new DropShadow(2d, 2d, +2d, Color.BLACK));
		widget.setPadding(new Insets(10d));
		widget.getChildren().add(leftPane);
		widget.getChildren().add(rightPane);
		
		this.getChildren().add(widget);
		this.setLayoutX(100.0);
		this.setLayoutY(100.0);
	}
	
	private void showGraph() {
		audioComponentGraph.showGraph();
	}
	
	private void onFrequencyChange() {
		label.setText(getCurrentFrequency(frequencySlider.getValue()));
		audioComponent = audioComponentUpdater.apply((int) frequencySlider.getValue());
		
		audioComponentGraph.updatePolyLine();
	}
	
	private String getCurrentFrequency(Number currentFrequency) {
		return name + " (" + currentFrequency.intValue() + " Hz)";
	}
	
	private void endConnection(MouseEvent mouseEvent) {
		Circle speaker = SpeakerWidget.getSpeaker();
		Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
		double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - mouseEvent.getSceneX(), 2.0) + Math.pow(speakerBounds.getCenterY() - mouseEvent.getSceneY(), 2.0));
		if (distance > SpeakerWidget.circleWidgetRadius) {
			// if the mouse isn't in the circle, remove the line
			parent.getChildren().remove(line);
			SpeakerWidget.removeWidget(this);
			line = null;
		} else {
			// connect the current widget to the speaker
			SpeakerWidget.addWidget(this);
		}
		
	}
	
	private void moveConnection(MouseEvent mouseEvent) {
		Bounds parentBounds = parent.getBoundsInParent();
		line.setEndX(mouseEvent.getSceneX() - parentBounds.getMinX());
		line.setEndY(mouseEvent.getSceneY() - parentBounds.getMinY());
	}
	
	private Point2D getSocketCenterPosition() {
		Bounds bounds = socket.localToScene(socket.getBoundsInLocal());
		return new Point2D(bounds.getCenterX(), bounds.getCenterY());
	}
	
	
	private void startConnection(MouseEvent mouseEvent) {
		// make sure that one audio component widget can only be connected to one thing
		if (line != null) {
			parent.getChildren().remove(line);
			SpeakerWidget.removeWidget(this);
		}
		Point2D socketCenterPosition = getSocketCenterPosition();
		double endX = mouseEvent.getSceneX();
		double endY = mouseEvent.getSceneY();
		line = new Line(socketCenterPosition.getX(), socketCenterPosition.getY(), endX, endY);
		line.setFill(Color.BLACK);
		line.setStrokeWidth(4);
		parent.getChildren().add(line);
	}
	
	
	private void onMousePressed(MouseEvent mouseEvent) {
		mouseBeforeDraggingPositionX = mouseEvent.getSceneX();
		mouseBeforeDraggingPositionY = mouseEvent.getSceneY();
		widgetBeforeDraggingPositionX = this.getLayoutX();
		widgetBeforeDraggingPositionY = this.getLayoutY();
	}
	
	// TODO: confine the widget in the canvas
	private void onMouseDragged(MouseEvent mouseEvent) {
		double mouseDeltaX = mouseEvent.getSceneX() - mouseBeforeDraggingPositionX;
		double mouseDeltaY = mouseEvent.getSceneY() - mouseBeforeDraggingPositionY;
		this.relocate(widgetBeforeDraggingPositionX + mouseDeltaX, widgetBeforeDraggingPositionY + mouseDeltaY);
		if (line == null) {
			return;
		}
		// move the line along with the widget
		Point2D socketCenterPosition = getSocketCenterPosition();
		line.setStartX(socketCenterPosition.getX());
		line.setStartY(socketCenterPosition.getY());
	}
	
	private void removeWidget() {
		parent.getChildren().remove(this);
		SpeakerWidget.removeWidget(this);
		if (line != null) {
			parent.getChildren().remove(line);
		}
	}
	
	public double getCurrentFrequency()
	{
		return frequencySlider.getValue();
	}
	
	public String getName()
	{
		return name;
	}
	public static double getDefaultFrequency()
	{
		return defaultFrequency;
	}
	
	public AudioComponent getAudioComponent() {
		return audioComponent;
	}
	
}
