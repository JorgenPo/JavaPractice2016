package rbtree;

import java.io.*;

/**
 * Helper methods for saving / loading tree and more
 * 
 * @author Георгий
 *
 */
public class Utills {
	/**
	 * Saves tree to the fileName file 
	 * 
	 * @param fileName
	 *		Complete file name to save tree
	 * @param tree
	 * 		Tree to save
	 */
	public static final <K,V> void saveTreeToFile(RBTree<K,V> tree, String fileName) {
		try (BufferedWriter file = new BufferedWriter(new FileWriter(fileName))) {
			String str = "NUM_VERTEX=";
			str += tree.getSize();
			file.write(str + '\n');
			
			str = "KEY_TYPE=";
			str += tree.getRoot().getKey().getClass().getName();
			file.write(str + '\n');

			str = "VALUE_TYPE=";
			str += tree.getRoot().getValue().getClass().getName();
			file.write(str + '\n');
			
			file.write('\n');
			
			tree.perform((node) -> {
				try {
					file.write(node.getKey() + "=" + node.getValue() + '\n');
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
		} catch (FileNotFoundException ex) {
			System.err.println("File not found! Check file name!");
		} catch (IOException ex) {
			System.err.println("Can't close the file!");
		}
	}
	
	/**
	 * Loads file from the file. Makes tree of <String,String>
	 * 
	 * @param fileName
	 * 		Complete file name to load tree
	 * @return
	 * 		Red-black tree <String, String>
	 */
	public static final RBTree<String,String> loadTreeFromFile(String fileName) {
		RBTree<String,String> tree = new RBTree<>();
		try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
			String str = file.readLine();
			String[] arr = str.split("=");
			int treeSize = Integer.parseInt(arr[1]);
			
			str = file.readLine();
			arr = str.split("=");
			String keyType = arr[1];
			Class<?> keyClass = Class.forName(keyType);
			
			str = file.readLine();
			arr = str.split("=");
			String valType = arr[1];
			Class<?> valClass = Class.forName(valType);
		
			//String v = valClass.getSimpleName();
			//String k = keyClass.getSimpleName();
			
			file.readLine(); // Blank string
			
			for (int i = 0; i < treeSize; ++i) {
				str = file.readLine();
				arr = str.split("=");
				tree.put(arr[0], arr[1]);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found! Check file name!");
		} catch (IOException e) {
			System.err.println("Can't close the file!");
		} catch (ClassNotFoundException e) {
			System.err.println("Wrong class name of the type or value!");
		}
		
		return tree;
	}
}
