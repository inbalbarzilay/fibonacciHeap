public class FibonacciHeapMain {
    public static void main(String[] args) {
        FibonacciHeap fibHeap = new FibonacciHeap();
        fibHeap.insert(4, "4");
        fibHeap.insert(2, "2");
        fibHeap.insert(3, "3");
        fibHeap.printHeap();

        FibonacciHeap fibHeap2 = new FibonacciHeap();
        fibHeap2.insert(5, "5");
        fibHeap2.insert(6, "6");
        fibHeap2.insert(1, "1");
        fibHeap2.printHeap();

        fibHeap.meld(fibHeap2);

        fibHeap.printHeap();
        System.out.println("Min: " + fibHeap.findMin());
        System.out.println("Size: " + fibHeap.size());
        System.out.println("Num Trees: " + fibHeap.numTrees());
        System.out.println("Total Links: " + fibHeap.totalLinks());
        System.out.println("Total Cuts: " + fibHeap.totalCuts());
    }
}
