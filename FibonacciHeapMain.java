import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciHeapMain {
    static void bugFound(String test) {
        System.out.println("Bug found in " + test);
//        grade -= testScore;
    }

    public static void main(String[] args) {
//        FibonacciHeap fibHeap = new FibonacciHeap();
//        fibHeap.insert(4, "4");
//        fibHeap.insert(2, "2");
//        fibHeap.insert(3, "3");
//        fibHeap.insert(7, "7");
//        FibonacciHeap.HeapNode node = fibHeap.insert(69, "69");
//        fibHeap.insert(42, "42");
//        fibHeap.printHeap();
//
//        fibHeap.deleteMin();
//        fibHeap.delete(node);
//
//        FibonacciHeap fibHeap2 = new FibonacciHeap();
//        fibHeap2.insert(5, "5");
//        node = fibHeap2.insert(6, "6");
//        fibHeap2.insert(1, "1");
//        fibHeap2.printHeap();
//
//        fibHeap.meld(fibHeap2);
//        fibHeap.deleteMin();
//        fibHeap.deleteMin();
//
//        fibHeap.decreaseKey(node, 4);
//
//        fibHeap.printHeap();
//        System.out.println("Min: " + fibHeap.findMin());
//        System.out.println("Size: " + fibHeap.size());
//        System.out.println("Num Trees: " + fibHeap.numTrees());
////        System.out.println("Total Links: " + fibHeap.totalLinks());
////        System.out.println("Total Cuts: " + fibHeap.totalCuts());
//
////
//
//        FibonacciHeap heap = new FibonacciHeap();
//
//        for (int i = 1; i <= 10; i++) {
//            heap.insert(i, "node" + i);
//        }
//        heap.printHeap();
//        heap.deleteMin();
//        System.out.println(heap.totalCuts());
//        heap.printHeap();
//        heap.deleteMin();
//        System.out.println(heap.totalCuts());
//        heap.printHeap();
//        heap.deleteMin();
//        System.out.println(heap.totalCuts());
//
//
//        heap.printHeap();
//        System.out.println("Min: " + heap.findMin());

        String test = "test26";
        FibonacciHeap fibonacciHeap = new FibonacciHeap();

        int size = 1000;
        int totalCuts = 0;
        int links = 0;

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i, "info");
        }

        for (int i = 0; i < size / 2; i++) {
            if (fibonacciHeap.findMin().key != i + 1) {
                bugFound(test);
                return;
            }
            totalCuts += fibonacciHeap.findMin().rank;
            fibonacciHeap.deleteMin();
        }

        System.out.println(fibonacciHeap.size());

        if (/*fibonacciHeap.potential() > 100 ||*/
                fibonacciHeap.totalCuts() - totalCuts != 0 ||
                        fibonacciHeap.totalLinks() - links < size - 100)
            bugFound(test);
    }
}
