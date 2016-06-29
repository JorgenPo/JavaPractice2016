package rbtree;

/**
 * Tree-like data structure interface.
 * 
 * @author �������
 *
 * @param <K> Key type	
 * @param <V> Value type
 */
public interface ITree<K, V> {
	/**
	 * Node of the tree. Key-value pair.
	 * @author �������
	 *
	 * @param <K> Key type
	 * @param <V> Value type
	 */
	static interface INode<K, V> {
		public V getValue();
		public V setValue(V newVal);
		
		public K getKey();
		
		public INode<K,V> parent();
		public INode<K,V> leftChild();
		public INode<K,V> rightChild();
		
		public boolean isRed();
		
		public int hashCode();
		public boolean equals(Object o);
	}
	
	public INode<K,V> getRoot();
	
	public V put(K key, V value);
	
	public void remove(INode<K,V> node);
	public void remove(K key);
	
	public boolean containsKey(K key);
	public boolean containsValue(V val);
	
	public int getLength();
	public int getSize();
	
	public V get(K key);
	public V set(K key, V newVal);
}
