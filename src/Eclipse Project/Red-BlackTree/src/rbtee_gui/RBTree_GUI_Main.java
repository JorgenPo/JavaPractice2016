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
	 * Starting method main.
	 * 
	 * @param args
	 *            command arguments
	 */
	public static void main(String[] args) {
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
}