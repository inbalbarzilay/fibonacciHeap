public class FibonacciHeapMain {
    public static void main(String[] args) {
        FibonacciHeap fibHeap = new FibonacciHeap();
        fibHeap.insert(4, "4");
        fibHeap.insert(2, "2");
        fibHeap.insert(3, "3");
        fibHeap.insert(7, "7");
        FibonacciHeap.HeapNode node = fibHeap.insert(69, "69");
        fibHeap.insert(42, "42");
        fibHeap.printHeap();

        fibHeap.deleteMin();
        fibHeap.delete(node);

        FibonacciHeap fibHeap2 = new FibonacciHeap();
        fibHeap2.insert(5, "5");
        node = fibHeap2.insert(6, "6");
        fibHeap2.insert(1, "1");
        fibHeap2.printHeap();

        fibHeap.meld(fibHeap2);
        fibHeap.deleteMin();
        fibHeap.deleteMin();

        fibHeap.decreaseKey(node, 4);

        fibHeap.printHeap();
        System.out.println("Min: " + fibHeap.findMin());
        System.out.println("Size: " + fibHeap.size());
        System.out.println("Num Trees: " + fibHeap.numTrees());
        System.out.println("Total Links: " + fibHeap.totalLinks());
        System.out.println("Total Cuts: " + fibHeap.totalCuts());
    }
}
