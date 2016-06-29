package rbtee_gui;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rbtree.*;

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
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Method (main start).");

		primaryStage.setTitle("Red-Black tree visual editor");
		
		RBTreePane primaryPane = new RBTreePane();
		Scene primaryScene = new Scene(primaryPane);
		primaryStage.setScene(primaryScene);
		
		primaryPane.start();
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