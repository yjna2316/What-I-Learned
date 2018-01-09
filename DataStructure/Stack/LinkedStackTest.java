package Stack;

import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class LinkedStackTest {

    private LinkedStack s;

    @Before
    public void LinkedStackTest() {
        s = new LinkedStack();
    }

    @Test
    public void testInitStackIsEmpty() {
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void testPushToEmptyStack() {
        s.push(1);
        assertFalse(s.isEmpty());
        s.pop();
    }

    @Test
    public void testPushThenPeek() {
        int data = 1;
        s.push(data);

        int size = s.size();
        assertEquals(data, s.peek());
        assertEquals(size, s.size());
    }

    @Test
    public void testPushThenPop() {
        s.push(1);
        int data = s.peek();
        int size = s.size();
        assertEquals(data, s.pop());
        assertEquals(size - 1, s.size());
    }

    @Test(expected = EmptyStackException.class)
    public void testPeekEmptyStack() {
        assertTrue(s.isEmpty());
        s.peek();
    }

    @Test(expected = EmptyStackException.class)
    public void testPopEmptyStack() {
        assertTrue(s.isEmpty());
        s.pop();
    }


}