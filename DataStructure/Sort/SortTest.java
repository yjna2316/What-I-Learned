package Sort;

import org.junit.*;

import static org.junit.Assert.*;

public class SortTest {

    int[] unsorted = {5, 4, 3, 2, 1};
    int[] sorted = {1, 2, 3, 4, 5};

    Sort s = new Sort();

    @Test
    public void TestBubbleSort() {
        assertArrayEquals(sorted, s.bubbleSort(unsorted));
    }

    @Test
    public void TestSelectionSort() {
        assertArrayEquals(sorted, s.selectionSort(unsorted));
    }

    @Test
    public void TestInsertionSort() {
        assertArrayEquals(sorted, s.insertionSort(unsorted));
    }

    @Test
    public void TestQuickSort() {
        assertArrayEquals(sorted, s.quickSort(unsorted));
    }
}