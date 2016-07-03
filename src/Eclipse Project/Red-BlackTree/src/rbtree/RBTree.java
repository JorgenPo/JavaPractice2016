package rbtree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import rbtee_gui.RBTreePane;

public class RBTree<K, V> implements ITree<K, V> {

	private Node<K, V> root = null;
	private int size = 0;
	private final Comparator<? super K> comparator;
	private RBTreePane treePane;

	public RBTree() {
		comparator = null;
	}

	public void setRBTreePane(RBTreePane treePane) {
		this.treePane = treePane;
	}

	public RBTree(Comparator<? super K> comparator) {
		this.comparator = comparator;
	}

	static class Node<K, V> implements ITree.INode<K, V> {
		private K key;
		private V value;

		private Node<K, V> parent;
		private Node<K, V> leftChild;
		private Node<K, V> rightChild;

		private boolean isRed = false;

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V setValue(V newVal) {
			V old = value;
			value = newVal;
			return old;
		}

		@Override
		public INode<K, V> parent() {
			return parent;
		}

		@Override
		public INode<K, V> leftChild() {
			return leftChild;
		}

		@Override
		public INode<K, V> rightChild() {
			return rightChild;
		}

		@Override
		public boolean isRed() {
			return isRed;
		}

		public boolean equals(Object o) {
			if (!(o instanceof ITree.INode)) {
				return false;
			}
			ITree.INode<?, ?> e = (ITree.INode<?, ?>) o;

			return valEquals(key, e.getKey()) && valEquals(value, e.getValue());
		}

		public int hashCode() {
			int keyHash = (key == null ? 0 : key.hashCode());
			int valHash = (value == null ? 0 : value.hashCode());
			return keyHash ^ valHash;
		}

		public String toString() {
			return key + " = " + value;
		}
	}

	@Override
	public INode<K, V> getRoot() {
		return root;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		Node<K, V> r = root;

		if (r == null) { // First case - insert to empty tree
			compare(key, key);
			root = new Node<>(key, value, null);
			size = 1;
			return null;
		}

		int cmp; // Compare result
		Node<K, V> parent;

		Comparator<? super K> comp = comparator;
		if (comp != null) {
			do {
				parent = r;
				cmp = comp.compare(key, r.key);
				if (cmp < 0) {
					r = r.leftChild;
				} else if (cmp > 0) {
					r = r.rightChild;
				} else {
					return r.setValue(value);
				}
			} while (r != null);
		} else {
			if (key == null) {
				throw new NullPointerException();
			}
			Comparable<? super K> k = (Comparable<? super K>) key;

			do {
				parent = r;
				cmp = k.compareTo(r.key);
				if (cmp < 0) {
					r = r.leftChild;
				} else if (cmp > 0) {
					r = r.rightChild;
				} else {
					return r.setValue(value);
				}
			} while (r != null);
		}

		// Now r - leaf node. Now we can create new node and insert it
		Node<K, V> n = new Node<>(key, value, parent);
		if (cmp < 0) {
			parent.leftChild = n;
		} else {
			parent.rightChild = n;
		}

		balanceAfterInsertion(n);
		++size;
		return null;
	}

