package rbtree;

import rbtree.ITree.INode;

/**
 * Interface for step by step task execution
 * 
 * @author ploskov
 *
 * @param <T>
 *            Return type
 * @param <K>
 * @param <V>
 */
public interface TreeExecution<T, K, V> {
	public void step();

	public boolean works();

	public T getResult();

	public INode<K, V> getNode();
}
