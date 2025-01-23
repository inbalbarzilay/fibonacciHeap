// Adapted TestFibonacciHeap.java to match the provided FibonacciHeap.java implementation

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.*;
import org.junit.jupiter.api.*;

public class Tester {
    FibonacciHeap heap;

    @BeforeEach
    void setUp() {
        heap = new FibonacciHeap();
    }

    @Test
    void testInsertionAndFindMin() {
        heap.insert(10, "ten");
        heap.insert(5, "five");
        heap.insert(20, "twenty");

        assertEquals(5, heap.findMin().key, "findMin() should return the smallest key.");
    }

    @Test
    void testDeleteMin() {
        heap.insert(10, "ten");
        heap.insert(5, "five");
        heap.insert(20, "twenty");

        heap.deleteMin();

        assertEquals(10, heap.findMin().key, "After deleting the minimum, findMin() should return the next smallest key.");
        assertEquals(2, heap.size(), "Heap size should decrease after deleting the minimum.");
    }

    @Test
    void testDecreaseKey() {
        FibonacciHeap.HeapNode node = heap.insert(10, "ten");
        heap.insert(20, "twenty");
        heap.decreaseKey(node, 8);

        assertEquals(2, node.key, "The key should decrease by the specified value.");
        assertEquals(2, heap.findMin().key, "findMin() should return the updated smallest key.");
    }

    @Test
    void testDeleteNode() {
        FibonacciHeap.HeapNode node = heap.insert(10, "ten");
        heap.insert(5, "five");
        heap.insert(20, "twenty");

        heap.delete(node);

        assertEquals(2, heap.size(), "Heap size should decrease after deleting a node.");
        assertEquals(5, heap.findMin().key, "findMin() should return the smallest key after deletion.");
    }

    @Test
    void testMeld() {
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();

        heap1.insert(10, "ten");
        heap1.insert(20, "twenty");

        heap2.insert(5, "five");
        heap2.insert(15, "fifteen");

        heap1.meld(heap2);

        assertEquals(4, heap1.size(), "Heap size should be the sum of the sizes of the two heaps after melding.");
        assertEquals(5, heap1.findMin().key, "findMin() should return the smallest key across both heaps.");
    }

    @Test
    void testEmptyHeap() {
        assertTrue((heap.size() == 0), "Newly created heap should be empty.");
        assertNull(heap.findMin(), "findMin() should return null for an empty heap.");
    }

    @Test
    void testHeapPropertiesAfterMultipleOperations() {
        heap.insert(30, "thirty");
        heap.insert(40, "forty");
        heap.insert(50, "fifty");

        heap.deleteMin();

        assertEquals(40, heap.findMin().key, "findMin() should return the smallest key after deleteMin().");
        assertEquals(2, heap.size(), "Heap size should update correctly after operations.");

        FibonacciHeap.HeapNode node = heap.insert(10, "ten");
        heap.decreaseKey(node, 5);

        assertEquals(5, heap.findMin().key, "Heap should maintain correct minimum after decreaseKey.");
    }
}
