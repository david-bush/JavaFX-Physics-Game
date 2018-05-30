package handlers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import objects.Ball;

public class AddBallsHandler implements EventHandler<KeyEvent> {
	private BorderPane root;
	private double maxRadius;

	public AddBallsHandler(BorderPane root, double maxRadius) {
		this.root = root;
		this.maxRadius = maxRadius;
	}

	@Override
	public void handle(KeyEvent event) {
		if (event.getCode().equals(KeyCode.SPACE)) {
			double radius = (Math.random() * this.maxRadius);
			double xMax = this.root.getWidth() - radius;
			double yMax = this.root.getHeight() - radius;
			double x = (Math.random() * xMax);
			double y = (Math.random() * yMax);
			Ball ball = new Ball(x, y, radius, this.root);
			this.root.getChildren().add(ball);
		} else if (event.getCode().equals(KeyCode.R)) {
			for (Node child : this.root.getChildren()) {
				if (child instanceof Ball) {
					((Ball) child).randomizeFill();
					boolean x_or_y = Math.random() < 0.5;
					boolean neg = Math.random() < 0.5;
					double velocity = neg
							? -(Math.random() * 20)
							: (Math.random() * 20);

					if (x_or_y) {
						((Ball) child).setVelocityX(velocity);
					} else {
						((Ball) child).setVelocityY(velocity);
					}
				}
			}
		}
	}

}
