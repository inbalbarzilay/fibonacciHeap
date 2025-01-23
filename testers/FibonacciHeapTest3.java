import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FibonacciHeapTest3 {

    private FibonacciHeap heap;

    @BeforeEach
    void setUp() {
        heap = new FibonacciHeap();
    }

    @Test
    void testInsertAndFindMin() {
        assertNull(heap.findMin(), "Heap should be empty initially.");

        heap.insert(10, "A");
        assertEquals(10, heap.findMin().key, "Min should be 10 after inserting (10, \"A\").");

        heap.insert(5, "B");
        assertEquals(5, heap.findMin().key, "Min should be 5 after inserting (5, \"B\").");

        heap.insert(20, "C");
        assertEquals(5, heap.findMin().key, "Min should still be 5 after inserting (20, \"C\").");
    }

    @Test
    void testDeleteMin() {
        heap.insert(10, "A");
        heap.insert(5, "B");
        heap.insert(20, "C");

        heap.deleteMin();
        assertEquals(10, heap.findMin().key, "Min should be 10 after deleting the previous min.");

        heap.deleteMin();
        assertEquals(20, heap.findMin().key, "Min should be 20 after deleting the previous min.");

        heap.deleteMin();
        assertNull(heap.findMin(), "Heap should be empty after deleting all elements.");
    }

    @Test
    void testDecreaseKey() {
        FibonacciHeap.HeapNode node = heap.insert(10, "A");
        heap.insert(20, "B");

        heap.decreaseKey(node, 5);
        assertEquals(5, heap.findMin().key, "Min should be 5 after decreasing the key of node (10, \"A\").");
    }

    @Test
    void testDelete() {
        FibonacciHeap.HeapNode nodeA = heap.insert(10, "A");
        heap.insert(5, "B");
        heap.insert(20, "C");

        heap.delete(nodeA);
        assertEquals(5, heap.findMin().key, "Min should be 5 after deleting node (10, \"A\").");
    }

    @Test
    void testMeld() {
        FibonacciHeap heap2 = new FibonacciHeap();

        heap.insert(10, "A");
        heap.insert(20, "B");

        heap2.insert(5, "C");
        heap2.insert(15, "D");

        heap.meld(heap2);
        assertEquals(5, heap.findMin().key, "Min should be 5 after melding with another heap containing (5, \"C\") and (15, \"D\").");
    }

    @Test
    void testSize() {
        assertEquals(0, heap.size(), "Heap size should be 0 initially.");

        heap.insert(10, "A");
        assertEquals(1, heap.size(), "Heap size should be 1 after one insertion.");

        heap.insert(20, "B");
        assertEquals(2, heap.size(), "Heap size should be 2 after two insertions.");

        heap.deleteMin();
        assertEquals(1, heap.size(), "Heap size should be 1 after deleting one element.");
    }

    @Test
    void testNumTrees() {
        assertEquals(0, heap.numTrees(), "Number of trees should be 0 initially.");

        heap.insert(10, "A");
        assertEquals(1, heap.numTrees(), "Number of trees should be 1 after one insertion.");

        heap.insert(20, "B");
        assertEquals(2, heap.numTrees(), "Number of trees should be 2 after two insertions.");

        heap.deleteMin();
        assertEquals(1, heap.numTrees(), "Number of trees should be 1 after deleting the min.");
    }

    @Test
    void testTotalLinksAndCuts() {
        assertEquals(0, heap.totalLinks(), "Total links should be 0 initially.");
        assertEquals(0, heap.totalCuts(), "Total cuts should be 0 initially.");

        FibonacciHeap.HeapNode node = heap.insert(10, "A");
        heap.insert(20, "B");

        heap.decreaseKey(node, 5);
        assertEquals(0, heap.totalCuts(), "Total cuts should be 0 after decreasing the key of a node.");

        heap.insert(30, "C");
        heap.deleteMin();
        assertTrue(heap.totalLinks() > 0, "Total links should be greater than 0 after successive linking.");
    }
}
