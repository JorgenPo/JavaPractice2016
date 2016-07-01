package rbtee_gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rbtree.ITree.INode;

public class VertexGroup extends Group {
	private double centerX, centerY;
	private double size;

	private String key = "NIL";
	private String value = "NIL";
	private Color color = Color.BLACK;

	private Ellipse ellipse = null;
	private Rectangle rect = null;
	private Text txt;

	private VertexGroup parent = null;
	private Line arrow;

	public void setCenterX(double centerX) {
		this.centerX = centerX;
		if (ellipse != null)
			ellipse.setCenterX(centerX);
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
		if (ellipse != null)
			ellipse.setFill(color);
	}

	public void reset() {
		if (color == Color.GREY) {
			color = Color.BLACK;
		} else if (color == Color.PINK) {
			color = Color.RED;
		}
		if (ellipse != null)
			ellipse.setFill(color);
	}

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
		txt.setFill(Color.GREEN);
		txt.setStroke(null);

		if (iNode != null) {
			ellipse = new Ellipse(centerX, centerY, size, size);
			ellipse.setFill(color);
			/*
			 * ellipse.setCenterX(center); ellipse.setCenterY(radius *
			 * countHeight * 2); ellipse.setRadiusX(radius);
			 * ellipse.setRadiusY(radius);
			 */
			super.getChildren().addAll(ellipse, txt);
		} else {
			rect = new Rectangle(centerX, centerY, size, (size / 2));
			rect.setFill(color);
			super.getChildren().addAll(rect, txt);
		}
	}
}
