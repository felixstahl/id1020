package q5;
import java.util.Iterator;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a Stack using a nested Node class and iterator class
 *
 */
public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;   // top element of stack
    private int size;   // size of stack

    // constructor for stack
    public Stack() {
        first = null;
        size = 0;
    }

    // is stack empty?
    public boolean isEmpty() {
        return first == null;
    }

    // add an element to top stack
    public void push(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    // remove top element of stack and return it
    public Item pop() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    // return first element
    public Node<Item> first() {
        return first;
    }

    // return size
    public int size() {
        return size;
    }

    // return a stack iterator
    public Iterator<Item> iterator() {
        return new StackIterator<Item>(first);
    }

    // nested stack iterator class that implements iterator
    private class StackIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        // constructor for stack iterator
        public StackIterator(Node<Item> first) {
            current = first;
        }

        // checks if there is another item in stack
        public boolean hasNext() {
            return current != null;
        }

        // returns next item in stack while iterating
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // nested node class used for stack
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
}
