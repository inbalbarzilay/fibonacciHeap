/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 * user1 - inbalb4
 * id1 - 212321202
 * user2 - shwartzman1
 * id2 - 206479081
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
	 * Complexity: O(1)
	 * Returns:
	 * A new FibonacciHeap instance
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
	 * Complexity: O(1)
	 * Receives:
	 * Integer key - The new node's key
	 * String info - The new node's info
	 * Returns:
	 * A pointer to the new node
	 *
	 */
	public HeapNode insert(int key, String info) {
		HeapNode node = new HeapNode(key, info);

		if (this.rootsListHead == null) {
			// add node to empty roots list
			node.prev = node;
			node.next = node;
			this.min = node;
			this.rootsListHead = node;
		} else {
			// concat node to roots list
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
	 * Complexity: O(1)
	 * Returns:
	 * A pointer to the node with the minimal key
	 *
	 */
	public HeapNode findMin() {
		return this.min;
	}

	/**
	 * 
	 * Delete the node with the minimal key
	 * Complexity: O(log n) amortized
	 *
	 */
	public void deleteMin() {
		if ((this.min == null) || (this.min.child == null && this.min.next == this.min)) {
			// handle case where minimum is the only element in the heap
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

				// create new heap from the minimum's children and meld to heap
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
	 * Complexity: O(1) amortized
	 * Receives:
	 * HeapNode x - A pointer to the node whose key will be decreased
	 * Integer diff - Delta value of the amount to decrease
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
	 * Complexity: O(log n) amortized
	 * Receives:
	 * HeapNode x - A pointer to the node to be deleted
	 *
	 */
	public void delete(HeapNode x) {
		if (x != null) {
			// save heap fields to restore after deletion
			HeapNode heapMin = this.min;
			HeapNode rootsHead = this.rootsListHead;
			boolean isMin = (x == this.min);

			if (this.rootsListHead == x && x.next != x) {
				rootsHead = x.next;
			}

			if (this.min != x) {
				this.shouldConsolidate = false;
			}

			this.decreaseKey(x, x.key);
			this.deleteMin();

			if (!isMin) {
				this.min = heapMin;
			}
			if (!shouldConsolidate) {
				this.rootsListHead = rootsHead;
			}
			this.shouldConsolidate = true;
		}
	}


	/**
	 * 
	 * Return the total number of links.
	 * Complexity - O(1)
	 * Returns:
	 * The total number of links performed on the heap
	 * 
	 */
	public int totalLinks() {
		return this.totalLinks;
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * Complexity - O(1)
	 * Returns:
	 * The total amound of cuts performed on the heap
	 * 
	 */
	public int totalCuts() {
		return this.totalCuts;
	}


	/**
	 * 
	 * Meld the heap with heap2
	 * Complexity - O(1)
	 * Receives:
	 * FibonacciHeap heap2 - Another FibonacciHeap instance to be melded into the heap
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		if (heap2 != null && heap2.rootsListHead != null && this.rootsListHead == null) {
			// handle case where current heap is empty
			this.min = heap2.min;
			this.rootsListHead = heap2.rootsListHead;
			this.totalLinks = heap2.totalLinks;
			this.totalCuts = heap2.totalCuts;
			this.size = heap2.size;
			this.numTrees = heap2.numTrees;
			this.shouldConsolidate = heap2.shouldConsolidate;
		} else if (heap2 != null && heap2.rootsListHead != null) {
			// concat roots lists
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
	 * Return the number of elements in the heap.
	 * Complexity - O(1)
	 * Returns:
	 * The current size (number of nodes) of the heap
	 *   
	 */
	public int size() {
		return this.size;
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * Complexity - O(1)
	 * Returns:
	 * The current number of trees in the heap
	 * 
	 */
	public int numTrees() {
		return this.numTrees;
	}

	/**
	 *
	 * Link two trees with the same rank.
	 * Complexity - O(1)
	 * Receives:
	 * HeapNode node1 - A pointer to the root of the first tree to be linked
	 * HeapNode node2 - A pointer to the root of the second tree to be linked
	 * Returns:
	 * A pointer to the tree root of the new tree
	 *
	 */
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

		if (child == this.rootsListHead) {
			this.rootsListHead = parent;
		}

		this.totalLinks++;

		return parent;
	}

	/**
	 *
	 * Cut a node from its parent.
	 * Complexity - O(1)
	 * Receives:
	 * HeapNode node - A pointer to the node to be cut from its parent
	 *
	 */
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

		// add node to roots list after cutting
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

	/**
	 *
	 * Recursively cut a node from its parent while the parent is not marked.
	 * Complexity - O(1) amortized
	 * Recieves:
	 * HeapNode node - A pointer to the node from which to start the cascading cut
	 *
	 */
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

	/**
	 *
	 * Link trees of the same rank until all trees are of different ranks.
	 * Complexity - O(log n) amortized
	 *
	 */
	private void successiveLinking() {
		HeapNode[] roots = new HeapNode[this.size + 1];
		HeapNode currRoot = this.rootsListHead;

		// perform successive linking on the heap's current trees
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

		// build the updated heap after successive linking
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

		/**
		 *
		 * Constructor to initialize a heap node.
		 * Complexity: O(1)
		 * Returns:
		 * A new HeapNode instance
		 *
		 */
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

		/**
		 *
		 * Add a node to the heap node's children list.
		 * Complexity: O(1)
		 * Receives:
		 * HeapNode newChild - A pointer to the node to be added to the current node's children list
		 *
		 */
		private void addChild(HeapNode newChild) {
			if (this.prev == newChild) {
				this.prev = newChild.prev;
			}

			if (this.next == newChild) {
				this.next = newChild.next;
			}

			newChild.parent = this;

			if (this.child == null) {
				this.child = newChild;
				newChild.next = newChild;
				newChild.prev = newChild;
			} else {
				// add newChild to the current node's children list
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
