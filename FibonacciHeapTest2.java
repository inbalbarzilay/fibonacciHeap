import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FibonacciHeapTest2 {
    @Test
    public void testInsertAndFindMin() {
        FibonacciHeap heap = new FibonacciHeap();

        assertNull(heap.findMin(), "Heap should be empty initially.");

        heap.insert(10, "A");
        assertEquals(10, heap.findMin().key, "Min should be 10 after inserting (10, 'A').");

        heap.insert(5, "B");
        assertEquals(5, heap.findMin().key, "Min should be 5 after inserting (5, 'B').");

        heap.insert(15, "C");
        assertEquals(5, heap.findMin().key, "Min should remain 5 after inserting (15, 'C').");
    }

    @Test
    public void testDeleteMin() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "A");
        heap.insert(5, "B");
        heap.insert(15, "C");

        heap.deleteMin();
        assertEquals(10, heap.findMin().key, "Min should be 10 after deleting the minimum (5).");

        heap.deleteMin();
        assertEquals(15, heap.findMin().key, "Min should be 15 after deleting the minimum (10).");

        heap.deleteMin();
        assertNull(heap.findMin(), "Heap should be empty after deleting all elements.");
    }

    @Test
    public void testDecreaseKey() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode node = heap.insert(10, "A");
        heap.insert(15, "B");
        heap.insert(20, "C");

        heap.decreaseKey(node, 5);
        assertEquals(5, heap.findMin().key, "Min should be 5 after decreasing key of node with key 10 by 5.");
    }

    @Test
    public void testDelete() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode node1 = heap.insert(10, "A");
        FibonacciHeap.HeapNode node2 = heap.insert(5, "B");
        heap.insert(15, "C");

        heap.delete(node1);
        assertEquals(5, heap.findMin().key, "Min should remain 5 after deleting node with key 10.");

        heap.delete(node2);
        assertEquals(15, heap.findMin().key, "Min should be 15 after deleting node with key 5.");
    }

    @Test
    public void testMeld() {
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();

        heap1.insert(10, "A");
        heap1.insert(20, "B");

        heap2.insert(5, "C");
        heap2.insert(15, "D");

        heap1.meld(heap2);
        assertEquals(5, heap1.findMin().key, "Min should be 5 after melding two heaps.");
    }

    @Test
    public void testCounts() {
        FibonacciHeap heap = new FibonacciHeap();

        heap.insert(10, "A");
        heap.insert(5, "B");
        heap.insert(15, "C");

        assertEquals(3, heap.size(), "Heap size should be 3 after three insertions.");

        heap.deleteMin();
        assertEquals(2, heap.size(), "Heap size should be 2 after deleting the minimum.");

        assertEquals(1, heap.totalLinks(), "Total links should be 1 after consolidating.");
        assertEquals(0, heap.totalCuts(), "Total cuts should be 0 if no cutting operations occurred.");
    }

    @Test
    public void testNumTrees() {
        FibonacciHeap heap = new FibonacciHeap();

        heap.insert(10, "A");
        heap.insert(5, "B");
        heap.insert(15, "C");

        assertEquals(3, heap.numTrees(), "Heap should have 3 trees after three separate insertions.");

        heap.deleteMin();
        assertTrue(heap.numTrees() > 0, "Heap should have at least one tree after deleteMin.");
    }

    @Test
    public void testCascadingCut() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode parent = heap.insert(10, "Parent");
        FibonacciHeap.HeapNode child = heap.insert(15, "Child");
        heap.insert(1, "Min");
        heap.deleteMin();

        heap.decreaseKey(child, 10);

        assertNull(child.parent, "Child should be removed from parent after cascading cut.");
        assertEquals(2, heap.numTrees(), "Heap should have two trees after cascading cut.");
        assertEquals(1, heap.totalCuts(), "Total cuts should be 1 after cascading cut.");
    }

    @Test
    public void testConsolidation() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(10, "A");
        heap.insert(20, "B");
        heap.insert(30, "C");
        heap.insert(40, "D");
        heap.insert(50, "E");
        heap.insert(60, "F");

        heap.deleteMin();

        assertEquals(2, heap.numTrees(), "Heap should have 2 trees after consolidation.");
        assertTrue(heap.size() > 0, "Heap should not be empty after consolidation.");
    }

    @Test
    public void testEdgeCases() {
        FibonacciHeap heap = new FibonacciHeap();

        //assertThrows(NullPointerException.class, () -> heap.decreaseKey(null, 5), "Should throw exception for null node in decreaseKey.");

        //assertThrows(NullPointerException.class, () -> heap.delete(null), "Should throw exception for null node in delete.");

        assertDoesNotThrow(() -> heap.deleteMin(), "Deleting min from an empty heap should not throw an exception.");
    }
}
