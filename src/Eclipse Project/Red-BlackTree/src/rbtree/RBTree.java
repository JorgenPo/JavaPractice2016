package rbtree;

public class RBTree<K,V>
implements ITree<K, V> {

	static class Node<K,V> implements ITree.INode<K, V> {
		K mKey;
		V mValue;
		
		Node<K,V> mParent;
		Node<K,V> mLeftChild;
		Node<K,V> mRightChild;
		
		boolean mIsRed = false;
		
		public Node(K key, V value) {
			mKey = key;
			mValue = value;
		}
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNode(INode<K, V> node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNode(K key, V value) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V set(K key, V newVal) {
		// TODO Auto-generated method stub
		return null;
	}

	// Util methods
	static final boolean valEquals(Object val1, Object val2) {
		return (val1 == null ? val2 == null : val1.equals(val2));
	}
}
