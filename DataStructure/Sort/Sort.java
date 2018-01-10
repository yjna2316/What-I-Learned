package Sort;

import java.util.Arrays;

public class Sort {

    /**
     * Swaps a[left] and a[right]
     */
    public void swap (int[] a, int left, int right) {
        int temp = a[right];
        a[right] = a[left];
        a[left] = temp;
    }

    /**
     * Sorts an array using bubble sort
     */
    public int[] bubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i ++) {
            for (int j = 1; j < n - i; j ++) {
                if (a[j - 1] > a[j]) {
                    swap(a, j - 1, j);
                }
            }
        }
        return a;
    }

    /**
     * Sorts an array using selection sort.
     * Algorithm: first put the smallest item in the first position, then the next smallest in the second position, and so on.
     */
    public int[] selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i ++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j ++) {
                if(a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            swap(a, i, minIndex);
        }
        return a;
    }

    /**
     * Sorts an array using insertion sort.
     * Algorithm: start from the second element, then slide back the elements before the second one until smaller element comes out,
     * then start from the third element again, slide back, so on.
     */
    public int[] insertionSort(int[] a) {
        int n = a.length;
        for (int i = 1; i < n; i ++) {
            int key = a[i];
            int j = i - 1;
            for (; j >= 0 && a[j] > key; j --) {
                a[j + 1] = a[j];
            }
            a[j + 1] = key;
        }
        return a;
    }

    /**
     * Sorts an array using quickSort
     */
    public int[] quickSort(int[] a) {
        return quickSort(a, 0, a.length - 1);
    }

    public int[] quickSort (int[] a, int left, int right) {
        int low = left;
        int high = right;
        int pivot = low;

        if (left < right) {
            while (low < high) {
                while (low <= right && a[low] < a[pivot]) {
                    low ++;
                }
                while (left <= high && a[high] > a[pivot]) {
                    high --;
                }
                swap(a, low, high);
            }
            swap(a, pivot, high);
            quickSort(a, left, high - 1);
            quickSort(a, high + 1, right);
        }
        return a;
    }

}
