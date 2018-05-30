package objects;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball extends Circle {
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private Timeline timeline;
	private BorderPane root;

	private double velocityY, velocityX;
	private static final double G_ACCEL = .1;
	private static final int CORD_SIZE = 50;
	private static ArrayList<Ball> balls = new ArrayList<>();
	private ArrayList<Coordinate> mouseCords;

	public Ball(double x, double y, double r, BorderPane root) {
		super(x, y, r);
		this.randomizeFill();
		this.setOnMouseReleased(new MouseRelased(this));
		this.setOnMousePressed(new MousePressed(this));
		this.setOnMouseDragged(new MouseDragged(this));
		this.timeline = new Timeline(
				new KeyFrame(Duration.millis(16), ae -> this.moveBall()));
		this.timeline.setCycleCount(Animation.INDEFINITE);
		this.velocityY = 0;
		this.velocityX = 0;
		this.timeline.play();
		this.root = root;
		balls.add(this);
	}

	public Ball(double x, double y, double r, Paint color, BorderPane root) {
		super(x, y, r);
		this.setOnMouseReleased(new MouseRelased(this));
		this.setOnMousePressed(new MousePressed(this));
		this.setOnMouseDragged(new MouseDragged(this));
		this.timeline = new Timeline(
				new KeyFrame(Duration.millis(16), ae -> this.moveBall()));
		this.timeline.setCycleCount(Animation.INDEFINITE);
		this.velocityY = 0;
		this.velocityX = 0;
		this.timeline.play();
		this.root = root;
		balls.add(this);
	}

	public void randomizeFill() {
		int R = (int) (Math.random() * 256);
		int G = (int) (Math.random() * 256);
		int B = (int) (Math.random() * 256);
		this.setFill(Color.rgb(R, G, B));
	}

	public void moveBall() {
		// X translations
		if (this.getTranslateY() + this.getCenterY() - this.getRadius() <= 0) {
			this.velocityY = -this.velocityY * .8;
			this.velocityX = this.velocityX * .8;
			this.setTranslateY(this.getRadius() + 1 - this.getCenterY());
		} else if (this.getTranslateY() + this.getCenterY()
				+ this.getRadius() >= this.getScene().getHeight()) {
			this.velocityY = -this.velocityY * .8;
			this.velocityX = this.velocityX * .8;
			this.setTranslateY(this.getScene().getHeight() - this.getRadius()
					- this.getCenterY() - 1);
		} else {
			this.setTranslateY(this.getTranslateY() + this.velocityY);
			if (this.getTranslateY() + this.getCenterY() + this.getRadius()
					+ 5 < this.getScene().getHeight()) {
				this.velocityY = this.velocityY + G_ACCEL;
			}
		}

		// Y translations
		if (this.getTranslateX() + this.getCenterX() - this.getRadius() <= 0) {
			this.velocityX = -this.velocityX * .8;
			this.velocityY = this.velocityY * .8;
			this.setTranslateX(this.getRadius() + 1 - this.getCenterX());
		} else if (this.getTranslateX() + this.getCenterX()
				+ this.getRadius() >= this.getScene().getWidth()) {
			this.velocityX = -this.velocityX * .8;
			this.velocityY = this.velocityY * .8;
			this.setTranslateX(this.getScene().getWidth() - this.getRadius()
					- this.getCenterX() - 1);
		} else {
			this.setTranslateX(this.getTranslateX() + this.velocityX);
		}
		/*
		 * ArrayList<Ball> intersections = this.getIntersections(); if
		 * (!intersections.isEmpty()) { this.setFill(Color.TRANSPARENT);
		 * this.timeline.stop(); this.root.getChildren().remove(this);
		 * this.balls.remove(this); for (Ball b : intersections) {
		 * b.setFill(Color.TRANSPARENT); b.timeline.stop();
		 * b.root.getChildren().remove(b); .balls.remove(b); } }
		 */
	}

	private ArrayList<Ball> getIntersections() {
		ArrayList<Ball> intersections = new ArrayList<>();
		for (Ball otherBall : balls) {
			if (otherBall != this) {
				if (this.getBoundsInParent()
						.intersects(otherBall.getBoundsInParent())) {
					intersections.add(otherBall);
				}
			}
		}
		return intersections;
	}

	public double getVelocityY() {
		return this.velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public double getVelocityX() {
		return this.velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	private class MouseRelased implements EventHandler<MouseEvent> {
		private Ball parent;

		public MouseRelased(Ball p) {
			super();
			this.parent = p;
		}

		@Override
		public void handle(MouseEvent arg0) {
			this.parent.timeline.play();
			this.parent.randomizeFill();

			double curX = this.parent.getTranslateX();
			double curY = this.parent.getTranslateY();
			double sum_diff_x = 0;
			double sum_diff_y = 0;
			int count = 0;
			for (Coordinate c : Ball.this.mouseCords) {
				sum_diff_x += (curX - c.getX());
				sum_diff_y += (curY - c.getY());
				count++;
			}
			this.parent.velocityY = (Double.isNaN((sum_diff_y / count) / 10))
					? this.parent.velocityY
					: (sum_diff_y / count) / 10;
			this.parent.velocityX = (Double.isNaN((sum_diff_x / count) / 10))
					? this.parent.velocityX
					: (sum_diff_x / count) / 10;
			Ball.this.mouseCords.clear();

			this.parent.getTranslateX();
			this.parent.getTranslateY();
		}
	}

	private class MouseDragged implements EventHandler<MouseEvent> {
		private Ball parent;

		public MouseDragged(Ball p) {
			super();
			this.parent = p;
		}

		@Override
		public void handle(MouseEvent arg0) {
			double offsetX = arg0.getSceneX() - Ball.this.orgSceneX;
			double offsetY = arg0.getSceneY() - Ball.this.orgSceneY;
			double newTranslateX = Ball.this.orgTranslateX + offsetX;
			double newTranslateY = Ball.this.orgTranslateY + offsetY;
			this.parent.setTranslateX(newTranslateX);
			this.parent.setTranslateY(newTranslateY);

			Coordinate position = new Coordinate(this.parent.getTranslateX(),
					this.parent.getTranslateY());
			if (Ball.this.mouseCords.size() == CORD_SIZE) {
				Ball.this.mouseCords.remove(0);
			}
			Ball.this.mouseCords.add(position);
		}
	}

	private class MousePressed implements EventHandler<MouseEvent> {
		private Ball parent;

		public MousePressed(Ball p) {
			super();
			this.parent = p;
		}

		@Override
		public void handle(MouseEvent arg0) {
			this.parent.timeline.stop();
			this.parent.velocityY = 0;
			this.parent.mouseCords = new ArrayList<>(CORD_SIZE);
			this.parent.orgSceneX = arg0.getSceneX();
			this.parent.orgSceneY = arg0.getSceneY();
			Ball.this.orgTranslateX = ((Circle) (arg0.getSource()))
					.getTranslateX();
			Ball.this.orgTranslateY = ((Circle) (arg0.getSource()))
					.getTranslateY();
		}
	}
}
