package core;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Ball;

public class GameApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Ball b = new Ball(0, 0, 50);
		Ball b1 = new Ball(75, 80, 25);
		Ball b2 = new Ball(125, 0, 36);
		Ball b3 = new Ball(200, 0, 12);
		Ball b4 = new Ball(234, 100, 20);
		Ball b5 = new Ball(532, 0, 63);
		Ball b6 = new Ball(342, 0, 17);
		Ball b7 = new Ball(874, 30, 13);
		Ball b8 = new Ball(396, 0, 29);
		Ball b9 = new Ball(746, 0, 43);
		Ball b10 = new Ball(143, 0, 45);
		Ball b11 = new Ball(901, 4, 50);
		Ball b12 = new Ball(654, 78, 48);
		root.getChildren().add(b);
		root.getChildren().add(b1);
		root.getChildren().add(b2);
		root.getChildren().add(b3);
		root.getChildren().add(b4);
		root.getChildren().add(b5);
		root.getChildren().add(b6);
		root.getChildren().add(b7);
		root.getChildren().add(b8);
		root.getChildren().add(b9);
		root.getChildren().add(b10);
		root.getChildren().add(b11);
		root.getChildren().add(b12);

		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setTitle("Game");
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		primaryStage.setScene(
				new Scene(root, bounds.getWidth(), bounds.getHeight()));
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
