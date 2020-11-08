package src.q5;
import java.util.Iterator;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a Directed Graph using a bag implementation of adjacency list
 *
 */
public class Digraph {

    private final int vertices;
    private int edges;
    private Bag<Edge>[] adjacents;  // adjacency list
    private int[] indegree;

    // directed graph constructor
    public Digraph(int vertices) {
        this.vertices = vertices;
        this.edges = 0;
        this.indegree = new int[vertices];
        adjacents = new Bag[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacents[i] = new Bag<Edge>();
        }
    }

    // return vertices
    public int vertices() {
        return vertices;
    }

    // return adjacency list for node
    public Bag<Edge> adjacents(int v) {
        return adjacents[v];
    }

    // return edges
    public Bag<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int i = 0 ; i < vertices ; i++) {
            for (Edge e : adjacents[i]) {
                list.add(e);
            }
        }
        return list;
    }

    // add edge
    public void addEdge(Edge edge){
        adjacents[edge.tail].add(edge);
        indegree[edge.head]++;
        edges++;
    }

    // return out degree
    public int outDegree(int vertex) {
        return adjacents[vertex].size();
    }

    // bag implementation
    public class Bag<Item> implements Iterable<Item> {
        private Node<Item> first;
        private int size;

        // constructor
        public Bag() {
            first = null;
            size = 0;
        }

        // size of bag
        public int size() {
            return size;
        }

        // add item (edges) to bag
        public void add(Item item) {
            Node<Item> oldFirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldFirst;
            size++;
        }

        // iterator over bag
        @Override
        public Iterator<Item> iterator() {
            return new BagIterator<Item>(first);
        }

        private class BagIterator<Item> implements Iterator<Item> {

            private Node<Item> current;

            // constructor
            public BagIterator(Node<Item> first) {
                current = first;
            }

            // does it have another item?
            @Override
            public boolean hasNext() {
                return current != null;
            }

            // returns next item
            @Override
            public Item next() {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        // nested node class used by bag
        private class Node<Item> {
            private Item item;
            private Node<Item> next;
        }
    }
}