public class FibonacciHeapMain {
    public static void main(String[] args) {
        FibonacciHeap fibHeap = new FibonacciHeap();
        fibHeap.insert(1, "1");
        fibHeap.insert(2, "2");
        fibHeap.insert(3, "3");
        fibHeap.printHeap();
        System.out.println("Min: " + fibHeap.findMin());
    }
}
