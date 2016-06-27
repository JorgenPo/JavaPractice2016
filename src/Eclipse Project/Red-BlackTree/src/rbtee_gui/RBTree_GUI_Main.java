package rbtee_gui;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

/**
 * GUI of application.
 * 
 * @author comaralex
 * 
 * p.s. WWW - check to the future... need remove after checking
 * p.s. only for author.. debug..
 */

public class RBTree_GUI_Main extends Application {
	
	// For graphics
	GraphicsContext gc;
	Color[] mColors = { Color.RED, Color.BLUE, Color.GREEN, Color.BLACK };
	int mColorIdx = 0;
	
	/**
	 * Method main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		System.out.println("Method (start).");

		// Set title for application
		mainStage.setTitle("Red-Black tree visual editor");

		// Nodes (Layouts)
		// Main
		GridPane node_root = new GridPane(); // Main Node (GridPane of rows and columns)
		FlowPane node_top = new FlowPane(10, 10);
		FlowPane node_left = new FlowPane(10, 10);
		FlowPane node_center = new FlowPane(10, 10);
		// Internal
		FlowPane node_leftKey = new FlowPane(10, 10);
		FlowPane node_leftValue = new FlowPane(10, 10);
		
		// Scene for buttons and graphics
		Scene mainScene = new Scene(node_root, 800, 450);
		mainStage.setScene(mainScene);

		// Labels
		Label label_welcome = new Label("Status bar! (test)");
		label_welcome.setFont(new Font(16));
		Label label_authors = new Label("Authors: Komarov, Popov.");
		Label label_graph = new Label("Graph of RBTree. (Test version of graphics..)");
		Label label_key = new Label("Key");
		Label label_value = new Label("Value");
		
		// Buttons
		Button button_addElement = new Button("Add element...");
		Button button_getElement = new Button("Get element...");
		// WWW Testing button_changeColor
		Button button_changeColor = new Button("Change color.");
		button_changeColor.setOnAction( (ae) -> {
			gc.setStroke(mColors[mColorIdx]);
			gc.setFill(mColors[mColorIdx]);
			
			gc.strokeLine(0, 0, 200, 200);
			gc.fillText("Text", 60, 50);
			gc.fillRect(100, 320, 300, 40);
			
			mColorIdx++;
			if (mColorIdx == mColors.length)
				mColorIdx = 0;
		});
		
		// Actions
		button_addElement.setOnAction( (ae) -> label_welcome.setText("Button AddElement was pressed."));
		/*button_addElement.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				label_welcome.setText("Button AddElement was pressed.");
			}
		});*/
		button_getElement.setOnAction( (ae) -> label_welcome.setText("Button GetElement was pressed."));
		
		// Edits
		TextField lineEdit_key = new TextField();
		TextField lineEdit_value = new TextField();
		
		// Graphics
		Canvas canvas_graphOfRBTree = new Canvas(400, 400); // Canvas for RBTree's painting.
		gc = canvas_graphOfRBTree.getGraphicsContext2D();
		// WWW Remove graphics items.
		gc.strokeLine(0, 0, 200, 200);
		gc.strokeOval(100, 100, 200, 200);
		gc.strokeRect(0, 200, 50, 200);
		gc.fillOval(0, 0, 20, 20);
		gc.fillRect(100, 320, 300, 40);
		gc.setFont(new Font(20));
		gc.fillText("Text", 60, 50);
		node_root.getChildren().addAll(canvas_graphOfRBTree,
				button_changeColor);
		node_root.getChildren().add(label_welcome);
		
		// Geometry of Nodes (Panes, Layouts)
		node_root.getChildren().addAll(button_addElement,
				button_getElement);
		GridPane.setRowIndex(node_top, 0);
		GridPane.setColumnIndex(node_top, 0);
		GridPane.setColumnSpan(node_top, 2);
		GridPane.setRowIndex(node_left, 1);
		GridPane.setColumnIndex(node_left, 0);
		node_left.setOrientation(Orientation.VERTICAL);
		GridPane.setRowIndex(node_center, 1);
		GridPane.setColumnIndex(node_center, 1);
		// node_center.setOrientation(Orientation.VERTICAL);
		node_root.getChildren().addAll(node_top, node_left, node_center);
		
		// Geometry. FlowPane node_top.
		node_top.getChildren().addAll(label_welcome, label_authors);
		
		// Geometry. FlowPane node_left.
		node_leftKey.getChildren().addAll(label_key, lineEdit_key);
		node_leftValue.getChildren().addAll(label_value, lineEdit_value);
		node_left.getChildren().addAll(node_leftKey, node_leftValue, 
				button_addElement, button_getElement, button_changeColor);
		
		// Geometry. FlowPane node_center.
		node_center.getChildren().addAll(label_graph);
		node_center.getChildren().addAll(canvas_graphOfRBTree);
		
		mainStage.show();
	}
	
	// If need initialization of parameters at starting.
	public void init() {
		System.out.println("Method (init).");
	}
	
	// If need add actions after stopping application.
	public void stop() {
		System.out.println("Method (stop).");
	}
}