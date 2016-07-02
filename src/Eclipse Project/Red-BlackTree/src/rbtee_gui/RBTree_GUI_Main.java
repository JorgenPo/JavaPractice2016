package rbtee_gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rbtree.RBTree;
import rbtree.Utills;

/**
 * GUI of application.
 * 
 * @author comaralex
 */
public class RBTree_GUI_Main extends Application {

	/**
	 * Method main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Delete it. Test of RBTree (temp)
		RBTree<String, String> tree = new RBTree<>();
		System.out.println("Initial size of tree: " + tree.getSize());
		tree.put("Key", "Value");
		tree.put("Foo", "Bar");
		tree.put("Hello", "Tree");
		System.out.println("Size of tree after insertation: " + tree.getSize());

		// Test of saving tree
		try {
			Utills.saveTreeToFile(tree, "tree.graph");
			RBTree<String, String> loaded = Utills.loadTreeFromFile("tree.graph");
			System.out.println("Size of loaded tree: " + loaded.getSize());
		} catch (Exception e) {
			System.err.println("Error: can't save the graph!");
		}
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Method (main start).");

		primaryStage.setTitle("Red-Black tree visual editor");

		RBTreePane primaryPane = new RBTreePane();
		primaryPane.start(primaryStage);
		Scene primaryScene = new Scene(primaryPane);
		primaryStage.setScene(primaryScene);

		
		primaryStage.show();
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