/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap {
	public HeapNode min;
	public HeapNode rootsListHead;
	public int totalLinks;
	public int totalCuts;
	public int size;
	public int numTrees;
	
	/**
	 *
	 * Constructor to initialize an empty heap.
	 *
	 */
	public FibonacciHeap() {
		this.min = null;
		this.rootsListHead = null;
		this.totalLinks = 0;
		this.totalCuts = 0;
		this.size = 0;
		this.numTrees = 0;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) {
		HeapNode node = new HeapNode(key, info);

		if (this.rootsListHead == null) {
			node.prev = node;
			node.next = node;
			this.min = node;
			this.rootsListHead = node;
		} else {
			node.next = this.rootsListHead;
			node.prev = this.rootsListHead.prev;
			this.rootsListHead.prev.next = node;
			this.rootsListHead.prev = node;
			this.rootsListHead = node;

			if (key < this.min.key) {
				this.min = node;
			}
		}

		this.size++;
		this.numTrees++;

		return node; // should be replaced by student code
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin() {
		return this.min; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() {
		return; // should be replaced by student code
	}

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapNode x, int diff) {
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		return; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks() {
		return this.totalLinks; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts() {
		return this.totalCuts; // should be replaced by student code
	}


	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		return; // should be replaced by student code   		
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size() {
		return this.size; // should be replaced by student code
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees() {
		return this.numTrees; // should be replaced by student code
	}

	public HeapNode link(HeapNode node1, HeapNode node2) {
		HeapNode parent, child;

		if (node1.key <= node2.key) {
			parent = node1;
			child = node2;
		} else {
			parent = node2;
			child = node1;
		}
		parent.addChild(child);
		return parent;
	}

	private void cut(HeapNode node) {
		HeapNode parent = node.parent;
		node.parent = null;
		node.mark = false;
		parent.rank -= 1;

		if (node.next == node) {
			parent.child = null;
		} else {
			parent.child = node.next;
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}

		this.totalCuts++;
	}

	private void cascadingCut(HeapNode node) {
		HeapNode parent = node.parent;
		this.cut(node);

		if (parent.parent != null) {
			if (!parent.mark) {
				parent.mark = true;
			} else {
				this.cascadingCut(parent);
			}
		}
	}

	// ######################## DELETE THIS ########################
	public void printHeap() {
		if (min == null) {
			System.out.println("The heap is empty.");
			return;
		}
		System.out.println("Fibonacci Heap:");

		HeapNode start = this.rootsListHead;
		HeapNode current = this.rootsListHead;
		int treeNumber = 1;

		do {
			System.out.println("Tree " + treeNumber + ":");
			printTree(current, "", true);
			current = current.next;
			treeNumber++;
		} while (current != start);
	}

	private void printTree(HeapNode node, String prefix, boolean isLast) {
		if (node == null) return;

		// Print the current node as (key, "value")
		System.out.print(prefix);
		System.out.print(isLast ? "└── " : "├── ");
		System.out.println("(" + node.key + ", \"" + node.info + "\")");

		// Prepare prefix for the next level
		prefix += isLast ? "    " : "│   ";

		// Recursively print children
		if (node.child != null) {
			HeapNode child = node.child;
			do {
				printTree(child, prefix, child.next == node.child);
				child = child.next;
			} while (child != node.child);
		}
	}
	// ######################## DELETE THIS ########################


	/**
	 * Class implementing a node in a Fibonacci Heap.
	 *  
	 */
	public static class HeapNode {
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public boolean mark;

		public HeapNode(int key, String info) {
			this.key = key;
			this.info = info;
			this.child = null;
			this.next = null;
			this.prev = null;
			this.parent = null;
			this.rank = 0;
			this.mark = false;
		}

		public String toString() {
			return "(" + this.key + ", \"" + this.info + "\")";
		}

		private void addChild(HeapNode newChild) {
			newChild.parent = this;

			if (this.child == null) {
				this.child = newChild;
				newChild.next = newChild;
				newChild.prev = newChild;
			} else {
				newChild.next = this.child;
				newChild.prev = this.child.prev;
				this.child.prev.next = newChild;
				this.child.prev = newChild;
				this.child = newChild;
			}

			this.rank++;
		}
	}
}
