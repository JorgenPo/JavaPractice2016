package rbtee_gui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.File;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import rbtree.*;
import rbtree.ITree.INode;

/**
 * RBTreePane class realize GUI. User can insight as arranged red-black tree
 * using buttons on the scene.
 * 
 * @author COMar-PC
 */
public class RBTreePane extends BorderPane {

	private ToolBar toolBar;
	private VBox vbCenter;
	private HBox hbLeft, hbStatusBar;
	private FlowPane vbLeft;

	private Label labelStatus;
	private Separator separatorFieldsButtons;
	private Button btnAddElement, btnGetElement, btnStepPrev, btnStepNext;
	private Button btnOpenFile, btnSaveToFile;
	private ToggleButton btnVisualize;
	private TextField txtFieldKey, txtFieldValue;
	private TextArea txtArea;

	// For graphics
	private Group graphicGroup;
	private RBTree<String, String> mainTree;
	private HashMap<String, Integer> keyIndex;
	private TreeExecution<INode<String, String>, String, String> treeGetByStep;
	private TreeExecution<String, String, String> treeAddByStep;

	/**
	 * The main entry point of the application.
	 * 
	 * @param primaryStage
	 *            the primary stage for application
	 * @throws Exception
	 */
	public void start(final Stage primaryStage) throws Exception {
		System.out.println("Method (start).");
		primaryStage.getIcons().add(new Image(APP_ICON));

		mainTree = new RBTree<>();
		mainTree.setRBTreePane(this);
		graphicGroup = new Group();
		keyIndex = new HashMap<>();

		toolBar = makeToolBar(primaryStage);
		hbStatusBar = makeStatusBar();
		vbCenter = makeNodeCenter();
		// Separate LeftPane and CenterPane
		Separator separatorLeftCenter = new Separator(Orientation.VERTICAL);
		vbLeft = makeNodeLeft();
		hbLeft = new HBox();
		hbLeft.getChildren().addAll(vbLeft, separatorLeftCenter);
		setLayouts(primaryStage);
	}

	/**
	 * Opens file save dialog in your system and save the tree in selected file
	 * 
	 * @param tree
	 *            Tree to save
	 * @param mainStage
	 *            Main stage (window)
	 */
	private <K, V> void saveTree(RBTree<K, V> tree, final Stage mainStage) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Сохранение дерева");

		fc.getExtensionFilters().addAll(new ExtensionFilter("Graph files", "*.graph"),
				new ExtensionFilter("Text files", "*.txt"));
		fc.setInitialDirectory(new File(System.getProperty("user.dir")));

