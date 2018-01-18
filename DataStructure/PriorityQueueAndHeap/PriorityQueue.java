package priorityQueue;

import java.util.*;

/*
 * Assumption: MaxHeap ū ���� �տ� �ְ�, ���� ���� �ڿ� �ֵ��� �Ѵ�.
 */
public class PriorityQueue {

    int heapSize = 0;
    int[] arr = new int[101];

    /**
     * MaxHeap ���� ������, ���ο� ��带 ���� ������ ��� ������ �����Ѵ�.
     * �� ������ ������Ű�� ����, �θ� ���� ���ϸ� �Ž��� �ö󰣴�.
     * �������� �����ϰų�, ��Ʈ�� ������ ������.
     */
    public void addMaxHeap(int key) {
        heapSize = heapSize + 1;
        int newIndex = heapSize;
        while (newIndex > 1 && key > arr[newIndex / 2]) {
            arr[newIndex] = arr[newIndex / 2];
            newIndex = newIndex / 2;
        }
        arr[newIndex] = key;
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
        arr[1] = arr[heapSize];  // ������ ��带 ��Ʈ���� ������ �Ŀ�,
        heapSize = heapSize - 1;  // �� ���� ������ 1 �����Ѵ�.

        if(heapSize > 1) {  //  ���� ��尡 2���̻� ������ ��, ��尣 ��ġ ���� �ʿ� (heapify)
            siftDown(1);
        }
        return item;
    }

    /*
     * MaxHeap���� ���� �����, �� ������ ������Ű�� ����
     * ����� �ڽĳ�� �� �� ū ���� ��ȯ�� �ϸ� ��������.
     * �� ������ �����ϰų� �� Ʈ���� ��� ������.
     */
    public void siftDown(int index) {
        int key = arr[index];
        int childIndex;

        while (heapSize >= index * 2) {
            if (heapSize == index * 2) {  // ����� �ڽ� ��尡 ���� �ڽ� ��常 ���� ��,
                childIndex = index * 2;
            } else {
                childIndex = (arr[index * 2] > arr[index * 2 + 1]) ? index * 2 : index * 2 + 1;
            }

            if (arr[childIndex] < key) {
                break;
            }
            arr[index] = arr[childIndex];
            index = childIndex;
        }
        arr[index] = key;
    }

    public int peek() {
        if (isEmpty()) {
        throw new NoSuchElementException();
    }
        return arr[1];
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }
}
