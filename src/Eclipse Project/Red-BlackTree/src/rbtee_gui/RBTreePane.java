package rbtee_gui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.geometry.Insets;
import rbtree.*;
import rbtree.ITree.INode;

/**
 * TODO Add declaration. GUI Class ...
 * 
 * @author COMar-PC
 */
public class RBTreePane extends BorderPane {

	private ToolBar toolBar;
	private FlowPane vbLeft;
	private VBox vbCenter;
	private HBox hbLeft, hbStatusBar;

	private Label labelStatus;
	private Button btnAddElement, btnGetElement;
	private Button btnOpenFile, btnSaveToFile, btnStepPrev, btnStepNext, btnReset;
	private ToggleButton btnVisualize;
	private TextField txtFieldKey, txtFieldValue;
	private TextArea txtArea;

	// For graphics
	private Group graphicGroup;
	private int colorIdx = 0;

	private RBTree<String, String> mainTree;

	// TODO Delete it. Test items.
	private Button testBtnChangeColor;
	private VertexGroup testVertexGroup;

	/**
	 * TODO Add declaration of starting method.
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		System.out.println("Method (start).");

		mainTree = new RBTree<>();

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
	 * Creates and returns status bar of application. Locates certain items in
	 * the center pane.
	 * 
	 * @return status bar of application
	 */
	private HBox makeStatusBar() {
		// Label
		labelStatus = new Label("Status bar...");

		HBox statusBar = new HBox();
		statusBar.setPrefHeight(35);
		statusBar.setMinHeight(35);
		statusBar.setPadding(new Insets(5, 20, 5, 5));
		statusBar.getChildren().addAll(labelStatus);

		return statusBar;
	}

	/**
	 * Creates and returns tool bar of application. Locates certain items in the
	 * center pane.
	 * 
	 * @return tool bar of application
	 */
	private ToolBar makeToolBar() {
		// Open file with data for tree painting.
		btnOpenFile = new Button("Open...");
		btnOpenFile.setOnAction((ae) -> {
			labelStatus.setText("Open file clicked.");
			// TODO Add method Open file.
		});
		// Save data of current tree to the file.
		btnSaveToFile = new Button("Save...");
		btnSaveToFile.setOnAction((ae) -> {
			labelStatus.setText("Save to file clicked.");
			// TODO Add method Save to file.
		});
		// Separate buttons Files and Steps
		Separator separatorFilesSteps = new Separator(Orientation.VERTICAL);
		// Start visualization of tree.
		btnVisualize = new ToggleButton("Visualize");
		btnVisualize.setSelected(true);
		// Start visualization of tree.
		btnStepPrev = new Button("<<");
		btnStepPrev.setOnAction((ae) -> {
			labelStatus.setText("Previous step clicked.");
			// TODO Add method Previous step.
		});
		// Start visualization of tree.
		btnStepNext = new Button(">>");
		btnStepNext.setOnAction((ae) -> {
			labelStatus.setText("Next step clicked.");
			// TODO Add method Next step.
		});
		// Separate buttons Files and Steps
		Separator separatorStepsReset = new Separator(Orientation.VERTICAL);
		// Start visualization of tree.
		btnReset = new Button("Reset");
		btnReset.setOnAction((ae) -> {
			labelStatus.setText("Reset clicked.");
			// TODO Add method Next step.
		});
		ToolBar toolBar = new ToolBar(btnOpenFile, btnSaveToFile, separatorFilesSteps, btnVisualize, btnStepPrev,
				btnStepNext, separatorStepsReset, btnReset);
		toolBar.setPadding(new Insets(3, 20, 5, 10));

		return toolBar;
	}

	/**
	 * Creates and returns center pane of application. Locates certain items in
	 * the center pane.
	 * 
	 * @return center pane of application
	 */
	private VBox makeNodeCenter() {
		// Label of the graph
		Label labelGraph = new Label("Graph of RBTree. (Test version of graphics..)");
		// Graphic
		/*
		 * Canvas canvasGraphOfRBTree = new Canvas(400, 400); // Canvas for
		 * RBTree's painting. gc = canvasGraphOfRBTree.getGraphicsContext2D();
		 * // TODO Remove graphics items. gc.strokeLine(0, 0, 200, 200);
		 * gc.strokeOval(100, 100, 200, 200); gc.strokeRect(0, 200, 50, 200);
		 * gc.fillOval(0, 0, 20, 20); gc.fillRect(100, 320, 300, 40);
		 * gc.setFont(new Font(20)); gc.fillText("Text", 60, 50);
		 */
		graphicGroup = new Group();

		/*
		 * TODO Delete it. Check before. Really exciting. Example.
		 *
		 * Random random = new Random(System.currentTimeMillis()); for (int i =
		 * 0; i < 500; i++) { Text text = new Text(random.nextInt((int)
		 * getWidth()), random.nextInt((int) getHeight()), new
		 * Character((char)random.nextInt(255)).toString());
		 * text.setFont(Font.font("Serif", random.nextInt(30)));
		 * text.setFill(Color.rgb(random.nextInt(255), random.nextInt(255),
		 * random.nextInt(255), 0.5)); graphicGroup.getChildren().add(text); }
		 * 
		 * Text text2 = new Text(60, getHeight() / 2, "Graphics is Fun!");
		 * text2.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD,
		 * FontPosture.REGULAR, 60)); text2.setFill(Color.RED);
		 * text2.setFontSmoothingType(FontSmoothingType.LCD); DropShadow shadow
		 * = new DropShadow(); shadow.setOffsetX(2.0f); shadow.setOffsetY(2.0f);
		 * shadow.setColor(Color.BLACK); shadow.setRadius(7);
		 * text2.setEffect(shadow); text2.setStroke(Color.DARKRED);
		 * graphicGroup.getChildren().add(text2);
		 */

		VBox centerPane = new VBox(10);
		centerPane.setPadding(new Insets(5, 20, 10, 5));
		centerPane.getChildren().addAll(labelGraph, graphicGroup);

		return centerPane;
	}

