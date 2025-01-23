import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Experiment {
    public static void main(String[] args) {
        int i = 5;
        int n = (int) Math.pow(3, i + 7) - 1;
        System.out.println("n: " + n + " n/2: "+ n/2);
        List<Integer> elems = new ArrayList<Integer>();

        for (int j = 0; j < n; j++) {
            elems.add(j);
        }

        double runTime = 0.0;
        double heapSize = 0.0;
        double linkAmount = 0.0;
        double cutAmount = 0.0;
        double treeAmount = 0.0;

        for (int j = 0; j < 20; j++) {
            Collections.shuffle(elems);
            FibonacciHeap heap = new FibonacciHeap();
            FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[n + 1];

            long startTime = System.currentTimeMillis();

            for (int elem : elems) {
                nodes[elem] = heap.insert(elem, "info");
            }

            heap.deleteMin();

            for (int k = n; k > 31; k--) {
                heap.delete(nodes[k]);
            }

            long stopTime = System.currentTimeMillis();

            runTime += stopTime - startTime;
            heapSize += heap.size();
            linkAmount += heap.totalLinks();
            cutAmount += heap.totalCuts();
            treeAmount += heap.numTrees();
        }


        System.out.println("Run time: " + runTime / 20);
        System.out.println("Size: " + heapSize / 20);
        System.out.println("Size binary: " + Integer.toBinaryString((int)(heapSize / 20)));
        System.out.println("Link: " + linkAmount / 20);
        System.out.println("Cut: " + cutAmount / 20);
        System.out.println("Trees: " + treeAmount / 20);
    }
}
