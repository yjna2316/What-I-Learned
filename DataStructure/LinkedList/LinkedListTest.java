package LinkedList;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class LinkedListTest {

    private LinkedList list;

    @Before
    public void init() {
        list = new LinkedList();
    }

    @Test
    public void TestInitEmptyList() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void TestAddToEmptyList() {
        assertTrue(list.isEmpty());
        list.addFirst(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    public void TestAddFirst() {
        int data  = 1;
        int sizeBefore = list.size();
        list.addFirst(data);
        int sizeAfter = list.size();
        assertEquals(sizeBefore  + 1, sizeAfter);
        assertEquals(data, list.get(sizeAfter - 1));
    }

    @Test
    public void TestAddLast() {
        int data = 1;
        int sizeBefore = list.size();
        list.addLast(data);
        int sizeAfter = list.size();
        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals(data, list.get(sizeAfter - 1));
    }

    @Test
    public void TestAddAtIndex() {
        int sizeBefore, sizeAfter;

        insert(3);

        sizeBefore = list.size();
        list.add(0, -1);
        sizeAfter = list.size();
        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals(-1, list.get(0));

        list.add(2, -2);
        assertEquals(-2, list.get(2));
    }

    @Test
    public void TestRemoveAtIndex() {
        int sizeBefore, sizeAfter;

        insert(5);
        sizeBefore = list.size();

        Object dataAtIndexOne = list.get(1);
        list.remove(0);
        assertEquals(dataAtIndexOne, list.get(0));

        sizeAfter = list.size();
        assertEquals(sizeBefore, sizeAfter + 1);

        Object dataAtIndexTwo = list.get(2);
        list.remove(1);
        assertEquals(dataAtIndexTwo, list.get(1));
    }

    @Test
    public void TestIndexOf() {
        list.add(0,1);
        list.add(1,2);
        list.add(2,2);
        assertEquals(1, list.indexOf(2));
    }

    private void insert(int n) {
        for (int i = 0; i < n; i ++) {
            list.addLast(i);
        }
    }

    @Test (expected = NoSuchElementException.class)
    public void TestRemoveEmptyList() {
        assertTrue(list.isEmpty());
        list.remove(0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void TestIndexOutOfSize() {
        int size = list.size();
        list.isValidIndex(size + 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void TestIndexNegative() {
        list.isValidIndex(-1);
    }
}