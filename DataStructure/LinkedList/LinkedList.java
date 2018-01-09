package LinkedList;

import java.util.NoSuchElementException;

public class LinkedList {
    private int size;
    private Node head;
    private Node tail;

    private class Node {
        private Object data;
        private Node next;
        public Node(Object data) {
            this.data = data;
            this.next = null;
        }
        public String toString() {
            return String.valueOf(this.data);
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addFirst(Object data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size ++;
        if (head.next == null) {
            tail = head;
        }
    }

    public void addLast(Object data) {
        Node newNode = new Node(data);
        if(isEmpty()) {
            addFirst(data);
        } else {
            tail.next = newNode;
            tail = newNode;
            size ++;
        }
    }

    public void add(int index, Object data) {
        isValidIndex(index);

        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode;
            size ++;
        } else if (index == 0) {
            addFirst(data);
        } else {
            Node n = getNode(index - 1);
            newNode.next = n.next;
            n.next = newNode;
            size ++;
        }

        if (newNode.next == null) {
            tail = newNode;
        }
    }

    /**
     * Remove the node at the given index
     */
    public void remove(int index) {
        isValidIndex(index);
        Node n;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else if (index == 0) {
            head = head.next;
            n = head;
        } else {
            n = getNode(index - 1);
            n.next = n.next.next;
        }

        if (n.next == null) {
            tail = n;
        }
        size --;
    }

    /**
     * Returns the node at the given index
     */
    public Node getNode(int index) {
        Node n = head;
        for (int i = 0; i < index; i ++) {
            n = n.next;
        }
        return n;
    }

    /**
     * Returns the data of the node at the given index
     */
    public Object get(int index) {
        isValidIndex(index);
        return getNode(index).data;
    }

    /**
     * Returns the index of the first item of the given item, otherwise, -1
     */
    public int indexOf(Object item) {
        int index = 0;
        Node n = head;
        while (n != null && !n.data.equals(item)) {
            index ++;
            n = n.next;
        }
        return (n == null) ? -1 : index;
    }

    public int size() {
        return size;
    }

    /**
     * Check whether it is valid Index
     */
    public void isValidIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index " + index + " for size " + size);
        }
    }
}
