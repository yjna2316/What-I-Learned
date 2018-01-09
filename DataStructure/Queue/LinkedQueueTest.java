package Queue;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class LinkedQueueTest {
    private LinkedQueue q;
    @Before
    public void InitQueue(){
        q = new LinkedQueue();
    }

    @Test
    public void testInitQueueIsEmpty() {
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void testEnqueueToEmptyQueue() {
        q.enqueue(1);
        assertFalse(q.isEmpty());
    }

    @Test
    public void testEnqueueThenPeek() {
        int data = 1;
        q.enqueue(data);
        int size = q.size();
        assertEquals(data, q.peek());
        assertEquals(size, q.size());
    }

    @Test
    public void testEnqueue() {
        int data = 1;
        int size = q.size();
        q.enqueue(data);
        assertEquals(data, q.peek());
        assertEquals(size + 1, q.size());
    }

    @Test
    public void testEnqueueThenDequeue() {
        int first = 1, second = 2;
        q.enqueue(first);
        q.enqueue(second);
        int size = q.size();
        assertEquals(first, q.dequeue());
        assertEquals(second, q.peek());
        assertEquals(size - 1, q.size());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertTrue(q.isEmpty());
        assertNull(q.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeqEmptyQueue() {
        assertTrue(q.isEmpty());
        q.dequeue();
    }
}