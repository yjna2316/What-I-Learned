package Queue;

import java.util.NoSuchElementException;

public class LinkedQueue {
    private class Node {
        Object data;
        Node next;
        Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private int size;
    private LinkedQueue q;
    private Node front, rear;

    public LinkedQueue() {
        q = null;
        front = null;
        rear = null;
        size = 0;
    }

    public boolean isEmpty() {
        return (front == null & rear == null);
    }

    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    public void enqueue(Object data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size ++;
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object data = front.data;
        front = front.next;
        size --;
        return data;
    }


    public int size() {
        return size;
    }
}
