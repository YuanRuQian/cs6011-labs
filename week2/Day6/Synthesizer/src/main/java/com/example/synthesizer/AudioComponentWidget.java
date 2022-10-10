package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Function;

public class AudioComponentWidget extends Pane {
	AudioComponentWidget(AudioComponent audioComponent, String name, Function<Integer, AudioComponent> audioComponentUpdater, Paint color) {
		audioComponent_ = audioComponent;
		parent_ = SynthesizeApplication.getMainCanvas();
		HBox widget = new HBox();
		name_ = name;
		audioComponentUpdater_ = audioComponentUpdater;
		audioComponentGraph_ = new AudioComponentGraphWidget(this);
		
		VBox leftPane = new VBox();
		
		
		Button showGraphButton = new Button("Show Graph");
		showGraphButton.setOnMouseClicked(mouseEvent -> showGraph());
		
		leftPane.setSpacing(10.0);
		
		// white noise is not impacted by frequency
		if (!(audioComponent_ instanceof WhiteNoise)) {
			FrequencySlider frequencySlider = new FrequencySlider(name_, this);
			leftPane.getChildren().add(frequencySlider);
		}
		
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
		
		socket_ = new Circle(10.0);
		socket_.setOnMousePressed(this::startConnection);
		socket_.setOnMouseDragged(this::moveConnection);
		socket_.setOnMouseReleased(this::endConnection);
		rightPane.setSpacing(10.0);
		rightPane.getChildren().add(closeButton);
		rightPane.getChildren().add(socket_);
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
	
	public void updateAudioComponent(AudioComponent newAudioComponent) {
		audioComponent_ = newAudioComponent;
	}
	
	private void showGraph() {
		audioComponentGraph_.showGraph();
	}
	
	private void endConnection(MouseEvent mouseEvent) {
		Circle speaker = SpeakerWidget.getSpeaker();
		Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
		double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - mouseEvent.getSceneX(), 2.0) + Math.pow(speakerBounds.getCenterY() - mouseEvent.getSceneY(), 2.0));
		if (distance > SpeakerWidget.circleWidgetRadius) {
			// if the mouse isn't in the circle, remove the line
			parent_.getChildren().remove(line_);
			SpeakerWidget.removeWidget(this);
			line_ = null;
		} else {
			// connect the current widget to the speaker
			SpeakerWidget.addWidget(this);
		}
		
	}
	
	private void moveConnection(MouseEvent mouseEvent) {
		Bounds parentBounds = parent_.getBoundsInParent();
		line_.setEndX(mouseEvent.getSceneX() - parentBounds.getMinX());
		line_.setEndY(mouseEvent.getSceneY() - parentBounds.getMinY());
	}
	
	private Point2D getSocketCenterPosition() {
		Double topPaneHeight = SynthesizeApplication.getTopPane().getHeight();
		Bounds bounds = socket_.localToScene(socket_.getBoundsInLocal());
		return new Point2D(bounds.getCenterX(), bounds.getCenterY() - topPaneHeight);
	}
	
	
	private void startConnection(MouseEvent mouseEvent) {
		// make sure that one audio component widget can only be connected to one thing
		if (line_ != null) {
			parent_.getChildren().remove(line_);
			SpeakerWidget.removeWidget(this);
		}
		Point2D socketCenterPosition = getSocketCenterPosition();
		double endX = mouseEvent.getSceneX();
		double endY = mouseEvent.getSceneY();
		line_ = new Line(socketCenterPosition.getX(), socketCenterPosition.getY(), endX, endY);
		line_.setFill(Color.BLACK);
		line_.setStrokeWidth(4);
		parent_.getChildren().add(line_);
	}
	
	private void onMousePressed(MouseEvent mouseEvent) {
		mouseBeforeDraggingPositionX_ = mouseEvent.getSceneX();
		mouseBeforeDraggingPositionY_ = mouseEvent.getSceneY();
		widgetBeforeDraggingPositionX_ = this.getLayoutX();
		widgetBeforeDraggingPositionY_ = this.getLayoutY();
	}
	
	private void onMouseDragged(MouseEvent mouseEvent) {
		double mouseDeltaX = mouseEvent.getSceneX() - mouseBeforeDraggingPositionX_;
		double mouseDeltaY = mouseEvent.getSceneY() - mouseBeforeDraggingPositionY_;
		this.relocate(widgetBeforeDraggingPositionX_ + mouseDeltaX, widgetBeforeDraggingPositionY_ + mouseDeltaY);
		if (line_ == null) {
			return;
		}
		// move the line along with the widget
		Point2D socketCenterPosition = getSocketCenterPosition();
		line_.setStartX(socketCenterPosition.getX());
		line_.setStartY(socketCenterPosition.getY());
	}
	
	private void removeWidget() {
		parent_.getChildren().remove(this);
		SpeakerWidget.removeWidget(this);
		if (line_ != null) {
			parent_.getChildren().remove(line_);
		}
	}
	
	public String getName() {
		return name_;
	}
	
	public static double getDefaultFrequency() {
		return defaultFrequency_;
	}
	
	public AudioComponent getAudioComponent() {
		return audioComponent_;
	}
	
	public Function<Integer, AudioComponent> getAudioComponentUpdater() {
		return audioComponentUpdater_;
	}
	
	public AudioComponentGraphWidget getAudioComponentGraph() {
		return audioComponentGraph_;
	}
	
	private static final double defaultFrequency_ = 440;
	private final String name_;
	private AudioComponent audioComponent_;
	private final AnchorPane parent_;
	private Line line_;
	private final Circle socket_;
	private final Function<Integer, AudioComponent> audioComponentUpdater_;
	
	private double mouseBeforeDraggingPositionX_;
	private double mouseBeforeDraggingPositionY_;
	private double widgetBeforeDraggingPositionX_;
	private double widgetBeforeDraggingPositionY_;
	private final AudioComponentGraphWidget audioComponentGraph_;
}
