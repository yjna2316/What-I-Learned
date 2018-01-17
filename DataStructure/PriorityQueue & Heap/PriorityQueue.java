package priorityQueue;

import java.util.*;

/*
 * Assumption: MaxHeap ū ���� �տ� �ְ�, ���� ���� �ڿ� �ֵ��� �Ѵ�.
 */
public class PriorityQueue {

    int heapSize = 0;
    int[] a = new int[101];

    /**
     * MaxHeap ���� ������, ���ο� ��带 ���� ������ ��� ������ �����Ѵ�.
     * �� ������ ������Ű�� ����, �θ� ���� ���ϸ� �Ž��� �ö󰣴�.
     * �������� �����ϰų�, ��Ʈ�� ������ ������.
     */
    public void addMaxHeap(int key) {
        heapSize = heapSize + 1;
        int newIdx = heapSize;
        while (newIdx > 1 && key > a[newIdx / 2]) {
            a[newIdx] = a[newIdx / 2];
            newIdx = newIdx / 2;
        }
        a[newIdx] = key;

    }

    /**
     * ���� ������, ���� ��Ʈ ��� �ڸ��� ���� ������ ��带 �����´�.
     * Heapify�� ��ģ��, ��Ʈ ��带 ��ȯ�Ѵ�.
     */
    public int removeMaxHeap() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int item = peek();
        a[1] = a[heapSize];  // ������ ��带 ��Ʈ���� ������ �Ŀ�,
        heapSize = heapSize - 1;  // �� ���� ������ 1 �����Ѵ�.

        if(heapSize > 1) {  //  ���� ��尡 2���̻� ������ ��, ��尣 ��ġ ���� �ʿ� (heapify)
            siftDown(1);
        }
        for (int val : a) {
            System.out.print(val+ " ");
        }
        return item;
    }

    /*
     * MaxHeap���� ���� �����, �� ������ ������Ű�� ����
     * ����� �ڽĳ�� �� �� ū ���� ��ȯ�� �ϸ� ��������.
     * �� ������ �����ϰų� �� Ʈ���� ��� ������.
     */
    public void siftDown(int index) {
        int key = a[index];
        int childIndex;

        while (heapSize >= index * 2) {
            if (heapSize == index * 2) {  // ����� �ڽ� ��尡 ���� �ڽ� ��常 ���� ��,
                childIndex = index * 2;
            } else {
                childIndex = (a[index * 2] > a[index * 2 + 1]) ? index * 2 : index * 2 + 1;
            }

            if (a[childIndex] < key) {
                break;
            }
            a[index] = a[childIndex];
            index = childIndex;
        }
        a[index] = key;
    }

    public int peek() {
        if (isEmpty()) {
        throw new NoSuchElementException();
    }
        return a[1];
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }
}