		File file = fc.showSaveDialog(mainStage);
		if (file != null) {
			Utills.saveTreeToFile(tree, file.getAbsolutePath());
			labelStatus.setText("Tree saved to " + file);
		} else {
			labelStatus.setText("Tree has not been saved! (canceled)");
		}
	}

	private void loadTree(final Stage mainStage) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Загрузка дерева");

		fc.getExtensionFilters().addAll(new ExtensionFilter("Graph files", "*.graph"),
				new ExtensionFilter("Text files", "*.txt"));
		fc.setInitialDirectory(new File(System.getProperty("user.dir")));

		File file = fc.showOpenDialog(mainStage);
		if (file != null) {
			this.mainTree = Utills.loadTreeFromFile(file.getAbsolutePath());
			labelStatus.setText("Tree loaded from " + file);
			paintRBTree();
		} else {
			labelStatus.setText("Tree has not been loaded!");
		}
	}

	/**
	 * Locates panes in the right place.
	 */
	private void setLayouts(final Stage primaryStage) {
		setTop(new VBox(createMenuBar(primaryStage, graphicGroup), toolBar));
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
		labelStatus = new Label("Status bar...");

		HBox statusBar = new HBox();
		statusBar.setPrefHeight(30);
		statusBar.setMinHeight(30);
		statusBar.setPadding(new Insets(5, 5, 5, 5));
		statusBar.getChildren().addAll(labelStatus);

		return statusBar;
	}

	/**
	 * Creates and returns menu bar of application.
	 * 
	 * @return menu bar of application
	 */
	private MenuBar createMenuBar(final Stage stage, final Group group) {
		Menu fileMenu = new Menu("_File");
		MenuItem exitMenuItem = new MenuItem("_Exit");
		exitMenuItem.setGraphic(new ImageView(new Image(CLOSE_ICON)));
		exitMenuItem.setOnAction((ae) -> stage.close());
		fileMenu.getItems().setAll(exitMenuItem);
		Menu zoomMenu = new Menu("_Zoom");
		MenuItem zoomResetMenuItem = new MenuItem("Zoom _Reset");
		zoomResetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
		zoomResetMenuItem.setGraphic(new ImageView(new Image(ZOOM_RESET_ICON)));
		zoomResetMenuItem.setOnAction((ae) -> {
			group.setScaleX(1);
			group.setScaleY(1);
		});
		MenuItem zoomInMenuItem = new MenuItem("Zoom _In");
		zoomInMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I));
		zoomInMenuItem.setGraphic(new ImageView(new Image(ZOOM_IN_ICON)));
		zoomInMenuItem.setOnAction((ae) -> {
			group.setScaleX(group.getScaleX() * 1.5);
			group.setScaleY(group.getScaleY() * 1.5);
		});
		MenuItem zoomOutMenuItem = new MenuItem("Zoom _Out");
		zoomOutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O));
		zoomOutMenuItem.setGraphic(new ImageView(new Image(ZOOM_OUT_ICON)));
		zoomOutMenuItem.setOnAction((ae) -> {
			group.setScaleX(group.getScaleX() * 1 / 1.5);
			group.setScaleY(group.getScaleY() * 1 / 1.5);
		});
		zoomMenu.getItems().setAll(zoomResetMenuItem, zoomInMenuItem, zoomOutMenuItem);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().setAll(fileMenu, zoomMenu);
		return menuBar;
	}

	/**
	 * Creates and returns tool bar of application. Locates certain items in the
	 * center pane.
	 * 
	 * @return tool bar of application
	 */
	private ToolBar makeToolBar(final Stage mainStage) {
		// Open file with data for tree painting.
		btnOpenFile = new Button("Open...");
		btnOpenFile.setOnAction((ae) -> {
			resetTree();
			labelStatus.setText("Open file clicked.");
			loadTree(mainStage);
		});
		// Save data of current tree to the file.
		btnSaveToFile = new Button("Save...");
		btnSaveToFile.setOnAction((ae) -> {
			resetTree();
			labelStatus.setText("Save to file clicked.");
			if (this.mainTree.getSize() != 0) {
				saveTree(this.mainTree, mainStage);
			}
		});
		// Separate File buttons and other items.
		Separator separatorFile = new Separator(Orientation.VERTICAL);
		// Visualization of tree.
		// If selected true - on visual, false - off visual
		btnVisualize = new ToggleButton("Visualize");
		btnVisualize.setSelected(true);
		// Separate Visual buttons and other items.
		Separator separatorVisual = new Separator(Orientation.VERTICAL);

		ToolBar toolBar = new ToolBar(btnOpenFile, btnSaveToFile, separatorFile, btnVisualize, separatorVisual);
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
		Label labelGraph = new Label("Graph of RBTree.");
		labelGraph.setFont(new Font(16));
		// Graphic
		Parent zoomGraphicPane = createZoomPane(graphicGroup);

		VBox centerPane = new VBox(10);
		centerPane.setPrefWidth(500);
		centerPane.setPadding(new Insets(5, 20, 10, 5));
		VBox.setVgrow(zoomGraphicPane, Priority.ALWAYS);
		centerPane.getChildren().setAll(labelGraph, zoomGraphicPane);

		return centerPane;
	}

	/**
	 * Add action zoom for group of elements.
	 * 
	 * @param group
	 *            group is zoomed by that action
	 * @return zoomed pane with group of elements
	 */
	private Parent createZoomPane(Group group) {
		final double SCALE_DELTA = 1.1;
		final StackPane zoomPane = new StackPane();

		zoomPane.getChildren().add(group);
		zoomPane.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				event.consume();

				if (event.getDeltaY() == 0)
					return;

				double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

				group.setScaleX(group.getScaleX() * scaleFactor);
				group.setScaleY(group.getScaleY() * scaleFactor);
			}
		});

		zoomPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
				zoomPane.setClip(
						new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
			}
		});

		return zoomPane;
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
		HBox hbLeftKey = new HBox();
		hbLeftKey.setAlignment(Pos.BASELINE_CENTER);
		hbLeftKey.getChildren().addAll(labelKey, txtFieldKey);
		// Layout for label value and textField of value
		Label labelValue = new Label("Value");
		labelValue.setMinWidth(38);
		txtFieldValue = new TextField();
		txtFieldValue.setPrefWidth(50);
		HBox hbLeftValue = new HBox();
		hbLeftValue.setAlignment(Pos.BASELINE_CENTER);
		hbLeftValue.getChildren().addAll(labelValue, txtFieldValue);
		// Separate TextFields and control Buttons
		separatorFieldsButtons = new Separator(Orientation.HORIZONTAL);
		separatorFieldsButtons.setMinHeight(15);

		// Buttons
		btnAddElement = new Button("Add element...");
		btnAddElement.setMaxWidth(Double.MAX_VALUE);
		btnGetElement = new Button("Get element...");
		btnGetElement.setMaxWidth(Double.MAX_VALUE);
		btnStepPrev = new Button("<<");
		btnAddElement.setMaxWidth(Double.MAX_VALUE);
		btnStepNext = new Button(">>");
		btnGetElement.setMaxWidth(Double.MAX_VALUE);

		// Text area
		txtArea = new TextArea();
		txtArea.setMaxWidth(100);
		txtArea.setEditable(false);
		txtArea.autosize();

		// Actions
		btnAddElement.setOnAction((ae) -> {
			resetTree();
			txtArea.clear();
			if (txtFieldKey.getText().isEmpty()) {
				labelStatus.setText("Error when try to put new element. Please check key and value fields!");
			} else {
				labelStatus.setText("Status bar...");
				String key = txtFieldKey.getText();
				String value = txtFieldValue.getText();
				txtArea.appendText("Put element:\nKey = " + key + "\nValue = " + value);
				if (!btnVisualize.isSelected()) {
					String oldValue = mainTree.put(key, value);
					if (oldValue == null) {
						txtArea.appendText("\nOld element not found, creating new element...");
					} else {
						txtArea.appendText("\nOld element's value = " + oldValue);
					}
					paintRBTree();
				} else {
					txtArea.appendText("\nVisual mode:\n");
					treeAddByStep = mainTree.putVisual(key, value);
					treeGetByStep = null;
					chooseButtonSet("StepButtons");
					setDisableButtons(toolBar);
					if (mainTree.getSize() > 0) {
						if (!treeAddByStep.works()) {
							paintRBTree();
						}
						VertexGroup vertex = (VertexGroup) graphicGroup.getChildren()
								.get(keyIndex.get(treeAddByStep.getNode().getKey()));
						vertex.setSelected();
					}
					txtArea.appendText("Root = " + treeAddByStep.getNode().getKey() + "\nAlgorithm (put):\n");
				}
			}
			/*
			 * if ((txtFieldKey.getText().isEmpty()) ||
			 * (txtFieldValue.getText().isEmpty())) { labelStatus.
			 * setText("Error when try AddElement. Please check Key or Value!");
			 * } else { labelStatus.setText("Status bar..."); String key =
			 * txtFieldKey.getText(); mainTree.put(key,
			 * txtFieldValue.getText()); if (btnVisualize.isSelected())
			 * paintRBTree(); }
			 */
		});
		btnGetElement.setOnAction((ae) -> {
			resetTree();
			txtArea.clear();
			if (txtFieldKey.getText().isEmpty()) {
				labelStatus.setText("Error when try GetElement. Please check Key!");
			} else {
				labelStatus.setText("Status bar...");
				String strKey = txtFieldKey.getText();
				txtArea.appendText("Get element:\nKey = " + strKey);
				String strValue = mainTree.get(strKey);
				if (strValue == null) {
					txtArea.appendText("\nKey is not found.\nPlease try again.");
					return;
				} else {
					txtArea.appendText("\nValue = " + strValue);
				}
				if (btnVisualize.isSelected()) {
					txtArea.appendText("\nVisual mode:\n");
					treeGetByStep = mainTree.getVisual(strKey);
					treeAddByStep = null;
					chooseButtonSet("StepButtons");
					setDisableButtons(toolBar);
					VertexGroup vertex = (VertexGroup) graphicGroup.getChildren()
							.get(keyIndex.get(treeGetByStep.getNode().getKey()));
					vertex.setSelected();
					txtArea.appendText("Root = " + treeGetByStep.getNode().getKey() + "\nAlgorithm (get):\n");
				}
			}
		});
		btnStepNext.setOnAction((ae) -> {
			if (treeGetByStep != null) {
				VertexGroup vertex = (VertexGroup) graphicGroup.getChildren()
						.get(keyIndex.get(treeGetByStep.getNode().getKey()));
				vertex.reset();
				if (treeGetByStep.works()) {
					treeGetByStep.step();
					vertex = (VertexGroup) graphicGroup.getChildren()
							.get(keyIndex.get(treeGetByStep.getNode().getKey()));
					vertex.setSelected();
					txtArea.appendText(treeGetByStep.getNode().getKey() + "\n");
				} else {
					txtArea.appendText("End.\n");
					chooseButtonSet("ControlButtons");
					setDisableButtons(toolBar);
				}
			} else {
				VertexGroup vertex;
				if (mainTree.getSize() > 0) {
					vertex = (VertexGroup) graphicGroup.getChildren()
							.get(keyIndex.get(treeAddByStep.getNode().getKey()));
					vertex.reset();
				}
				int oldSize = mainTree.getSize();

				if (treeAddByStep.works()) {
					treeAddByStep.step();
					if (oldSize < mainTree.getSize()) {
						paintRBTree();
					}
					vertex = (VertexGroup) graphicGroup.getChildren()
							.get(keyIndex.get(treeAddByStep.getNode().getKey()));
					vertex.setSelected();
					txtArea.appendText(treeAddByStep.getNode().getKey() + "\n");
				} else {
					txtArea.appendText("End.\n");
					chooseButtonSet("ControlButtons");
					setDisableButtons(toolBar);
				}
			}
		});

		FlowPane leftPane = new FlowPane(Orientation.VERTICAL);
		leftPane.setPadding(new Insets(20, 20, 20, 20));
		leftPane.setHgap(8);
		leftPane.setVgap(8);
		leftPane.setAlignment(Pos.BASELINE_CENTER);
		leftPane.getChildren().addAll(hbLeftKey, hbLeftValue, separatorFieldsButtons, btnAddElement, btnGetElement,
				txtArea);
		return leftPane;
	}

	/**
	 * Set specified buttons according to parameter <tt>strButtonSet<tt>
	 * 
	 * @param strButtonSet
	 *            name of button set
	 */
	public void chooseButtonSet(String strButtonSet) {
		vbLeft.getChildren().removeAll(btnAddElement, btnGetElement, btnStepPrev, btnStepNext);
		int idxOfSeparator = vbLeft.getChildren().indexOf(separatorFieldsButtons);
		switch (strButtonSet) {
		case "StepButtons":
			vbLeft.getChildren().add(++idxOfSeparator, btnStepPrev);
			vbLeft.getChildren().add(++idxOfSeparator, btnStepNext);
			break;

		case "ControlButtons":
			vbLeft.getChildren().add(++idxOfSeparator, btnAddElement);
			vbLeft.getChildren().add(++idxOfSeparator, btnGetElement);
			break;

		default:
			break;
		}
	};

	/**
	 * Disable and enable buttons from parent and HBox/VBox children of this
	 * parent.
	 * 
	 * @param parent
	 *            parent with Button/HBox/VBox children
	 */
	public void setDisableButtons(Parent parent) {
		for (int i = 0; i < parent.getChildrenUnmodifiable().size(); ++i) {
			Node nodeClass = parent.getChildrenUnmodifiable().get(i);
			switch (nodeClass.getClass().getName()) {
			case "javafx.scene.layout.HBox":
				HBox hbClass = (HBox) nodeClass;
				for (int j = 0; j < hbClass.getChildren().size(); ++j) {
					Node nodeClassAtHBox = hbClass.getChildren().get(j);
					switch (nodeClassAtHBox.getClass().getName()) {
					case "javafx.scene.control.Button":
						Button btnAtHBox = (Button) nodeClassAtHBox;
						if (btnAtHBox.isDisabled())
							btnAtHBox.setDisable(false);
						else
							btnAtHBox.setDisable(true);
						break;

					default:
						break;
					}
				}
				break;

			case "javafx.scene.layout.VBox":
				VBox vbClass = (VBox) nodeClass;
				for (int j = 0; j < vbClass.getChildren().size(); ++j) {
					Node nodeClassAtVBox = vbClass.getChildren().get(j);
					switch (nodeClassAtVBox.getClass().getName()) {
					case "javafx.scene.control.Button":
						Button btnAtVBox = (Button) nodeClassAtVBox;
						if (btnAtVBox.isDisabled())
							btnAtVBox.setDisable(false);
						else
							btnAtVBox.setDisable(true);
						break;

					default:
						break;
					}
				}
				break;

			case "javafx.scene.control.Button":
				Button btnAtNodeClass = (Button) nodeClass;
				if (btnAtNodeClass.isDisabled())
					btnAtNodeClass.setDisable(false);
				else
					btnAtNodeClass.setDisable(true);
				break;

			default:
				break;
			}
		}
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

	private void preOrder(INode<String, String> iNode, final double center, double width, double radius) {
		System.out.println(center);
		VertexGroup vert = new VertexGroup(iNode, countHeight, center, radius);
		graphicGroup.getChildren().add(vert);
		keyIndex.put(iNode.getKey(), graphicGroup.getChildren().indexOf(vert));
		if (iNode.leftChild() != null) {
			countHeight++;
			preOrder(iNode.leftChild(), (center - width / Math.pow(2, countHeight)), width, radius);
			countHeight--;
		} else {
			countHeight++;
			VertexGroup vertNull = new VertexGroup(null, countHeight, (center - width / Math.pow(2, countHeight)),
					radius);
			countHeight--;
			graphicGroup.getChildren().add(vertNull);
		}
		if (iNode.rightChild() != null) {
			countHeight++;
			preOrder(iNode.rightChild(), (center + width / Math.pow(2, countHeight)), width, radius);
			countHeight--;
		} else {
			countHeight++;
			VertexGroup vertNull = new VertexGroup(null, countHeight, (center + width / Math.pow(2, countHeight)),
					radius);
			graphicGroup.getChildren().add(vertNull);
			countHeight--;
		}
	}

	/**
	 * Vertex set selected by specified key.
	 * 
	 * @param key
	 *            key to select vertex
	 */
	public void setSelectedByKey(String key) {
		VertexGroup vert = (VertexGroup) graphicGroup.getChildren().get(keyIndex.get(key));
		vert.setSelected();
	}

	/**
	 * Reset to default colors of all vertex groups in the graphic group.
	 */
	private void resetTree() {
		if (graphicGroup.getChildren().isEmpty())
			return;
		for (int i = 0; i < graphicGroup.getChildren().size(); i++) {
			VertexGroup vert = (VertexGroup) graphicGroup.getChildren().get(i);
			vert.reset();
		}
	}

	private static final String APP_ICON = "/images/App-tree-black-2-icon.png";
	private static final String ZOOM_RESET_ICON = "/images/Zoom-icon.png";
	private static final String ZOOM_OUT_ICON = "/images/Zoom-Out-icon.png";
	private static final String ZOOM_IN_ICON = "/images/Zoom-In-icon.png";
	private static final String CLOSE_ICON = "/images/Actions-application-exit-icon.png";
}
