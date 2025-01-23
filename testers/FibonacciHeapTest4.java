import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FibonacciHeapTest4 {
    private FibonacciHeap heap;

    @BeforeEach
    void setUp() {
        heap = new FibonacciHeap();
    }

    @Test
    void testEmptyHeap() {
        assertNull(heap.findMin());
        assertEquals(0, heap.size());
        assertEquals(0, heap.numTrees());
        assertEquals(0, heap.totalLinks());
        assertEquals(0, heap.totalCuts());
    }

    @Test
    void testCutCounter() {
        // Insert nodes
        heap.insert(1, "one");
        heap.insert(2, "two");
        heap.insert(3, "three");
        heap.insert(4, "four");
        heap.insert(5, "five");

        // First deleteMin - should not increase cuts
        heap.deleteMin();
        assertEquals(0, heap.totalCuts());

        // Second deleteMin - now cuts should be present
        heap.deleteMin();
        assertTrue(heap.totalCuts() > 0);
    }

    @Test
    void testDeleteMinNoCutsFirstTime() {
        heap.insert(5, "five");
        heap.insert(3, "three");
        heap.insert(7, "seven");

        // First deleteMin should not increase cuts
        int initialCuts = heap.totalCuts();
        heap.deleteMin();
        assertEquals(initialCuts, heap.totalCuts());
    }

    @Test
    void testInsertSingleNode() {
        FibonacciHeap.HeapNode node = heap.insert(5, "test");

        assertEquals(1, heap.size());
        assertEquals(1, heap.numTrees());
        assertEquals(5, heap.findMin().key);
        assertEquals("test", heap.findMin().info);
        assertEquals(node, heap.findMin());
        assertEquals(0, heap.totalCuts());
    }

    @Test
    void testInsertMultipleNodes() {
        heap.insert(5, "five");
        heap.insert(3, "three");
        heap.insert(7, "seven");

        assertEquals(3, heap.size());
        assertEquals(3, heap.numTrees());
        assertEquals(3, heap.findMin().key);
        assertEquals("three", heap.findMin().info);
        assertEquals(0, heap.totalCuts());
    }

    @Test
    void testDeleteMin() {
        heap.insert(5, "five");
        heap.insert(3, "three");
        heap.insert(7, "seven");

        int initialCuts = heap.totalCuts();
        heap.deleteMin();
        assertEquals(initialCuts, heap.totalCuts());
        assertEquals(2, heap.size());
        assertEquals(5, heap.findMin().key);
    }

    @Test
    void testDeleteMinSingleNode() {
        heap.insert(5, "five");
        heap.deleteMin();

        assertNull(heap.findMin());
        assertEquals(0, heap.size());
        assertEquals(0, heap.numTrees());
        assertEquals(0, heap.totalCuts());
    }

    @Test
    void testDecreaseKey() {
        FibonacciHeap.HeapNode node = heap.insert(10, "ten");
        heap.insert(5, "five");
        heap.insert(7, "seven");

        heap.decreaseKey(node, 8);  // 10 -> 2
        assertEquals(2, heap.findMin().key);
        assertEquals("ten", heap.findMin().info);
    }

    @Test
    void testDelete() {
        FibonacciHeap.HeapNode node = heap.insert(5, "five");
        heap.insert(3, "three");
        heap.insert(7, "seven");

        heap.delete(node);
        assertEquals(2, heap.size());
        assertEquals(3, heap.findMin().key);
    }

    @Test
    void testMeld() {
        FibonacciHeap heap2 = new FibonacciHeap();

        heap.insert(5, "five");
        heap.insert(7, "seven");
        heap2.insert(3, "three");
        heap2.insert(4, "four");

        heap.meld(heap2);

        assertEquals(4, heap.size());
        assertEquals(4, heap.numTrees());
        assertEquals(3, heap.findMin().key);
        assertEquals(0, heap.totalCuts());  // Meld should not cause cuts
    }

    @Test
    void testMultipleDeleteMins() {
        // Insert several nodes
        for (int i = 10; i >= 1; i--) {
            heap.insert(i, "node" + i);
        }

        // First deleteMin - should not increase cuts
        heap.deleteMin();
        int cutsAfterFirst = heap.totalCuts();
        assertEquals(0, cutsAfterFirst);

        // Second deleteMin - should increase cuts
        heap.deleteMin();
        assertTrue(heap.totalCuts() > cutsAfterFirst);

        // Third deleteMin - should further increase cuts
        heap.deleteMin();
        assertTrue(heap.totalCuts() > cutsAfterFirst);
    }

    @Test
    void testLargeOperationsWithCuts() {
        // Insert many nodes
        for (int i = 20; i >= 1; i--) {
            heap.insert(i, "node" + i);
        }

        assertEquals(0, heap.totalCuts());  // No cuts yet

        // First deleteMin
        heap.deleteMin();
        assertEquals(0, heap.totalCuts());  // Still no cuts

        // Second deleteMin should trigger cuts
        heap.deleteMin();
        assertTrue(heap.totalCuts() > 0);

        int cutsAfterSecond = heap.totalCuts();

        // Third deleteMin should increase cuts further
        heap.deleteMin();
        assertTrue(heap.totalCuts() > cutsAfterSecond);
    }

    @Test
    void testComplexOperationsWithCuts() {
        // Insert nodes and perform operations
        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[10];
        for (int i = 9; i >= 0; i--) {
            nodes[i] = heap.insert(i + 1, "node" + i);
        }

        assertEquals(0, heap.totalCuts());  // No cuts initially

        // First deleteMin
        heap.deleteMin();
        assertEquals(0, heap.totalCuts());  // Still no cuts

        // Second deleteMin
        heap.deleteMin();
        int cutsAfterSecondDelete = heap.totalCuts();
        assertTrue(cutsAfterSecondDelete > 0);

        // Decrease some keys
        heap.decreaseKey(nodes[5], 2);
        heap.decreaseKey(nodes[7], 3);

        // Third deleteMin
        heap.deleteMin();
        assertTrue(heap.totalCuts() > cutsAfterSecondDelete);
    }
}