	/**
	 * Creates and returns left pane of application. Locates certain items in
	 * the left pane.
	 * 
	 * @return left pane of application
	 */
	private FlowPane makeNodeLeft() {
		// Layout for label key and textField of key
		Label labelKey = new Label("Key");
		labelKey.setMinWidth(38);
		txtFieldKey = new TextField();
		txtFieldKey.setPrefWidth(50);
		txtFieldKey.setMaxWidth(50);
		HBox hbLeftKey = new HBox();
		hbLeftKey.setAlignment(Pos.BASELINE_CENTER);
		hbLeftKey.getChildren().addAll(labelKey, txtFieldKey);
		// Layout for label value and textField of value
		Label labelValue = new Label("Value");
		labelValue.setMinWidth(38);
		txtFieldValue = new TextField();
		txtFieldValue.setPrefWidth(50);
		txtFieldValue.setMaxWidth(50);
		HBox hbLeftValue = new HBox();
		hbLeftValue.setAlignment(Pos.BASELINE_CENTER);
		hbLeftValue.getChildren().addAll(labelValue, txtFieldValue);
		// Separate TextFields and control Buttons
		Separator separatorFieldsButtons = new Separator(Orientation.HORIZONTAL);
		separatorFieldsButtons.setMinHeight(15);
		// Buttons
		btnAddElement = new Button("Add element...");
		btnAddElement.setMaxWidth(Double.MAX_VALUE);
		btnGetElement = new Button("Get element...");
		btnGetElement.setMaxWidth(Double.MAX_VALUE);
		// TODO Delete it. Testing button_changeColor
		testBtnChangeColor = new Button("Test");
		testBtnChangeColor.setMaxWidth(Double.MAX_VALUE);
		testBtnChangeColor.setOnAction((ae) -> {
			if (colorIdx == 0) {
				// graphicGroup.getChildren().clear();
				double radius = getWidth() < getHeight() ? (getWidth() / countItemsAtRow)
						: (getHeight() / countItemsAtRow);
				testVertexGroup = new VertexGroup(mainTree.getRoot(), countHeight, getWidth(), radius);
				graphicGroup.getChildren().add(testVertexGroup);
				colorIdx++;
			} else {
				if (!txtFieldKey.getText().isEmpty())
					testVertexGroup.setCenterX(Double.valueOf(txtFieldKey.getText()));
			}
		});
		// Text area
		txtArea = new TextArea();
		txtArea.setMaxWidth(100);
		txtArea.setEditable(false);
		txtArea.autosize();

		// Actions
		btnAddElement.setOnAction((ae) -> {
			labelStatus.setText("Button AddElement was pressed.");
			if ((txtFieldKey.getText().isEmpty()) || (txtFieldValue.getText().isEmpty())) {
				labelStatus.setText("Error when try AddElement. Please check Key or Value!");
			} else {
				String key = txtFieldKey.getText();
				mainTree.put(key, txtFieldValue.getText());
				if (btnVisualize.isSelected())
					paintRBTree();
			}
		});
		btnGetElement.setOnAction((ae) -> {
			labelStatus.setText("Button GetElement was pressed.");
			if (txtFieldKey.getText().isEmpty()) {
				labelStatus.setText("Error when try GetElement. Please check Key!");
			} else {
				String value = mainTree.get(txtFieldKey.getText());
				if (value == null) {
					labelStatus.setText("Key is not found. Please try again.");
				} else {
					labelStatus.setText(String.valueOf(value));
				}
			}
		});

		FlowPane leftPane = new FlowPane(Orientation.VERTICAL);
		leftPane.setPadding(new Insets(20, 20, 20, 20));
		leftPane.setVgap(8);
		leftPane.setAlignment(Pos.BASELINE_CENTER);
		leftPane.getChildren().addAll(hbLeftKey, hbLeftValue, separatorFieldsButtons, btnAddElement, btnGetElement,
				testBtnChangeColor, txtArea);
		leftPane.setMinHeight(409);
		return leftPane;
	}

	// Graphic methods
	/**
	 * Create vertex in the graph.
	 * 
	 * @param key
	 *            key of vertex which was added to the tree
	 */
	private void paintRBTree() {
		graphicGroup.getChildren().clear();

		if (mainTree.getRoot() != null) {
			double radius = getWidth() < getHeight() ? (getWidth() / countItemsAtRow) : (getHeight() / countItemsAtRow);
			System.out.println("Radius = " + radius + "\nWidth = " + getWidth() + "\nHeigth = " + getHeight());
			countHeight++;
			preOrder(mainTree.getRoot(), (getWidth() / 2), getWidth(), radius);
			countHeight--;
		}
	}

	private int countHeight = 1;
	private int countItemsAtRow = 10 * 4;

	private void preOrder(INode<String, String> iNode, double center, double width, double radius) {
		System.out.println(center);
		graphicGroup.getChildren().add(new VertexGroup(iNode, countHeight, center, radius));
		if (iNode.leftChild() != null) {
			countHeight++;
			preOrder(iNode.leftChild(), (center - width / Math.pow(2, countHeight)), width, radius);
			countHeight--;
		}
		if (iNode.rightChild() != null) {
			countHeight++;
			preOrder(iNode.rightChild(), (center + width / Math.pow(2, countHeight)), width, radius);
			countHeight--;
		}
	}
}
