package Stack;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class LinkedStack {
    private class Node {
        int data;
        Node next;
        Node (int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    /*
     * initialize the stack
     */
    public LinkedStack() {
        top = null;
        size = 0;
    }

    /*
     * return whether the stack has no items
     */
    public boolean isEmpty() {
        return top == null;
    }

    /*
     * returns the last come element on the top
     */
    public int peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return top.data;
    }

    /*
     * add an element on the top
     */
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size ++;
    }

    /*
     * remove the last come element at the top
     */
    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        int data = peek();
        top = top.next;
        size --;
        return data;
    }

    /*
     * return the number of elements in the stack
     */
    public int size() {
        return size;
    }
}