package rbtee_gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import rbtree.ITree.INode;

public class VertexGroup extends Group {
	private double centerX, centerY;
	private double size;

	private String key = "NIL";
	private String value = "NIL";
	private Color color = Color.BLACK;

	private Circle circle = null;
	private Rectangle rect = null;
	private Text txt;

	private VertexGroup parent = null;
	private Line arrow;

	public VertexGroup(INode<String, String> iNode, int countHeight, double center, double radius) {
		centerX = center;
		centerY = radius * countHeight * 2;
		size = radius;

		if (iNode != null) {
			key = String.valueOf(iNode.getKey());
			value = String.valueOf(iNode.getValue());
			color = iNode.isRed() ? Color.RED : Color.BLACK;
		}

		txt = new Text(centerX, centerY, key);
		txt.setBoundsType(TextBoundsType.VISUAL);
		txt.setFill(Color.DARKGREEN);
		txt.setStroke(null);

		if (iNode != null) {
			circle = new Circle(size);
			circle.setFill(color);
			circle.relocate(centerX, centerY);

			centerText(txt, "Circle");
			super.getChildren().addAll(circle, txt);
		} else {
			rect = new Rectangle(size * 2, size);
			rect.setFill(color);
			rect.relocate(centerX, centerY);

			centerText(txt, "Rectangle");
			super.getChildren().addAll(rect, txt);
		}

		this.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldbounds, Bounds bounds) {
				setClip(new Circle(bounds.getMinX(), bounds.getMinY(), radius));
			}
		});
	}

	private void centerText(Text txt, String strTextBounds) {
		double w = txt.getBoundsInLocal().getWidth();
		double h = txt.getBoundsInLocal().getHeight();
		switch (strTextBounds) {
		case "Rectangle":
			txt.relocate(centerX + size - w / 2, centerY + (size - h) / 2);
			break;

		case "Circle":
			txt.relocate(centerX + size - w / 2, centerY + size - h / 2);
			break;

		default:
			break;
		}
		
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
		if (circle != null)
			circle.setCenterX(centerX);
		if (rect != null)
			rect.setX(centerX);
		if (txt != null)
			txt.setX(centerX);
	}

	public void setSelected() {
		if (color == Color.BLACK) {
			color = Color.GREY;
		} else if (color == Color.RED) {
			color = Color.PINK;
		}
		if (circle != null)
			circle.setFill(color);
	}

	public void reset() {
		if (color == Color.GREY) {
			color = Color.BLACK;
		} else if (color == Color.PINK) {
			color = Color.RED;
		}
		if (circle != null)
			circle.setFill(color);
	}
}
