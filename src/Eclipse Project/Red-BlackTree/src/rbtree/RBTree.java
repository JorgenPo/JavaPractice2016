package rbtree;

import java.util.Comparator;

public class RBTree<K,V>
implements ITree<K, V> {
	
	private Node<K,V> mRoot = null;
	private int mSize = 0;
	private final Comparator<? super K> mComparator;
	
	public RBTree() {
		mComparator = null;
	}
	
	public RBTree(Comparator<? super K> comparator) {
		mComparator = comparator;
	}
	
	static class Node<K,V> implements ITree.INode<K, V> {
		K mKey;
		V mValue;
		
		Node<K,V> mParent;
		Node<K,V> mLeftChild;
		Node<K,V> mRightChild;
		
		boolean mIsRed = false;
		
		public Node(K key, V value, Node<K,V> parent) {
			mKey = key;
			mValue = value;
			mParent = parent;
		}
		
		@Override
		public V getValue() {
			return mValue;
		}
		
		@Override
		public K getKey() {
			return mKey;
		}

		@Override
		public V setValue(V newVal) {
			V old = mValue;
			mValue = newVal;
			return old;
		}

		@Override
		public INode<K, V> parent() {
			return mParent;
		}

		@Override
		public INode<K, V> leftChild() {
			return mLeftChild;
		}

		@Override
		public INode<K, V> rightChild() {
			return mRightChild;
		}

		@Override
		public boolean isRed() {
			return mIsRed;
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof ITree.INode)) {
				return false;
			}
			ITree.INode<?, ?> e = (ITree.INode<?, ?>)o;
			
			return valEquals(mKey, e.getKey()) && valEquals(mValue, e.getValue());
		}
		
		public int hashCode() {
			int keyHash = (mKey == null ? 0 : mKey.hashCode());
			int valHash = (mValue == null ? 0 : mValue.hashCode());
			return keyHash ^ valHash;
		}
		
		public String toString() {
			return mKey + " = " + mValue;
		}
	}
	
	@Override
	public INode<K, V> getRoot() {
		return mRoot;
	}

	@Override
	public V addNode(INode<K, V> node) {
		return addNode(node.getKey(), node.getValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public V addNode(K key, V value) {
		Node<K,V> r = mRoot;
		
		if (r == null) {	// First case - insert to empty tree
			compare(key, key);
			mRoot = new Node<>(key, value, null);
			mSize = 1;
			return null;
		}
		
		int cmp;	// Compare result
		Node<K,V> parent;
		
		Comparator<? super K> comp = mComparator;
		if (comp != null) {
			do {
				parent = r;
				cmp = comp.compare(key, r.mKey);
				if (cmp < 0) {
					r = r.mLeftChild;
				} else if (cmp > 0) {
					r = r.mRightChild;
				} else {
					return r.setValue(value);
				}
			} while (r != null);
		} else {
			if (key == null) {
				throw new NullPointerException();
			}
			Comparable<? super K> k = (Comparable<? super K>)key;
			
			do {
				parent = r;
				cmp = k.compareTo(r.mKey);
				if (cmp < 0) {
					r = r.mLeftChild;
				} else if (cmp > 0) {
					r = r.mRightChild;
				} else {
					return r.setValue(value);
				}
			} while (r != null);
		}
		
		// Now r - leaf node. Now we can create new node and insert it
		Node<K,V> n = new Node<>(key, value, parent);
		if (cmp < 0) {
			parent.mLeftChild = n;
		} else {
			parent.mRightChild = n;
		}
		
		balanceTree(n);
		++mSize;
		return null;
	}

	@Override
	public void deleteNode(INode<K, V> node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNode(K key) {
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
		return mSize;
	}
	
	final Node<K,V> getNodeWithComparator(Object k) {
		@SuppressWarnings("unchecked")
		K key = (K)k;
		
		Comparator<? super K> comp = mComparator;
		if (comp != null) {
			Node<K,V> node = mRoot;
			while(node != null) {
				int cmp = comp.compare(node.mKey, key);
				if (cmp < 0) {
					node = node.mLeftChild;
				} else if (cmp > 0) {
					node = node.mRightChild;
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
	 * @param k Key to search.
	 * @return Node with key k or null if there are no node with such key.
	 */
	final Node<K,V> getNode(Object k) {
		if (mComparator != null) {
			getNodeWithComparator(k);
		} 
		if (k == null) {
			throw new NullPointerException();
		}
		
		@SuppressWarnings("unchecked")
		Comparable<? super K> key = (Comparable<? super K>)k;
		
		Node<K,V> node = mRoot;
		while (node != null) {
			int cmp = key.compareTo(node.mKey);
			if (cmp < 0) {
				node = node.mLeftChild;
			} else if (cmp > 0) {
				node = node.mRightChild;
			} else {
				return node;
			}
		}
		return null;
	}

	@Override
	public V get(K key) {
		Node<K,V> e = getNode(key);
		return e == null ? null : e.mValue;
	}

	@Override
	public V set(K key, V newVal) {
		Node<K,V> e = getNode(key);
		if (e == null) {
			return null;
		}
		
		V old = e.mValue;
		e.setValue(newVal);
		return old;
	}
	
	// Util methods ============================================================
	
	/**
	 * Compares two keys.
	 */
	@SuppressWarnings({ "unchecked"})
	final int compare(Object key1, Object key2) {
		if (mComparator == null) {
			return ((Comparable<? super K>)key1).compareTo((K)key2);
		} else {
			return mComparator.compare((K)key1, (K)key2);
		}
	}

	static final boolean valEquals(Object val1, Object val2) {
		return (val1 == null ? val2 == null : val1.equals(val2));
	}
	
	/**
	 * Balances tree after insertion.
	 * 
	 * @param x root of the tree to balance.
	 */
	private void balanceTree(Node<K,V> x) {
		
	}
}
