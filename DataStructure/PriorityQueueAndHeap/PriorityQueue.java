package priorityQueue;

import java.util.*;

/*
 * Assumption: MaxHeap 큰 값이 앞에 있고, 작은 값은 뒤에 있도록 한다.
 */
public class PriorityQueue {

    int heapSize = 0;
    int[] arr = new int[101];

    /**
     * MaxHeap 삽입 연산은, 새로운 노드를 힙의 마지막 노드 다음에 삽입한다.
     * 힙 성질을 만족시키기 위해, 부모 노드와 비교하며 거슬러 올라간다.
     * 힙성질을 만족하거나, 루트에 도달할 때까지.
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
     * 삭제 연산은, 먼저 루트 노드 자리에 힙의 마지막 노드를 가져온다.
     * Heapify를 거친후, 루트 노드를 반환한다.
     */
    public int removeMaxHeap() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int item = peek();
        arr[1] = arr[heapSize];  // 마지막 노드를 루트노드로 가져온 후에,
        heapSize = heapSize - 1;  // 힙 원소 개수가 1 감소한다.

        if(heapSize > 1) {  //  남은 노드가 2개이상 남았을 때, 노드간 위치 조정 필요 (heapify)
            siftDown(1);
        }
        return item;
    }

    /*
     * MaxHeap에서 삭제 연산시, 힙 성질을 만족시키기 위해
     * 노드의 자식노드 중 더 큰 값과 교환을 하며 내려간다.
     * 힙 성질을 만족하거나 힙 트리를 벗어날 때까지.
     */
    public void siftDown(int index) {
        int key = arr[index];
        int childIndex;

        while (heapSize >= index * 2) {
            if (heapSize == index * 2) {  // 노드의 자식 노드가 왼쪽 자식 노드만 있을 때,
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
