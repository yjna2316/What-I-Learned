package priorityQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.Inet4Address;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class PriorityQueueTest {

    private PriorityQueue priorityQueue;

    @Before
    public void init() {
        priorityQueue = new PriorityQueue();
    }

    @Test
    public void testInitEmpty() {
        assertTrue(priorityQueue.isEmpty());
        assertEquals(0, priorityQueue.heapSize);
    }

    @Test
    public void testAddMaxHeap() throws Exception {
        int max = 0;
        for (int i = 0; i < 3; ++i) {
            max = randomInput(10, max);
            int top = priorityQueue.peek();
            printHeap();
            String log = String.format(
                "max: %d top: %d heapSize: %d",
                max,
                top,
                priorityQueue.heapSize);
            System.out.println(log);
            assertEquals(max, top);
            System.out.println();
        }
    }

    @Test
    public void testRemoveMaxHeap() throws Exception {
        int max =  randomInput(4, 0);
        for (int i = 0; i < 4; ++i) {
            printHeap();
            int top = priorityQueue.peek();
            int pop = priorityQueue.removeMaxHeap();
            int heapSize = priorityQueue.heapSize;
            String log = String.format(
                "%d th pop: %d heapsize: %d",
                i + 1, 
                pop, 
                heapSize);
            System.out.println(log);
            assertEquals(top, pop);
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeek() throws Exception {
        assertEquals(true, priorityQueue.isEmpty());
        priorityQueue.peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmptyHeap() throws Exception {
        assertEquals(true, priorityQueue.isEmpty());
        priorityQueue.removeMaxHeap();
    }

    public int randomInput(int n, int max) {
        for (int i = 0; i < n; ++i) {
            int random = (int)(Math.random() * 100 + 1);
            priorityQueue.addMaxHeap(random);
            max = (random > max) ? random : max;
            String log = String.format(
                "%d th: %d heapsize: %d max: %d",
                i + 1, 
                random, 
                priorityQueue.heapSize, 
                max);
            System.out.println(log);
        }
        System.out.println();
        return max;
    }

    public void printHeap() {
        for (int key : priorityQueue.a) {
            System.out.print(key+ " ");
        }
        System.out.println();
    }

}