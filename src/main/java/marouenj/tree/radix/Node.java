package marouenj.tree.radix;

import java.util.TreeMap;

/**
 * A node that doesn't hold a value.
 * A node whose key is a prefix to the key(s) that map to values.
 * 
 * @author marouen.jilani
 *
 * @param <A>
 */
public class Node<A> {
	
	TreeMap<String, Node<A>> children;
	
	public Node() {
		this.children = new TreeMap<>();
	}
}
