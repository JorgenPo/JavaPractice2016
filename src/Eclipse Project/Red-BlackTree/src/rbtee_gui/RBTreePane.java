package rbtee_gui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

/**
 * TODO Add declaration. GUI Class ...
 * 
 * @author COMar-PC
 */
public class RBTreePane extends BorderPane {

	private ToolBar toolBar;
	private TilePane vbLeft;
	private VBox vbCenter;
	private HBox hbLeft, hbStatusBar;
	
	private Label labelStatus;
	private Button btnAddElement, btnGetElement;
	private Button btnChangeColor;

	// For graphics
	private GraphicsContext gc;
	private Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.BLACK };
	private int colorIdx = 0;

	/**
	 * TODO Add declaration of starting method.
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		System.out.println("Method (start).");

		toolBar = makeToolBar();
		hbStatusBar = makeStatusBar();
		vbCenter = makeNodeCenter();
		// Separate LeftPane and CenterPane
		Separator separatorLeftCenter = new Separator(Orientation.VERTICAL);
		vbLeft = makeNodeLeft();
		hbLeft = new HBox();
	    hbLeft.getChildren().addAll(vbLeft, separatorLeftCenter);
	    
		setLayouts();
	}

	/**
	 * Locates panes in the right place.
	 */
	private void setLayouts() {
	    setTop(toolBar);
	    setLeft(hbLeft);
	    setCenter(vbCenter);
	    setBottom(hbStatusBar);
	}

	/**
	 * Creates and returns status bar of application. Locates certain items in the center pane.
	 * 
	 * @return status bar of application
	 */
	private HBox makeStatusBar() {
		// Label
		labelStatus = new Label("Status bar...");
		
		HBox statusBar = new HBox();
		statusBar.setPadding(new Insets(5, 20, 5, 5));
		statusBar.getChildren().addAll(labelStatus);
		
		return statusBar;
	}

	/**
	 * Creates and returns tool bar of application. Locates certain items in the center pane.
	 * 
	 * @return tool bar of application
	 */
	private ToolBar makeToolBar() {
		// Open file with data for tree painting.
		Button btnOpenFile = new Button("Open...");
		btnOpenFile.setOnAction( (ae) -> {
			labelStatus.setText("Open file clicked.");
			// TODO Add method Open file.
		});
		// Save data of current tree to the file.
		Button btnSaveToFile = new Button("Save...");
		btnSaveToFile.setOnAction( (ae) -> {
			labelStatus.setText("Save to file clicked.");
			// TODO Add method Save to file.
		});
		// Separate buttons Files and Steps
		Separator separatorFilesSteps = new Separator(Orientation.VERTICAL);
		// Start visualization of tree.
		Button btnVisualize = new Button("Visualize");
		btnVisualize.setOnAction( (ae) -> {
			labelStatus.setText("Visualize clicked.");
			// TODO Add method Visualize tree.
		});
		// Start visualization of tree.
		Button btnStepPrev = new Button("<<");
		btnStepPrev.setOnAction( (ae) -> {
			labelStatus.setText("Previous step clicked.");
			// TODO Add method Previous step.
		});
		// Start visualization of tree.
		Button btnStepNext = new Button(">>");
		btnStepNext.setOnAction( (ae) -> {
			labelStatus.setText("Next step clicked.");
			// TODO Add method Next step.
		});
		
		ToolBar toolBar = new ToolBar(btnOpenFile, btnSaveToFile, separatorFilesSteps,
				btnVisualize, btnStepPrev, btnStepNext);
		toolBar.setPadding(new Insets(3, 20, 5, 10));
		
		return toolBar;
	}
	
	/**
	 * Creates and returns center pane of application. Locates certain items in the center pane.
	 * 
	 * @return center pane of application
	 */
	private VBox makeNodeCenter() {
		// Label of the graph
		Label labelGraph = new Label("Graph of RBTree. (Test version of graphics..)");
		// Graphic
		Canvas canvasGraphOfRBTree = new Canvas(400, 400); // Canvas for RBTree's painting.
		gc = canvasGraphOfRBTree.getGraphicsContext2D();
		// TODO Remove graphics items.
		gc.strokeLine(0, 0, 200, 200);
		gc.strokeOval(100, 100, 200, 200);
		gc.strokeRect(0, 200, 50, 200);
		gc.fillOval(0, 0, 20, 20);
		gc.fillRect(100, 320, 300, 40);
		gc.setFont(new Font(20));
		gc.fillText("Text", 60, 50);

		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(5, 20, 10, 5));
		centerPane.getChildren().addAll(labelGraph, canvasGraphOfRBTree);
		
		return centerPane;
	}

	/**
	 * Creates and returns left pane of application. Locates certain items in the left pane.
	 * 
	 * @return left pane of application
	 */
	private TilePane makeNodeLeft() {
		// Layout for label key and textField of key
		Label labelKey = new Label("Key");
		labelKey.setMinWidth(38);
		TextField txtFieldKey = new TextField();
		txtFieldKey.setPrefWidth(50);
		txtFieldKey.setMaxWidth(50);
		HBox hbLeftKey = new HBox();
		hbLeftKey.setAlignment(Pos.BASELINE_CENTER);
		hbLeftKey.getChildren().addAll(labelKey, txtFieldKey);
		// Layout for label value and textField of value
		Label labelValue = new Label("Value");
		labelValue.setMinWidth(38);
		TextField txtFieldValue = new TextField();
		txtFieldValue.setPrefWidth(50);
		txtFieldValue.setMaxWidth(50);
		HBox hbLeftValue = new HBox();
		hbLeftValue.setAlignment(Pos.BASELINE_CENTER);
		hbLeftValue.getChildren().addAll(labelValue, txtFieldValue);
		// Separate TextFields and control Buttons
		Separator separatorFieldsButtons = new Separator(Orientation.HORIZONTAL);
		separatorFieldsButtons.setMinHeight(25);
		// Buttons
		btnAddElement = new Button("Add element...");
		btnGetElement = new Button("Get element...");
		// TODO Delete it. Testing button_changeColor
		btnChangeColor = new Button("Change color.");
		btnChangeColor.setOnAction( (ae) -> {
			gc.setStroke(colors[colorIdx]);
			gc.setFill(colors[colorIdx]);

			gc.strokeLine(0, 0, 200, 200);
			gc.fillText("Text", 60, 50);
			gc.fillRect(100, 320, 300, 40);

			colorIdx++;
			if (colorIdx == colors.length)
				colorIdx = 0;
		});

		// Actions
		btnAddElement.setOnAction( (ae) -> labelStatus.setText("Button AddElement was pressed."));
		btnGetElement.setOnAction( (ae) -> labelStatus.setText("Button GetElement was pressed."));

		TilePane leftPane = new TilePane(Orientation.VERTICAL);
		leftPane.setPadding(new Insets(20, 20, 20, 20));
		leftPane.setVgap(8);
		leftPane.setAlignment(Pos.BASELINE_CENTER);
		leftPane.getChildren().addAll(hbLeftKey, hbLeftValue, separatorFieldsButtons,
				btnAddElement, btnGetElement, btnChangeColor);
		return leftPane;
	}
}
