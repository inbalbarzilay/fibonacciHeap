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
	public boolean shouldConsolidate;
	
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
		this.shouldConsolidate = true;
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

		return node;
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin() {
		return this.min;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() {
		if ((this.min == null) || (this.min.child == null && this.min.next == this.min)) {
			this.min = null;
			this.rootsListHead = null;
			this.size = 0;
			this.numTrees = 0;
		} else {
			if (this.min.next != this.min) {
				if (this.rootsListHead == this.min) {
					this.rootsListHead = this.min.next;
				}

				this.min.next.prev = this.min.prev;
				this.min.prev.next = this.min.next;
			}
			
			this.numTrees += min.rank - 1;

			if (this.min.child != null) {
				if (this.min.next == this.min) {
					this.rootsListHead = this.min.child;
				}

				FibonacciHeap minChildren = new FibonacciHeap();
				minChildren.rootsListHead = this.min.child;
				minChildren.min = minChildren.rootsListHead;
				HeapNode currChild = minChildren.rootsListHead;

				for (int i = 0; i < this.min.rank; i++) {
					currChild.parent = null;

					if (currChild.key < minChildren.min.key) {
						minChildren.min = currChild;
					}

					currChild = currChild.next;
					this.totalCuts++;
				}
				this.meld(minChildren);
			}

			if (this.shouldConsolidate) {
				this.successiveLinking();
			}
			this.size--;
		}
	}

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapNode x, int diff) {
		if (x != null) {
			x.key -= diff;

			if (x.parent == null) {
				if (x.key < this.min.key) {
					this.min = x;
				}
			} else if (x.key < x.parent.key) {
				this.cascadingCut(x);
			}
		}
	}

	/**
	 * 
	 * Delete the x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		if (x != null) {
			HeapNode heapMin = this.min;
			HeapNode rootsHead = this.rootsListHead;

			if (this.rootsListHead == x && x.next != x) {
				rootsHead = x.next;
			}

			if (this.rootsListHead != x || this.min != x) { // this.rootsListHead != x  why?
				this.shouldConsolidate = false;
			}

			this.decreaseKey(x, x.key);
			this.deleteMin();

			this.min = heapMin;
			this.rootsListHead = rootsHead;
			this.shouldConsolidate = true;
		}
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
		if ((this == null) || (heap2 != null && this.rootsListHead == null)) {
			this.min = heap2.min;
			this.rootsListHead = heap2.rootsListHead;
			this.totalLinks = heap2.totalLinks;
			this.totalCuts = heap2.totalCuts;
			this.size = heap2.size;
			this.numTrees = heap2.numTrees;
			this.shouldConsolidate = heap2.shouldConsolidate;
		} else if (heap2 != null && heap2.rootsListHead != null) {
			HeapNode rootsListLast = this.rootsListHead.prev;
			rootsListLast.next.prev = heap2.rootsListHead.prev;
			heap2.rootsListHead.prev.next = rootsListLast.next;
			rootsListLast.next = heap2.rootsListHead;
			heap2.rootsListHead.prev = rootsListLast;

			if (heap2.min.key < this.min.key) {
				this.min = heap2.min;
			}

			this.totalLinks += heap2.totalLinks();
			this.totalCuts += heap2.totalCuts();
			this.size += heap2.size();
			this.numTrees += heap2.numTrees();
		}
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

	private HeapNode link(HeapNode node1, HeapNode node2) {
		HeapNode parent, child;

		if (node1.key <= node2.key) {
			parent = node1;
			child = node2;
		} else {
			parent = node2;
			child = node1;
		}
		parent.addChild(child);
		this.totalLinks++;

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

		node.next = this.rootsListHead;
		node.prev = this.rootsListHead.prev;
		this.rootsListHead.prev.next = node;
		this.rootsListHead.prev = node;
		this.rootsListHead = node;

		if (node.key < this.min.key) {
			this.min = node;
		}

		this.numTrees++;
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

	private void successiveLinking() {
		HeapNode[] roots = new HeapNode[this.size + 1];
		HeapNode currRoot = this.rootsListHead;

		for (int i = 0; i < this.numTrees; i++) {
			HeapNode nextRoot = currRoot.next;

			if (roots[currRoot.rank] == null) {
				roots[currRoot.rank] = currRoot;
			} else {
				while (roots[currRoot.rank] != null) {
					currRoot = link(currRoot, roots[currRoot.rank]);
					roots[currRoot.rank - 1] = null;
				}
				roots[currRoot.rank] = currRoot;
			}
			currRoot = nextRoot;
		}

		HeapNode firstRoot = null, lastRoot = null, min = null;
		this.numTrees = 0;

		for (HeapNode root : roots) {
			if (root == null) {
				continue;
			}

			this.numTrees++;

			if (min == null || root.key < min.key) {
				min = root;
			}

			if (firstRoot == null) {
				firstRoot = root;
			}
			if (lastRoot == null) {
				lastRoot = root;
				continue;
			}

			root.prev = lastRoot;
			lastRoot.next = root;
			lastRoot = root;
		}
		lastRoot.next = firstRoot;
		firstRoot.prev = lastRoot;

		this.rootsListHead = firstRoot;
		this.min = min;
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

		// ######################## DELETE THIS ########################
		public String toString() {
			return "(" + this.key + ", \"" + this.info + "\")";
		}
		// ######################## DELETE THIS ########################

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