	@Override
	public void remove(INode<K, V> node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(K key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(K key) {
		return getNode(key) != null;
	}

	@Override
	public boolean containsValue(V val) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	final Node<K, V> getNodeWithComparator(Object k) {
		@SuppressWarnings("unchecked")
		K key = (K) k;

		Comparator<? super K> comp = comparator;
		if (comp != null) {
			Node<K, V> node = root;
			while (node != null) {
				int cmp = comp.compare(node.key, key);
				if (cmp < 0) {
					node = node.leftChild;
				} else if (cmp > 0) {
					node = node.rightChild;
				} else {
					return node;
				}
			}
		}

		return null;
	}

	/**
	 * Find and return entry with key k.
	 * 
	 * @param k
	 *            Key to search.
	 * @return Node with key k or null if there are no node with such key.
	 */
	final TreeExecution<INode<K, V>, K, V> getNode(Object k) {
		if (comparator != null) {
			getNodeWithComparator(k);
		}

		if (k == null) {
			throw new NullPointerException();
		}

		@SuppressWarnings("unchecked")
		Comparable<? super K> key = (Comparable<? super K>) k;

		return new TreeExecution<INode<K, V>, K, V>() {
			private Node<K, V> node;
			{
				node = root;
			}

			@Override
			public void step() {
				if (!works())
					return;

				int cmp = key.compareTo(node.key);

				if (cmp < 0) {
					node = node.leftChild;
				} else {
					node = node.rightChild;
				}
			}

			@Override
			public boolean works() {
				return node != null && key.compareTo(node.key) != 0;
			}

			@Override
			public Node<K, V> getResult() {
				while (works()) {
					step();
				}

				return node;
			}

			@Override
			public rbtree.ITree.INode<K, V> getNode() {
				return node;
			}
		};
	}

	@Override
	public V get(K key) {
		INode<K, V> e = getNode(key).getResult();
		return e == null ? null : e.getValue();
	}

	public TreeExecution<INode<K, V>, K, V> getVisual(K key) {
		TreeExecution<INode<K, V>, K, V> e = getNode(key);
		return e;
	}

	@Override
	public V set(K key, V newVal) {
		INode<K, V> e = getNode(key).getResult();
		if (e == null) {
			return null;
		}

		V old = e.getValue();
		e.setValue(newVal);
		return old;
	}

	// Util methods ============================================================

	/**
	 * Compares two keys.
	 */
	@SuppressWarnings({ "unchecked" })
	final int compare(Object key1, Object key2) {
		if (comparator == null) {
			return ((Comparable<? super K>) key1).compareTo((K) key2);
		} else {
			return comparator.compare((K) key1, (K) key2);
		}
	}

	static final boolean valEquals(Object val1, Object val2) {
		return (val1 == null ? val2 == null : val1.equals(val2));
	}

	// Balancing methods

	private static <K, V> boolean isRed(Node<K, V> p) {
		return p == null ? false : p.isRed;
	}

	private static <K, V> Node<K, V> parentOf(Node<K, V> p) {
		return p == null ? null : p.parent;
	}

	private static <K, V> void setRed(Node<K, V> p, boolean b) {
		if (p != null) {
			p.isRed = b;
		}
	}

	private static <K, V> Node<K, V> leftOf(Node<K, V> p) {
		return p == null ? null : p.leftChild;
	}

	private static <K, V> Node<K, V> rightOf(Node<K, V> p) {
		return p == null ? null : p.rightChild;
	}

	private void rotateLeft(Node<K, V> x) {
		if (x != null) {
			Node<K, V> q = x.rightChild;

			x.rightChild = q.leftChild;
			if (q.leftChild != null) {
				q.leftChild.parent = x;
			}

			q.parent = x.parent;
			if (x.parent == null) { // If x - root element
				root = q;
			} else if (x.parent.leftChild == x) { // If x - left child
				x.parent.leftChild = q;
			} else {
				x.parent.rightChild = q;
			}

			q.leftChild = x;
			x.parent = q;
		}
	}

	private void rotateRight(Node<K, V> x) {
		if (x != null) {
			Node<K, V> q = x.leftChild;

			x.leftChild = q.rightChild;
			if (q.rightChild != null) {
				q.rightChild.parent = x;
			}

			q.parent = x.parent;
			if (x.parent == null) { // If x - root element
				root = q;
			} else if (x.parent.leftChild == x) { // If x - left child
				x.parent.leftChild = q;
			} else {
				x.parent.rightChild = q;
			}

			q.rightChild = x;
			x.parent = q;
		}
	}

	/**
	 * Balances tree after insertion.
	 * 
	 * @param x
	 *            root of the tree to balance.
	 */
	private void balanceAfterInsertion(Node<K, V> x) {
		x.isRed = true;

		while (x != null && x != root && x.parent.isRed) {
			if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
				Node<K, V> u = rightOf(parentOf(parentOf(x))); // Uncle
				if (isRed(u)) {
					setRed(parentOf(x), false);
					setRed(u, false);
					setRed(parentOf(parentOf(x)), true);
					x = parentOf(parentOf(x)); // If grandpa is red, we should
												// do this proc for him
				} else {
					if (x == rightOf(parentOf(x))) { // If x - right child of
														// Parent
						x = parentOf(x);
						rotateLeft(x);
					}
					setRed(parentOf(x), false);
					setRed(parentOf(parentOf(x)), true);
					rotateRight(parentOf(parentOf(x)));
				}
			} else { // Same thing, just symmetric
				Node<K, V> u = leftOf(parentOf(parentOf(x))); // Uncle
				if (isRed(u)) {
					setRed(parentOf(x), false);
					setRed(u, false);
					setRed(parentOf(parentOf(x)), true);
					x = parentOf(parentOf(x)); // If grandpa is red, we should
												// do this proc for him
				} else {
					if (x == leftOf(parentOf(x))) { // If x - right child of
													// Parent
						x = parentOf(x);
						rotateRight(x);
					}
					setRed(parentOf(x), false);
					setRed(parentOf(parentOf(x)), true);
					rotateLeft(parentOf(parentOf(x)));
				}
			}
		}
		root.isRed = false;
	}

	/**
	 * Perform action to every node in tree. Based on BFS.
	 * 
	 * @param action
	 *            Action (function) to perform
	 */
	public void perform(Consumer<Node<K, V>> action) {
		Queue<Node<K, V>> q = new LinkedList<Node<K, V>>();
		q.add(root);

		Node<K, V> n = null;
		while (!q.isEmpty()) {
			n = q.remove();
			action.accept(n);

			if (n.leftChild != null) {
				q.add(n.leftChild);
			}

			if (n.rightChild != null) {
				q.add(n.rightChild);
			}
		}
		root.isRed = false;
	}
}
